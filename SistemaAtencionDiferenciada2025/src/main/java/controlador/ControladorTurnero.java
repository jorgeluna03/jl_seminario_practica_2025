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
    
    /**
     * Constructor del Controlador del Turnero
     * @param controladorPrincipal Referencia al controlador principal
     */
    public ControladorTurnero(ControladorPrincipal controladorPrincipal) {
        this.controladorPrincipal = controladorPrincipal;
        this.colaTurnos = new ArrayList<>();
        this.contadorTurnos = 1;
        this.clienteDAO = new ClienteDAO();
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
     * Comando: Registrar un nuevo turno
     * @param dni DNI del cliente
     * @return Turnero creado
     */
    public Turnero registrarTurno(String dni) {
        try {
            // Buscar cliente por DNI
            Cliente cliente = clienteDAO.obtenerClientePorDni(dni);
            
            if (cliente == null) {
                throw new Exception("Cliente no encontrado con DNI: " + dni);
            }
            
            // Generar código de turno
            String codigoTurno = generarCodigoTurno();
            
            // Crear nuevo turno
            Turnero nuevoTurno = new Turnero(contadorTurnos++, LocalDate.now(), cliente, codigoTurno);
            
            // Agregar a la cola
            colaTurnos.add(nuevoTurno);
            
            // Notificar evento (si el controlador principal está disponible)
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
     * Comando: Atender un turno (remover de la cola)
     * @param codigoTurno Código del turno a atender
     */
    public void atenderTurno(String codigoTurno) {
        try {
            Turnero turnoAtendido = null;
            
            // Buscar y remover el turno
            for (int i = 0; i < colaTurnos.size(); i++) {
                if (colaTurnos.get(i).getCodigoTurno().equals(codigoTurno)) {
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
