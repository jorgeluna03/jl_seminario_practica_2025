/*
 * Gestor de Ventanas MDI - Sistema de Atención Diferenciada 2025
 * Implementa el patrón Facade para la gestión de ventanas internas
 * 
 * @author Jorge Luna
 * @version 1.0
 */
package controlador;

import javax.swing.JDesktopPane;
import javax.swing.JInternalFrame;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

/**
 * Gestor de Ventanas MDI que implementa el patrón Facade
 * Simplifica la gestión de ventanas internas del sistema
 */
public class GestorVentanasMDI {
    
    // Desktop pane donde se gestionan las ventanas
    private JDesktopPane desktopPane;
    
    // Lista de ventanas abiertas
    private List<JInternalFrame> ventanasAbiertas;
    
    // Fábrica de formularios
    private FabricaFormularios fabricaFormularios;
    
    // Gestor de eventos
    private GestorEventos gestorEventos;
    
    // Configuración por defecto
    private Map<String, Object> configuracionPorDefecto;
    
    // Contador para posicionamiento automático
    private int contadorPosicion;
    
    /**
     * Constructor del Gestor de Ventanas MDI
     * @param desktopPane Desktop pane donde se gestionan las ventanas
     */
    public GestorVentanasMDI(JDesktopPane desktopPane) {
        this.desktopPane = desktopPane;
        this.ventanasAbiertas = new ArrayList<>();
        this.contadorPosicion = 0;
        
        // Inicializar configuración por defecto
        inicializarConfiguracionPorDefecto();
    }
    
    /**
     * Inicializa la configuración por defecto para las ventanas
     */
    private void inicializarConfiguracionPorDefecto() {
        this.configuracionPorDefecto = new HashMap<>();
        configuracionPorDefecto.put("ancho", 400);
        configuracionPorDefecto.put("alto", 300);
        configuracionPorDefecto.put("maximizable", true);
        configuracionPorDefecto.put("redimensionable", true);
        configuracionPorDefecto.put("cerrable", true);
        configuracionPorDefecto.put("iconificable", true);
    }
    
    /**
     * Abre una ventana del tipo especificado
     * @param tipo Tipo de ventana a abrir
     * @return Ventana abierta
     */
    public JInternalFrame abrirVentana(String tipo) {
        return abrirVentanaConParametros(tipo, null);
    }
    
    /**
     * Abre una ventana del tipo especificado con parámetros
     * @param tipo Tipo de ventana a abrir
     * @param parametros Parámetros adicionales
     * @return Ventana abierta
     */
    public JInternalFrame abrirVentanaConParametros(String tipo, Map<String, Object> parametros) {
        try {
            // Crear ventana usando la fábrica
            JInternalFrame ventana;
            if (parametros != null) {
                ventana = fabricaFormularios.crearFormulario(tipo, parametros);
            } else {
                ventana = fabricaFormularios.crearFormulario(tipo);
            }
            
            // Configurar posición automática
            configurarPosicion(ventana);
            
            // Agregar al desktop pane
            desktopPane.add(ventana);
            
            // Agregar a la lista de ventanas abiertas
            ventanasAbiertas.add(ventana);
            
            // Hacer visible la ventana
            ventana.setVisible(true);
            
            // Intentar seleccionar la ventana
            try {
                ventana.setSelected(true);
            } catch (Exception e) {
                // Ignorar errores de selección
            }
            
            // Notificar evento
            if (gestorEventos != null) {
                gestorEventos.notificarCambio("VENTANA_ABIERTA", tipo);
            }
            
            System.out.println("Ventana abierta: " + tipo);
            return ventana;
            
        } catch (Exception e) {
            System.err.println("Error al abrir ventana " + tipo + ": " + e.getMessage());
            return null;
        }
    }
    
    /**
     * Abre una ventana específica (instancia ya creada)
     * @param tipo Tipo de ventana
     * @param ventana Instancia de la ventana
     * @return Ventana abierta
     */
    public JInternalFrame abrirVentana(String tipo, JInternalFrame ventana) {
        try {
            // Configurar posición automática
            configurarPosicion(ventana);
            
            // Agregar al desktop pane
            desktopPane.add(ventana);
            
            // Agregar a la lista de ventanas abiertas
            ventanasAbiertas.add(ventana);
            
            // Hacer visible la ventana
            ventana.setVisible(true);
            
            // Intentar seleccionar la ventana
            try {
                ventana.setSelected(true);
            } catch (Exception e) {
                // Ignorar errores de selección
            }
            
            // Notificar evento
            if (gestorEventos != null) {
                gestorEventos.notificarCambio("VENTANA_ABIERTA", tipo);
            }
            
            System.out.println("Ventana específica abierta: " + tipo);
            return ventana;
            
        } catch (Exception e) {
            System.err.println("Error al abrir ventana específica " + tipo + ": " + e.getMessage());
            return null;
        }
    }
    
    /**
     * Configura la posición automática de una ventana
     * @param ventana Ventana a configurar
     */
    private void configurarPosicion(JInternalFrame ventana) {
        int offset = 30;
        int x = 50 + (contadorPosicion * offset) % (desktopPane.getWidth() - 200);
        int y = 50 + (contadorPosicion * offset) % (desktopPane.getHeight() - 200);
        
        ventana.setLocation(x, y);
        contadorPosicion++;
    }
    
    /**
     * Cierra una ventana específica
     * @param ventana Ventana a cerrar
     */
    public void cerrarVentana(JInternalFrame ventana) {
        if (ventana != null && ventanasAbiertas.contains(ventana)) {
            ventana.dispose();
            ventanasAbiertas.remove(ventana);
            
            // Notificar evento
            if (gestorEventos != null) {
                gestorEventos.notificarCambio("VENTANA_CERRADA");
            }
            
            System.out.println("Ventana cerrada");
        }
    }
    
    /**
     * Cierra todas las ventanas abiertas
     */
    public void cerrarTodasVentanas() {
        System.out.println("Cerrando " + ventanasAbiertas.size() + " ventanas");
        
        // Crear una copia de la lista para evitar modificaciones concurrentes
        List<JInternalFrame> ventanasACopiar = new ArrayList<>(ventanasAbiertas);
        
        for (JInternalFrame ventana : ventanasACopiar) {
            cerrarVentana(ventana);
        }
        
        // Notificar evento
        if (gestorEventos != null) {
            gestorEventos.notificarCambio("TODAS_VENTANAS_CERRADAS");
        }
    }
    
    /**
     * Organiza las ventanas en cascada
     */
    public void organizarCascada() {
        if (ventanasAbiertas.isEmpty()) {
            System.out.println("No hay ventanas para organizar en cascada");
            return;
        }
        
        int x = 0, y = 0;
        int offset = 30;
        
        for (JInternalFrame ventana : ventanasAbiertas) {
            if (ventana.isVisible()) {
                ventana.setLocation(x, y);
                ventana.setSize(400, 300);
                x += offset;
                y += offset;
            }
        }
        
        System.out.println("Ventanas organizadas en cascada");
    }
    
    /**
     * Organiza las ventanas en mosaico
     */
    public void organizarMosaico() {
        if (ventanasAbiertas.isEmpty()) {
            System.out.println("No hay ventanas para organizar en mosaico");
            return;
        }
        
        int visibleFrames = 0;
        for (JInternalFrame ventana : ventanasAbiertas) {
            if (ventana.isVisible()) {
                visibleFrames++;
            }
        }
        
        if (visibleFrames == 0) {
            System.out.println("No hay ventanas visibles para organizar");
            return;
        }
        
        // Calcular dimensiones para mosaico
        int cols = (int) Math.ceil(Math.sqrt(visibleFrames));
        int rows = (int) Math.ceil((double) visibleFrames / cols);
        
        int frameWidth = desktopPane.getWidth() / cols;
        int frameHeight = desktopPane.getHeight() / rows;
        
        int index = 0;
        for (JInternalFrame ventana : ventanasAbiertas) {
            if (ventana.isVisible()) {
                int col = index % cols;
                int row = index / cols;
                
                ventana.setLocation(col * frameWidth, row * frameHeight);
                ventana.setSize(frameWidth, frameHeight);
                index++;
            }
        }
        
        System.out.println("Ventanas organizadas en mosaico");
    }
    
    /**
     * Obtiene la lista de ventanas abiertas
     * @return Lista de ventanas abiertas
     */
    public List<JInternalFrame> getVentanasAbiertas() {
        return new ArrayList<>(ventanasAbiertas);
    }
    
    /**
     * Obtiene el número de ventanas abiertas
     * @return Número de ventanas abiertas
     */
    public int getCantidadVentanasAbiertas() {
        return ventanasAbiertas.size();
    }
    
    /**
     * Obtiene el número de ventanas visibles
     * @return Número de ventanas visibles
     */
    public int getCantidadVentanasVisibles() {
        int visibles = 0;
        for (JInternalFrame ventana : ventanasAbiertas) {
            if (ventana.isVisible()) {
                visibles++;
            }
        }
        return visibles;
    }
    
    /**
     * Verifica si hay ventanas abiertas
     * @return true si hay ventanas abiertas, false en caso contrario
     */
    public boolean hayVentanasAbiertas() {
        return !ventanasAbiertas.isEmpty();
    }
    
    /**
     * Obtiene la ventana activa (seleccionada)
     * @return Ventana activa o null si no hay ninguna
     */
    public JInternalFrame getVentanaActiva() {
        return desktopPane.getSelectedFrame();
    }
    
    /**
     * Establece la fábrica de formularios
     * @param fabricaFormularios Fábrica de formularios
     */
    public void setFabricaFormularios(FabricaFormularios fabricaFormularios) {
        this.fabricaFormularios = fabricaFormularios;
    }
    
    /**
     * Establece el gestor de eventos
     * @param gestorEventos Gestor de eventos
     */
    public void setGestorEventos(GestorEventos gestorEventos) {
        this.gestorEventos = gestorEventos;
    }
    
    /**
     * Obtiene estadísticas del gestor de ventanas
     * @return String con estadísticas
     */
    public String obtenerEstadisticas() {
        return String.format(
            "ESTADÍSTICAS DEL GESTOR DE VENTANAS MDI\n\n" +
            "Ventanas abiertas: %d\n" +
            "Ventanas visibles: %d\n" +
            "Ventana activa: %s",
            getCantidadVentanasAbiertas(),
            getCantidadVentanasVisibles(),
            getVentanaActiva() != null ? getVentanaActiva().getTitle() : "Ninguna"
        );
    }
}
