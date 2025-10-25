/*
 * Fábrica de Formularios - Sistema de Atención Diferenciada 2025
 * Implementa el patrón Factory para la creación de formularios
 * 
 * @author Jorge Luna
 * @version 1.0
 */
package controlador;

import vista.frmTurneroMDI;
import vista.frmClienteMDI;
import vista.frmAtencionMDI;
import vista.frmReportesMDI;
import javax.swing.JInternalFrame;
import java.util.HashMap;
import java.util.Map;

/**
 * Fábrica de Formularios que implementa el patrón Factory
 * Centraliza la creación de formularios del sistema
 */
public class FabricaFormularios {
    
    // Registro de tipos de formularios disponibles
    private Map<String, Class<? extends JInternalFrame>> tiposFormularios;
    
    // Instancia singleton de la fábrica
    private static FabricaFormularios instancia;
    
    /**
     * Constructor público para permitir instanciación
     */
    public FabricaFormularios() {
        this.tiposFormularios = new HashMap<>();
        registrarTiposFormularios();
    }
    
    /**
     * Obtiene la instancia singleton de la fábrica
     * @return Instancia de FabricaFormularios
     */
    public static FabricaFormularios getInstancia() {
        if (instancia == null) {
            instancia = new FabricaFormularios();
        }
        return instancia;
    }
    
    /**
     * Registra los tipos de formularios disponibles
     */
    private void registrarTiposFormularios() {
        // Registrar formularios del sistema
        tiposFormularios.put("TURNERO", frmTurneroMDI.class);
        tiposFormularios.put("CLIENTE", frmClienteMDI.class);
        tiposFormularios.put("ATENCION", frmAtencionMDI.class);
        tiposFormularios.put("REPORTES", frmReportesMDI.class);
        
        System.out.println("Tipos de formularios registrados: " + tiposFormularios.size());
    }
    
    /**
     * Crea un formulario del tipo especificado
     * @param tipo Tipo de formulario a crear
     * @return Formulario creado
     * @throws IllegalArgumentException Si el tipo no es válido
     */
    public JInternalFrame crearFormulario(String tipo) {
        if (tipo == null || tipo.trim().isEmpty()) {
            throw new IllegalArgumentException("El tipo de formulario no puede ser nulo o vacío");
        }
        
        String tipoNormalizado = tipo.toUpperCase().trim();
        
        if (!tiposFormularios.containsKey(tipoNormalizado)) {
            throw new IllegalArgumentException("Tipo de formulario no soportado: " + tipo);
        }
        
        try {
            Class<? extends JInternalFrame> claseFormulario = tiposFormularios.get(tipoNormalizado);
            JInternalFrame formulario = claseFormulario.getDeclaredConstructor().newInstance();
            
            System.out.println("Formulario creado: " + tipoNormalizado);
            return formulario;
            
        } catch (Exception e) {
            throw new RuntimeException("Error al crear formulario de tipo " + tipo + ": " + e.getMessage(), e);
        }
    }
    
    /**
     * Crea un formulario con parámetros adicionales
     * @param tipo Tipo de formulario a crear
     * @param parametros Parámetros para la creación del formulario
     * @return Formulario creado
     */
    public JInternalFrame crearFormulario(String tipo, Map<String, Object> parametros) {
        JInternalFrame formulario = crearFormulario(tipo);
        
        // Aplicar parámetros si es necesario
        if (parametros != null && !parametros.isEmpty()) {
            configurarFormulario(formulario, parametros);
        }
        
        return formulario;
    }
    
    /**
     * Configura un formulario con parámetros específicos
     * @param formulario Formulario a configurar
     * @param parametros Parámetros de configuración
     */
    private void configurarFormulario(JInternalFrame formulario, Map<String, Object> parametros) {
        try {
            // Configurar título si se proporciona
            if (parametros.containsKey("titulo")) {
                formulario.setTitle((String) parametros.get("titulo"));
            }
            
            // Configurar tamaño si se proporciona
            if (parametros.containsKey("ancho") && parametros.containsKey("alto")) {
                int ancho = (Integer) parametros.get("ancho");
                int alto = (Integer) parametros.get("alto");
                formulario.setSize(ancho, alto);
            }
            
            // Configurar posición si se proporciona
            if (parametros.containsKey("x") && parametros.containsKey("y")) {
                int x = (Integer) parametros.get("x");
                int y = (Integer) parametros.get("y");
                formulario.setLocation(x, y);
            }
            
            // Configurar propiedades adicionales
            if (parametros.containsKey("maximizable")) {
                formulario.setMaximizable((Boolean) parametros.get("maximizable"));
            }
            
            if (parametros.containsKey("redimensionable")) {
                formulario.setResizable((Boolean) parametros.get("redimensionable"));
            }
            
            if (parametros.containsKey("cerrable")) {
                formulario.setClosable((Boolean) parametros.get("cerrable"));
            }
            
            System.out.println("Formulario configurado con parámetros");
            
        } catch (Exception e) {
            System.err.println("Error al configurar formulario: " + e.getMessage());
        }
    }
    
    /**
     * Verifica si un tipo de formulario está disponible
     * @param tipo Tipo de formulario a verificar
     * @return true si está disponible, false en caso contrario
     */
    public boolean esTipoValido(String tipo) {
        if (tipo == null || tipo.trim().isEmpty()) {
            return false;
        }
        
        return tiposFormularios.containsKey(tipo.toUpperCase().trim());
    }
    
    /**
     * Obtiene la lista de tipos de formularios disponibles
     * @return Array de tipos disponibles
     */
    public String[] getTiposDisponibles() {
        return tiposFormularios.keySet().toArray(new String[0]);
    }
    
    /**
     * Obtiene el número de tipos de formularios registrados
     * @return Número de tipos registrados
     */
    public int getCantidadTipos() {
        return tiposFormularios.size();
    }
    
    /**
     * Registra un nuevo tipo de formulario
     * @param tipo Tipo de formulario
     * @param claseFormulario Clase del formulario
     */
    public void registrarTipoFormulario(String tipo, Class<? extends JInternalFrame> claseFormulario) {
        if (tipo == null || tipo.trim().isEmpty()) {
            throw new IllegalArgumentException("El tipo de formulario no puede ser nulo o vacío");
        }
        
        if (claseFormulario == null) {
            throw new IllegalArgumentException("La clase del formulario no puede ser nula");
        }
        
        String tipoNormalizado = tipo.toUpperCase().trim();
        tiposFormularios.put(tipoNormalizado, claseFormulario);
        
        System.out.println("Nuevo tipo de formulario registrado: " + tipoNormalizado);
    }
    
    /**
     * Desregistra un tipo de formulario
     * @param tipo Tipo de formulario a desregistrar
     * @return true si se desregistró correctamente, false en caso contrario
     */
    public boolean desregistrarTipoFormulario(String tipo) {
        if (tipo == null || tipo.trim().isEmpty()) {
            return false;
        }
        
        String tipoNormalizado = tipo.toUpperCase().trim();
        boolean removido = tiposFormularios.remove(tipoNormalizado) != null;
        
        if (removido) {
            System.out.println("Tipo de formulario desregistrado: " + tipoNormalizado);
        }
        
        return removido;
    }
    
    /**
     * Obtiene estadísticas de la fábrica
     * @return String con estadísticas
     */
    public String obtenerEstadisticas() {
        return String.format(
            "ESTADÍSTICAS DE LA FÁBRICA DE FORMULARIOS\n\n" +
            "Tipos registrados: %d\n" +
            "Tipos disponibles: %s",
            getCantidadTipos(),
            String.join(", ", getTiposDisponibles())
        );
    }
    
    /**
     * Limpia todos los tipos registrados
     */
    public void limpiarRegistro() {
        tiposFormularios.clear();
        System.out.println("Registro de tipos de formularios limpiado");
    }
}
