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
import dao.TurneroDAO;
import dao.BoxAtencionDAO;
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
    private TurneroDAO turneroDAO;
    private BoxAtencionDAO boxAtencionDAO;
    
    /**
     * Constructor del Controlador del Turnero
     * @param controladorPrincipal Referencia al controlador principal
     */
    public ControladorTurnero(ControladorPrincipal controladorPrincipal) {
        this.controladorPrincipal = controladorPrincipal;
        this.colaTurnos = new ArrayList<>();
        this.clienteDAO = new ClienteDAO();
        this.gestionesDAO = new GestionesDAO();
        this.modeloAtencionDAO = new ModeloAtencionDiferenciadaDAO();
        this.turneroDAO = new TurneroDAO();
        this.boxAtencionDAO = new BoxAtencionDAO();
        
        // Inicializar contador desde la BD para evitar duplicados
        inicializarContadorTurnos();
    }
    
    /**
     * Inicializa el contador de turnos basándose en los valores máximos de la BD
     * Busca el máximo idTurnero y el máximo número de código de turno
     * Usa el mayor de ambos para asegurar que no haya duplicados
     */
    private void inicializarContadorTurnos() {
        try {
            int maxIdTurnero = turneroDAO.obtenerMaximoIdTurnero();
            int maxNumeroCodigo = turneroDAO.obtenerMaximoNumeroCodigoTurno();
            
            // Usar el mayor entre idTurnero y número de código para evitar duplicados
            int maximo = Math.max(maxIdTurnero, maxNumeroCodigo);
            
            // El siguiente turno debe ser maximo + 1
            this.contadorTurnos = maximo + 1;
            
            System.out.println("=== INICIALIZACIÓN DE CONTADOR DE TURNOS ===");
            System.out.println("Máximo idTurnero en BD: " + maxIdTurnero);
            System.out.println("Máximo número de código en BD: " + maxNumeroCodigo);
            System.out.println("Próximo idTurnero: " + this.contadorTurnos);
            // Generar código de ejemplo para verificar (después de asignar contadorTurnos)
            String siguienteCodigo = generarCodigoTurno();
            System.out.println("Próximo código de turno: " + siguienteCodigo);
            
        } catch (Exception e) {
            System.err.println("Error al inicializar contador de turnos: " + e.getMessage());
            System.err.println("Usando valor por defecto: 1");
            e.printStackTrace();
            this.contadorTurnos = 1;
        }
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
     * Determina el idRol requerido según el segmento del cliente y la prioridad de la gestión
     * @param segmento Segmento del cliente: "ALTO", "MEDIO", "BAJO"
     * @param prioridad Prioridad de la gestión (1 = más alta, 4+ = más baja)
     * @return idRol requerido
     */
    private int determinarIdRolRequerido(String segmento, int prioridad) {
        // Lógica de asignación de rol según segmento y prioridad
        // Cliente BUENO (ALTO) + Prioridad ALTA (1) → idRol = 1
        // Cliente BUENO (ALTO) + Prioridad MEDIA/BAJA → idRol = 2
        // Cliente REGULAR (MEDIO) + Prioridad ALTA → idRol = 2
        // Cliente REGULAR (MEDIO) + Prioridad MEDIA/BAJA → idRol = 3
        // Cliente MALO (BAJO) + Prioridad ALTA → idRol = 2
        // Cliente MALO (BAJO) + Prioridad MEDIA/BAJA → idRol = 3
        
        if (segmento == null) {
            segmento = "BAJO"; // Por defecto
        }
        
        String segmentoUpper = segmento.toUpperCase().trim();
        
        // Prioridad alta = 1
        if (prioridad == 1) {
            if ("ALTO".equals(segmentoUpper)) {
                return 1; // Cliente bueno con prioridad alta → rol 1
            } else {
                return 2; // Cliente regular/malo con prioridad alta → rol 2
            }
        } 
        // Prioridad media/alta = 2
        else if (prioridad == 2) {
            if ("ALTO".equals(segmentoUpper)) {
                return 2; // Cliente bueno con prioridad media → rol 2
            } else {
                return 3; // Cliente regular/malo con prioridad media → rol 3
            }
        }
        // Prioridad media/baja o baja = 3, 4, 5+
        else {
            return 3; // Prioridad baja → rol 3
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
            
            // 4. Obtener segmento y segmentoScore del cliente desde ModeloAtencionDiferenciadaDAO
            // Usar idCliente en lugar de DNI para buscar el segmento
            java.util.Optional<String> segmentoOpt = modeloAtencionDAO.obtenerSegmentoPorIdCliente(cliente.getIdCliente());
            String segmento = segmentoOpt.orElse("BAJO"); // Por defecto BAJO si no tiene segmento
            Integer segmentoScore = modeloAtencionDAO.obtenerSegmentoScorePorIdCliente(cliente.getIdCliente());
            
            // 5. Determinar idRol requerido según segmento y prioridad
            int idRolRequerido = determinarIdRolRequerido(segmento, prioridad);
            System.out.println("=== CALCULO DE SCORE ===");
            System.out.println("Cliente: " + cliente.getIdCliente() + " (" + cliente.getNombre() + " " + cliente.getApellido() + ")");
            System.out.println("Segmento: " + segmento + " (score: " + segmentoScore + ")");
            System.out.println("Prioridad: " + prioridad);
            System.out.println("idRol requerido: " + idRolRequerido);
            
            // 6. Calcular score combinado (menor = más prioritario)
            // Fórmula: prioridad * 10 + segmentoScore
            // Ejemplo: prioridad 1 (más alta) + segmento ALTO (1) = score 11 (muy prioritario)
            //          prioridad 4 (más baja) + segmento BAJO (5) = score 45 (menos prioritario)
            Integer score = (prioridad * 10) + segmentoScore;
            System.out.println("SCORE CALCULADO: " + score + " (menor = más prioritario)");
            System.out.println("========================");
            
            // 7. Generar código de turno
            String codigoTurno = generarCodigoTurno();
            
            // 8. Crear nuevo turno con todos los datos (sin box aún)
            Turnero nuevoTurno = new Turnero(contadorTurnos++, LocalDate.now(), cliente, codigoTurno);
            nuevoTurno.setEstado("EN_ESPERA");
            nuevoTurno.setIdGestion(idGestion);
            nuevoTurno.setPrioridad(prioridad);
            nuevoTurno.setSegmentoScore(segmentoScore);
            nuevoTurno.setScore(score);
            
            // 9. Persistir en base de datos primero (sin box, se asignará después)
            boolean guardado = turneroDAO.guardarTurnero(nuevoTurno);
            if (!guardado) {
                String mensajeError = "ADVERTENCIA: No se pudo guardar el turno en la base de datos.\n" +
                                     "Código: " + codigoTurno + "\n\n" +
                                     "Revisa la consola para ver el error detallado.\n" +
                                     "Posibles causas:\n" +
                                     "- La tabla turnero no tiene los campos nuevos (ejecuta el script SQL)\n" +
                                     "- Error de conexión a la base de datos\n" +
                                     "- El cliente no existe en la tabla cliente";
                
                System.err.println("ADVERTENCIA: No se pudo guardar el turno en la base de datos. Código: " + codigoTurno);
                
                // Mostrar mensaje al usuario
                JOptionPane.showMessageDialog(null, 
                    mensajeError, 
                    "Error al Persistir Turno", 
                    JOptionPane.WARNING_MESSAGE);
                
                // Continuar de todas formas para no interrumpir el flujo
            } else {
                System.out.println("✓ Turno guardado exitosamente en BD: " + codigoTurno);
                
                // 10. Buscar box disponible con el rol requerido (después de guardar)
                System.out.println("\n=== BUSCANDO BOX DISPONIBLE ===");
                System.out.println("idRol requerido: " + idRolRequerido);
                java.util.Optional<Integer> idBoxOpt = boxAtencionDAO.buscarBoxDisponiblePorRol(idRolRequerido);
                Integer idBox = null;
                
                if (idBoxOpt.isPresent()) {
                    idBox = idBoxOpt.get();
                    System.out.println("✓ Box encontrado: " + idBox + " (rol: " + idRolRequerido + ")");
                    
                    // 11. Asignar box al turno en BD
                    System.out.println("\n=== ASIGNANDO BOX AL TURNO ===");
                    boolean boxAsignado = turneroDAO.asignarBox(codigoTurno, idBox);
                    if (boxAsignado) {
                        nuevoTurno.setIdBox(idBox);
                        System.out.println("✓ Box " + idBox + " asignado al turno " + codigoTurno + " en memoria y BD");
                        
                        // Verificar que se guardó correctamente
                        java.util.Optional<Turnero> turnoVerificado = turneroDAO.buscarPorCodigo(codigoTurno);
                        if (turnoVerificado.isPresent() && turnoVerificado.get().getIdBox() != null) {
                            System.out.println("✓ Verificado: idBox guardado correctamente en BD = " + turnoVerificado.get().getIdBox());
                        } else {
                            System.err.println("❌ ADVERTENCIA: El idBox no se guardó correctamente en BD");
                        }
                        
                        // 12. Reordenar la cola del box según score (el nuevo turno puede tener mayor prioridad)
                        System.out.println("\n=== REORDENANDO COLA DEL BOX ===");
                        boolean reordenado = turneroDAO.reordenarColaPorBox(idBox);
                        if (reordenado) {
                            System.out.println("✓ Cola del box " + idBox + " reordenada correctamente");
                        } else {
                            System.err.println("⚠ Error al reordenar la cola del box " + idBox);
                        }
                    } else {
                        System.err.println("❌ ERROR: No se pudo asignar box " + idBox + " al turno " + codigoTurno);
                        System.err.println("   Revisa los logs anteriores para ver el error detallado");
                    }
                } else {
                    System.out.println("⚠ ADVERTENCIA: No se encontró box disponible con rol " + idRolRequerido);
                    System.out.println("   Posibles causas:");
                    System.out.println("   - No hay boxes con estado 'Asignado' en la tabla box_atencion");
                    System.out.println("   - No hay colaboradores con idRol = " + idRolRequerido);
                    System.out.println("   El turno se guardó sin box asignado (se asignará más tarde)");
                }
            }
            
            // 13. Agregar a la cola en memoria (ya está guardado en BD)
            colaTurnos.add(nuevoTurno);
            
            // 14. Notificar evento (si el controlador principal está disponible)
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
     * Comando: Atender un turno (remover de la cola y actualizar estado en BD)
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
                // Actualizar estado en base de datos a ATENDIDO
                turneroDAO.actualizarEstado(codigoTurno, "ATENDIDO");
                
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
     * Cuando se asigna el box, cambia el estado a EN_ATENCION y se persiste en la base de datos.
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
                    
                    // Actualizar en base de datos: estado a EN_ATENCION y asignar box
                    // El turno ya está guardado desde que se registró (EN_ESPERA)
                    turneroDAO.actualizarEstado(codigoTurno, "EN_ATENCION");
                    turneroDAO.asignarBox(codigoTurno, idBox);
                    
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
