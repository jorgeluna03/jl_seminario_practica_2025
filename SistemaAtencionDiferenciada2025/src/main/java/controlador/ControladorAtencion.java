/*
 * Controlador de Atención - Sistema de Atención Diferenciada 2025
 * Implementa el patrón Command para las operaciones de atención
 * 
 * @author Jorge Luna
 * @version 1.0
 */
package controlador;

import modelo.RegistrarAtencion;
import modelo.Cliente;
import java.time.LocalDateTime;
import java.util.List;
import java.util.ArrayList;
import javax.swing.JOptionPane;

/**
 * Controlador de Atención que maneja todas las operaciones relacionadas
 * Implementa el patrón Command para encapsular operaciones
 */
public class ControladorAtencion implements Observer {
    
    private ControladorPrincipal controladorPrincipal;
    private List<RegistrarAtencion> atencionesEnCurso;
    private List<RegistrarAtencion> historialAtenciones;
    
    /**
     * Constructor del Controlador de Atención
     * @param controladorPrincipal Referencia al controlador principal
     */
    public ControladorAtencion(ControladorPrincipal controladorPrincipal) {
        this.controladorPrincipal = controladorPrincipal;
        this.atencionesEnCurso = new ArrayList<>();
        this.historialAtenciones = new ArrayList<>();
    }
    
    /**
     * Comando: Crear una nueva atención
     * @param cliente Cliente a atender
     * @param tipoAtencion Tipo de atención
     * @return Atención creada
     */
    public RegistrarAtencion crearNuevaAtencion(Cliente cliente, String tipoAtencion) {
        try {
            if (cliente == null) {
                throw new Exception("El cliente es obligatorio");
            }
            if (tipoAtencion == null || tipoAtencion.trim().isEmpty()) {
                throw new Exception("El tipo de atención es obligatorio");
            }
            
            // Crear nueva atención
            RegistrarAtencion nuevaAtencion = new RegistrarAtencion(
                generarIdAtencion(),
                cliente,
                tipoAtencion,
                LocalDateTime.now(),
                "En Proceso"
            );
            
            // Agregar a atenciones en curso
            atencionesEnCurso.add(nuevaAtencion);
            
            // Notificar evento
            controladorPrincipal.getGestorEventos().notificarCambio("ATENCION_CREADA");
            
            JOptionPane.showMessageDialog(null, 
                "Atención creada exitosamente", 
                "Éxito", 
                JOptionPane.INFORMATION_MESSAGE);
            
            return nuevaAtencion;
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, 
                "Error al crear atención: " + e.getMessage(), 
                "Error", 
                JOptionPane.ERROR_MESSAGE);
            return null;
        }
    }
    
    /**
     * Comando: Pausar una atención
     * @param idAtencion ID de la atención a pausar
     */
    public void pausarAtencion(int idAtencion) {
        try {
            RegistrarAtencion atencion = buscarAtencionEnCurso(idAtencion);
            
            if (atencion != null) {
                atencion.setEstado("Pausada");
                atencion.setFechaFin(LocalDateTime.now());
                
                // Notificar evento
                controladorPrincipal.getGestorEventos().notificarCambio("ATENCION_PAUSADA");
                
                JOptionPane.showMessageDialog(null, 
                    "Atención pausada exitosamente", 
                    "Éxito", 
                    JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(null, 
                    "Atención no encontrada", 
                    "Error", 
                    JOptionPane.ERROR_MESSAGE);
            }
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, 
                "Error al pausar atención: " + e.getMessage(), 
                "Error", 
                JOptionPane.ERROR_MESSAGE);
        }
    }
    
    /**
     * Comando: Continuar una atención pausada
     * @param idAtencion ID de la atención a continuar
     */
    public void continuarAtencion(int idAtencion) {
        try {
            RegistrarAtencion atencion = buscarAtencionEnCurso(idAtencion);
            
            if (atencion != null) {
                atencion.setEstado("En Proceso");
                atencion.setFechaFin(null);
                
                // Notificar evento
                controladorPrincipal.getGestorEventos().notificarCambio("ATENCION_CONTINUADA");
                
                JOptionPane.showMessageDialog(null, 
                    "Atención continuada exitosamente", 
                    "Éxito", 
                    JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(null, 
                    "Atención no encontrada", 
                    "Error", 
                    JOptionPane.ERROR_MESSAGE);
            }
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, 
                "Error al continuar atención: " + e.getMessage(), 
                "Error", 
                JOptionPane.ERROR_MESSAGE);
        }
    }
    
    /**
     * Comando: Finalizar una atención
     * @param idAtencion ID de la atención a finalizar
     * @param resultado Resultado de la atención
     */
    public void finalizarAtencion(int idAtencion, String resultado) {
        try {
            RegistrarAtencion atencion = buscarAtencionEnCurso(idAtencion);
            
            if (atencion != null) {
                atencion.setEstado("Finalizada");
                atencion.setFechaFin(LocalDateTime.now());
                atencion.setResultado(resultado);
                
                // Mover de atenciones en curso al historial
                atencionesEnCurso.remove(atencion);
                historialAtenciones.add(atencion);
                
                // Notificar evento
                controladorPrincipal.getGestorEventos().notificarCambio("ATENCION_FINALIZADA");
                
                JOptionPane.showMessageDialog(null, 
                    "Atención finalizada exitosamente", 
                    "Éxito", 
                    JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(null, 
                    "Atención no encontrada", 
                    "Error", 
                    JOptionPane.ERROR_MESSAGE);
            }
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, 
                "Error al finalizar atención: " + e.getMessage(), 
                "Error", 
                JOptionPane.ERROR_MESSAGE);
        }
    }
    
    /**
     * Comando: Obtener atenciones en curso
     * @return Lista de atenciones en curso
     */
    public List<RegistrarAtencion> getAtencionesEnCurso() {
        return new ArrayList<>(atencionesEnCurso);
    }
    
    /**
     * Comando: Obtener historial de atenciones
     * @return Lista del historial de atenciones
     */
    public List<RegistrarAtencion> getHistorialAtenciones() {
        return new ArrayList<>(historialAtenciones);
    }
    
    /**
     * Comando: Buscar atención en curso por ID
     * @param idAtencion ID de la atención
     * @return Atención encontrada o null
     */
    public RegistrarAtencion buscarAtencionEnCurso(int idAtencion) {
        for (RegistrarAtencion atencion : atencionesEnCurso) {
            if (atencion.getIdAtencion() == idAtencion) {
                return atencion;
            }
        }
        return null;
    }
    
    /**
     * Comando: Mostrar atenciones en curso
     */
    public void mostrarAtencionesEnCurso() {
        try {
            if (atencionesEnCurso.isEmpty()) {
                JOptionPane.showMessageDialog(null, 
                    "No hay atenciones en curso", 
                    "Información", 
                    JOptionPane.INFORMATION_MESSAGE);
                return;
            }
            
            StringBuilder mensaje = new StringBuilder("ATENCIONES EN CURSO\n\n");
            
            for (RegistrarAtencion atencion : atencionesEnCurso) {
                mensaje.append(String.format(
                    "ID: %d | Cliente: %s %s | Tipo: %s | Estado: %s\n",
                    atencion.getIdAtencion(),
                    atencion.getCliente().getNombre(),
                    atencion.getCliente().getApellido(),
                    atencion.getTipoAtencion(),
                    atencion.getEstado()
                ));
            }
            
            JOptionPane.showMessageDialog(null, mensaje.toString(), "Atenciones en Curso", JOptionPane.INFORMATION_MESSAGE);
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, 
                "Error al mostrar atenciones en curso: " + e.getMessage(), 
                "Error", 
                JOptionPane.ERROR_MESSAGE);
        }
    }
    
    /**
     * Comando: Mostrar historial de atenciones
     */
    public void mostrarHistorialAtenciones() {
        try {
            if (historialAtenciones.isEmpty()) {
                JOptionPane.showMessageDialog(null, 
                    "No hay historial de atenciones", 
                    "Información", 
                    JOptionPane.INFORMATION_MESSAGE);
                return;
            }
            
            StringBuilder mensaje = new StringBuilder("HISTORIAL DE ATENCIONES\n\n");
            
            for (RegistrarAtencion atencion : historialAtenciones) {
                mensaje.append(String.format(
                    "ID: %d | Cliente: %s %s | Tipo: %s | Estado: %s | Fecha Fin: %s\n",
                    atencion.getIdAtencion(),
                    atencion.getCliente().getNombre(),
                    atencion.getCliente().getApellido(),
                    atencion.getTipoAtencion(),
                    atencion.getEstado(),
                    atencion.getFechaFin() != null ? atencion.getFechaFin().toString() : "N/A"
                ));
            }
            
            JOptionPane.showMessageDialog(null, mensaje.toString(), "Historial de Atenciones", JOptionPane.INFORMATION_MESSAGE);
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, 
                "Error al mostrar historial: " + e.getMessage(), 
                "Error", 
                JOptionPane.ERROR_MESSAGE);
        }
    }
    
    /**
     * Comando: Obtener estadísticas de atención
     */
    public void mostrarEstadisticasAtencion() {
        try {
            int totalEnCurso = atencionesEnCurso.size();
            int totalFinalizadas = historialAtenciones.size();
            int totalPausadas = 0;
            int totalEnProceso = 0;
            
            for (RegistrarAtencion atencion : atencionesEnCurso) {
                if ("Pausada".equals(atencion.getEstado())) {
                    totalPausadas++;
                } else if ("En Proceso".equals(atencion.getEstado())) {
                    totalEnProceso++;
                }
            }
            
            String estadisticas = String.format(
                "ESTADÍSTICAS DE ATENCIÓN\n\n" +
                "Atenciones en curso: %d\n" +
                "  - En proceso: %d\n" +
                "  - Pausadas: %d\n" +
                "Atenciones finalizadas: %d\n" +
                "Total de atenciones: %d",
                totalEnCurso,
                totalEnProceso,
                totalPausadas,
                totalFinalizadas,
                totalEnCurso + totalFinalizadas
            );
            
            JOptionPane.showMessageDialog(null, estadisticas, "Estadísticas de Atención", JOptionPane.INFORMATION_MESSAGE);
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, 
                "Error al obtener estadísticas: " + e.getMessage(), 
                "Error", 
                JOptionPane.ERROR_MESSAGE);
        }
    }
    
    /**
     * Genera un ID único para la atención
     * @return ID único generado
     */
    private int generarIdAtencion() {
        return (atencionesEnCurso.size() + historialAtenciones.size()) + 1;
    }
    
    /**
     * Implementación del patrón Observer
     * @param evento Evento recibido
     */
    @Override
    public void actualizar(String evento) {
        switch (evento) {
            case "SISTEMA_INICIADO":
                // Inicializar controlador de atención si es necesario
                break;
            case "SISTEMA_CERRANDO":
                // Guardar estado del controlador de atención
                break;
            case "USUARIO_CAMBIADO":
                // Actualizar información del usuario en el controlador
                break;
            default:
                // Otros eventos
                break;
        }
    }
}
