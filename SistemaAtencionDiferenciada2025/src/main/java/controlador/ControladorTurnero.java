/*
 * Controlador del Turnero - Sistema de Atención Diferenciada 2025
 * Implementa el patrón Command para las operaciones del turnero
 * 
 * @author Jorge Luna
 * @version 1.0
 */
package controlador;

import vista.frmTurneroMDI;
import modelo.Turnero;
import modelo.Cliente;
import dao.ClienteDAO;
import dao.GestionesDAO;
import dao.ModeloAtencionDiferenciadaDAO;
import java.time.LocalDate;
import java.util.List;
import java.util.ArrayList;
import javax.swing.JOptionPane;

/**
 * Controlador del Turnero que maneja todas las operaciones relacionadas
 * Implementa el patrón Command para encapsular operaciones
 */
public class ControladorTurnero implements Observer {
    
    private ControladorPrincipal controladorPrincipal;
    private frmTurneroMDI ventanaTurnero;
    private List<Turnero> colaTurnos;
    private int contadorTurnos;
    private ClienteDAO clienteDAO;
    private GestionesDAO gestionesDAO;
    private ModeloAtencionDiferenciadaDAO modeloAtencionDAO;
    
    /**
     * Constructor del Controlador del Turnero
     * @param controladorPrincipal Referencia al controlador principal
     */
    public ControladorTurnero(ControladorPrincipal controladorPrincipal) {
        this.controladorPrincipal = controladorPrincipal;
        this.colaTurnos = new ArrayList<>();
        this.contadorTurnos = 1;
        this.clienteDAO = new ClienteDAO();
        this.gestionesDAO = new GestionesDAO();
        this.modeloAtencionDAO = new ModeloAtencionDiferenciadaDAO();
    }
    
    /**
     * Comando: Abrir el formulario del turnero
     */
    public void abrirTurnero() {
        try {
            if (ventanaTurnero == null || ventanaTurnero.isClosed()) {
                ventanaTurnero = new frmTurneroMDI();
                ventanaTurnero.setControlador(this);
                
                // Abrir en el gestor de ventanas (si el controlador principal está disponible)
                if (controladorPrincipal != null) {
                    controladorPrincipal.getGestorVentanas().abrirVentana("TURNERO", ventanaTurnero);
                } else {
                    // Si no hay controlador principal, mostrar la ventana directamente
                    ventanaTurnero.setVisible(true);
                }
                
                // Notificar evento (si el controlador principal está disponible)
                if (controladorPrincipal != null) {
                    controladorPrincipal.getGestorEventos().notificarCambio("TURNERO_ABIERTO");
                }
            } else {
                // Si ya está abierto, traerlo al frente
                ventanaTurnero.setSelected(true);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, 
                "Error al abrir el turnero: " + e.getMessage(), 
                "Error", 
                JOptionPane.ERROR_MESSAGE);
        }
    }
    
    /**
     * Comando: Abrir el turnero como ventana independiente
     */
    public void abrirTurneroIndependiente() {
        try {
            // Crear y mostrar el formulario directamente
            vista.frmPantallaTurnero turneroForm = new vista.frmPantallaTurnero();
            turneroForm.setControladorTurnero(this);
            
            // Crear ventana independiente
            javax.swing.JFrame ventanaTurnero = new javax.swing.JFrame("Turnero - Sistema de Atención Diferenciada");
            ventanaTurnero.setDefaultCloseOperation(javax.swing.JFrame.DISPOSE_ON_CLOSE);
            ventanaTurnero.getContentPane().add(turneroForm);
            ventanaTurnero.pack();
            ventanaTurnero.setLocationRelativeTo(null);
            ventanaTurnero.setVisible(true);
            
            // Notificar evento (si el controlador principal está disponible)
            if (controladorPrincipal != null) {
                controladorPrincipal.getGestorEventos().notificarCambio("TURNERO_ABIERTO");
            }
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, 
                "Error al abrir el turnero: " + e.getMessage(), 
                "Error", 
                JOptionPane.ERROR_MESSAGE);
        }
    }
    
    /**
     * Comando: Llamar al siguiente turno
     */
    public void llamarSiguiente() {
        try {
            if (colaTurnos.isEmpty()) {
                JOptionPane.showMessageDialog(null, 
                    "No hay turnos en la cola", 
                    "Información", 
                    JOptionPane.INFORMATION_MESSAGE);
                return;
            }
            
            // Obtener el siguiente turno (el primero en la cola)
            Turnero siguienteTurno = colaTurnos.get(0);
            
            // Mostrar información del turno
            String mensaje = String.format(
                "Llamando turno: %s\nCliente: %s %s\nDNI: %s",
                siguienteTurno.getCodigoTurno(),
                siguienteTurno.getCliente().getNombre(),
                siguienteTurno.getCliente().getApellido(),
                siguienteTurno.getCliente().getDni()
            );
            
            JOptionPane.showMessageDialog(null, mensaje, "Llamando Turno", JOptionPane.INFORMATION_MESSAGE);
            
            // Notificar evento
            if (controladorPrincipal != null) {
                controladorPrincipal.getGestorEventos().notificarCambio("TURNO_LLAMADO");
            }
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, 
                "Error al llamar siguiente turno: " + e.getMessage(), 
                "Error", 
                JOptionPane.ERROR_MESSAGE);
        }
    }
    
    /**
     * Comando: Reasignar un turno
     */
    public void reasignarTurno() {
        try {
            if (colaTurnos.isEmpty()) {
                JOptionPane.showMessageDialog(null, 
                    "No hay turnos para reasignar", 
                    "Información", 
                    JOptionPane.INFORMATION_MESSAGE);
                return;
            }
            
            // Mostrar lista de turnos para reasignar
            StringBuilder mensaje = new StringBuilder("Turnos disponibles para reasignar:\n\n");
            for (int i = 0; i < colaTurnos.size(); i++) {
                Turnero turno = colaTurnos.get(i);
                mensaje.append(String.format("%d. %s - %s %s\n", 
                    i + 1, 
                    turno.getCodigoTurno(),
                    turno.getCliente().getNombre(),
                    turno.getCliente().getApellido()));
            }
            
            String input = JOptionPane.showInputDialog(null, 
                mensaje.toString() + "\nIngrese el número del turno a reasignar:");
            
            if (input != null && !input.trim().isEmpty()) {
                int indice = Integer.parseInt(input) - 1;
                if (indice >= 0 && indice < colaTurnos.size()) {
                    Turnero turnoReasignado = colaTurnos.remove(indice);
                    colaTurnos.add(turnoReasignado); // Mover al final
                    
                    JOptionPane.showMessageDialog(null, 
                        "Turno reasignado exitosamente", 
                        "Éxito", 
                        JOptionPane.INFORMATION_MESSAGE);
                    
                    // Notificar evento
                    if (controladorPrincipal != null) {
                        controladorPrincipal.getGestorEventos().notificarCambio("TURNO_REASIGNADO");
                    }
                } else {
                    JOptionPane.showMessageDialog(null, 
                        "Número de turno inválido", 
                        "Error", 
                        JOptionPane.ERROR_MESSAGE);
                }
            }
            
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, 
                "Debe ingresar un número válido", 
                "Error", 
                JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, 
                "Error al reasignar turno: " + e.getMessage(), 
                "Error", 
                JOptionPane.ERROR_MESSAGE);
        }
    }
    
    /**
     * Mapea el tipo de turno (String) a idGestion (Integer)
     * @param tipoTurno Tipo de turno: "Consulta general", "Solicitud Prestamo", "Reclamo", "Otros"
     * @return idGestion correspondiente o null si no se encuentra
     */
    private Integer mapearTipoTurnoAIdGestion(String tipoTurno) {
        if (tipoTurno == null) {
            return null;
        }
        
        String tipoNormalizado = tipoTurno.trim().toLowerCase();
        
        switch (tipoNormalizado) {
            case "consulta general":
                return 2;
            case "solicitud prestamo":
                return 3;
            case "reclamo":
                return 1;
            case "otros":
                return 4;
            default:
                return null; // Tipo no reconocido
        }
    }
    
    /**
     * Comando: Registrar un nuevo turno
     * @param dni DNI del cliente
     * @param tipoTurno Tipo de turno: "Consulta general", "Solicitud Prestamo", "Reclamo", "Otros"
     * @return Turnero creado
     */
    public Turnero registrarTurno(String dni, String tipoTurno) {
        try {
            // 1. Buscar cliente por DNI
            Cliente cliente = clienteDAO.obtenerClientePorDni(dni);
            
            if (cliente == null) {
                throw new Exception("Cliente no encontrado con DNI: " + dni);
            }
            
            // 2. Mapear tipo de turno a idGestion
            Integer idGestion = mapearTipoTurnoAIdGestion(tipoTurno);
            if (idGestion == null) {
                throw new Exception("Tipo de turno no válido: " + tipoTurno);
            }
            
            // 3. Obtener prioridad desde GestionesDAO
            Integer prioridad = gestionesDAO.obtenerPrioridadPorIdGestion(idGestion);
            if (prioridad == null) {
                throw new Exception("No se pudo obtener la prioridad para idGestion: " + idGestion);
            }
            
            // 4. Obtener segmentoScore del cliente desde ModeloAtencionDiferenciadaDAO
            Integer segmentoScore = modeloAtencionDAO.obtenerSegmentoScorePorDni(dni);
            
            // 5. Calcular score combinado (menor = más prioritario)
            // Fórmula: prioridad * 10 + segmentoScore
            // Ejemplo: prioridad 1 (más alta) + segmento ALTO (1) = score 11 (muy prioritario)
            //          prioridad 4 (más baja) + segmento BAJO (5) = score 45 (menos prioritario)
            Integer score = (prioridad * 10) + segmentoScore;
            
            // 6. Generar código de turno
            String codigoTurno = generarCodigoTurno();
            
            // 7. Crear nuevo turno con todos los datos
            Turnero nuevoTurno = new Turnero(contadorTurnos++, LocalDate.now(), cliente, codigoTurno);
            nuevoTurno.setEstado("EN_ESPERA");
            nuevoTurno.setIdGestion(idGestion);
            nuevoTurno.setPrioridad(prioridad);
            nuevoTurno.setSegmentoScore(segmentoScore);
            nuevoTurno.setScore(score);
            
            // 8. Agregar a la cola (por ahora en orden de llegada, luego se ordenará por score)
            colaTurnos.add(nuevoTurno);
            
            // 9. Notificar evento (si el controlador principal está disponible)
            if (controladorPrincipal != null) {
                controladorPrincipal.getGestorEventos().notificarCambio("TURNO_REGISTRADO");
            }
            
            return nuevoTurno;
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, 
                "Error al registrar turno: " + e.getMessage(), 
                "Error", 
                JOptionPane.ERROR_MESSAGE);
            return null;
        }
    }
    
    /**
     * Comando: Registrar un nuevo turno (método sobrecargado sin tipoTurno para compatibilidad)
     * @param dni DNI del cliente
     * @return Turnero creado
     * @deprecated Usar registrarTurno(String dni, String tipoTurno) en su lugar
     */
    @Deprecated
    public Turnero registrarTurno(String dni) {
        // Por defecto, asumir "Consulta general" si no se especifica tipo
        return registrarTurno(dni, "Consulta general");
    }
    
    /**
     * Comando: Atender un turno (remover de la cola)
     * @param codigoTurno Código del turno a atender
     */
    public void atenderTurno(String codigoTurno) {
        try {
            Turnero turnoAtendido = null;
            
            // Buscar y remover el turno
            for (int i = 0; i < colaTurnos.size(); i++) {
                if (colaTurnos.get(i).getCodigoTurno().equals(codigoTurno)) {
                    // Marcar como atendido antes de remover
                    colaTurnos.get(i).setEstado("ATENDIDO");
                    turnoAtendido = colaTurnos.remove(i);
                    break;
                }
            }
            
            if (turnoAtendido != null) {
                JOptionPane.showMessageDialog(null, 
                    "Turno " + codigoTurno + " atendido exitosamente", 
                    "Éxito", 
                    JOptionPane.INFORMATION_MESSAGE);
                
                // Notificar evento
                if (controladorPrincipal != null) {
                    controladorPrincipal.getGestorEventos().notificarCambio("TURNO_ATENDIDO");
                }
            } else {
                JOptionPane.showMessageDialog(null, 
                    "Turno no encontrado: " + codigoTurno, 
                    "Error", 
                    JOptionPane.ERROR_MESSAGE);
            }
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, 
                "Error al atender turno: " + e.getMessage(), 
                "Error", 
                JOptionPane.ERROR_MESSAGE);
        }
    }
    
    /**
     * Asigna un Box a un turno en cola por código de turno.
     * @param codigoTurno Código del turno
     * @param idBox Identificador del box a asignar
     * @return true si se asignó, false si no se encontró el turno
     */
    public boolean asignarBoxATurno(String codigoTurno, int idBox) {
        try {
            for (Turnero t : colaTurnos) {
                if (t.getCodigoTurno().equals(codigoTurno)) {
                    t.setIdBox(idBox);
                    t.setEstado("EN_ATENCION");
                    
                    // Notificar evento
                    if (controladorPrincipal != null) {
                        controladorPrincipal.getGestorEventos().notificarCambio("TURNO_EN_ATENCION");
                    }
                    return true;
                }
            }
            JOptionPane.showMessageDialog(null, 
                "Turno no encontrado para asignar box: " + codigoTurno, 
                "Información", 
                JOptionPane.INFORMATION_MESSAGE);
            return false;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, 
                "Error al asignar box al turno: " + e.getMessage(), 
                "Error", 
                JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }
    
    /**
     * Comando: Obtener estadísticas del turnero
     */
    public void mostrarEstadisticas() {
        try {
            int totalTurnos = colaTurnos.size();
            int turnosPrioritarios = 0;
            int turnosNormales = 0;
            
            for (Turnero turno : colaTurnos) {
                if (turno.getCodigoTurno().startsWith("P")) {
                    turnosPrioritarios++;
                } else {
                    turnosNormales++;
                }
            }
            
            String estadisticas = String.format(
                "ESTADÍSTICAS DEL TURNERO\n\n" +
                "Total de turnos en cola: %d\n" +
                "Turnos prioritarios: %d\n" +
                "Turnos normales: %d\n" +
                "Próximo turno: %s",
                totalTurnos,
                turnosPrioritarios,
                turnosNormales,
                colaTurnos.isEmpty() ? "No hay turnos" : colaTurnos.get(0).getCodigoTurno()
            );
            
            JOptionPane.showMessageDialog(null, estadisticas, "Estadísticas", JOptionPane.INFORMATION_MESSAGE);
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, 
                "Error al obtener estadísticas: " + e.getMessage(), 
                "Error", 
                JOptionPane.ERROR_MESSAGE);
        }
    }
    
    /**
     * Genera un código único para el turno
     * @return Código del turno generado
     */
    private String generarCodigoTurno() {
        // Simular prioridad basada en el número de turno
        String prefijo = (contadorTurnos % 3 == 0) ? "P" : "N"; // Cada 3 turnos es prioritario
        return prefijo + String.format("%04d", contadorTurnos);
    }
    
    /**
     * Obtiene la lista de turnos en cola
     * @return Lista de turnos
     */
    public List<Turnero> getColaTurnos() {
        return new ArrayList<>(colaTurnos);
    }
    
    /**
     * Obtiene el número total de turnos registrados
     * @return Número total de turnos
     */
    public int getTotalTurnos() {
        return contadorTurnos - 1;
    }
    
    /**
     * Implementación del patrón Observer
     * @param evento Evento recibido
     */
    @Override
    public void actualizar(String evento) {
        switch (evento) {
            case "SISTEMA_INICIADO":
                // Inicializar turnero si es necesario
                break;
            case "SISTEMA_CERRANDO":
                // Guardar estado del turnero
                break;
            case "USUARIO_CAMBIADO":
                // Actualizar información del usuario en el turnero
                break;
            default:
                // Otros eventos
                break;
        }
    }
}
