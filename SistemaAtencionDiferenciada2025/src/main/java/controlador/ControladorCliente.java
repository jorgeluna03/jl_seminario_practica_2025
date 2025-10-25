/*
 * Controlador de Clientes - Sistema de Atención Diferenciada 2025
 * Implementa el patrón Command para las operaciones de clientes
 * 
 * @author Jorge Luna
 * @version 1.0
 */
package controlador;

import modelo.Cliente;
import dao.ClienteDAO;
import java.time.LocalDate;
import java.util.List;
import java.util.ArrayList;
import javax.swing.JOptionPane;

/**
 * Controlador de Clientes que maneja todas las operaciones CRUD
 * Implementa el patrón Command para encapsular operaciones
 */
public class ControladorCliente implements Observer {
    
    private ControladorPrincipal controladorPrincipal;
    private ClienteDAO clienteDAO;
    
    /**
     * Constructor del Controlador de Clientes
     * @param controladorPrincipal Referencia al controlador principal
     */
    public ControladorCliente(ControladorPrincipal controladorPrincipal) {
        this.controladorPrincipal = controladorPrincipal;
        this.clienteDAO = new ClienteDAO();
    }
    
    /**
     * Comando: Crear un nuevo cliente
     * @param nombre Nombre del cliente
     * @param apellido Apellido del cliente
     * @param fechaNacimiento Fecha de nacimiento
     * @param dni DNI del cliente
     * @return Cliente creado
     */
    public Cliente crearCliente(String nombre, String apellido, LocalDate fechaNacimiento, String dni) {
        try {
            // Validar datos
            if (nombre == null || nombre.trim().isEmpty()) {
                throw new Exception("El nombre es obligatorio");
            }
            if (apellido == null || apellido.trim().isEmpty()) {
                throw new Exception("El apellido es obligatorio");
            }
            if (dni == null || dni.trim().isEmpty()) {
                throw new Exception("El DNI es obligatorio");
            }
            if (fechaNacimiento == null) {
                throw new Exception("La fecha de nacimiento es obligatoria");
            }
            
            // Verificar si el cliente ya existe
            Cliente clienteExistente = clienteDAO.obtenerClientePorDni(dni);
            if (clienteExistente != null) {
                throw new Exception("Ya existe un cliente con el DNI: " + dni);
            }
            
            // Crear nuevo cliente
            Cliente nuevoCliente = new Cliente(nombre, apellido, fechaNacimiento, dni, LocalDate.now());
            
            // Guardar en la base de datos
            boolean guardado = clienteDAO.guardarCliente(nuevoCliente);
            if (!guardado) {
                throw new Exception("Error al guardar el cliente en la base de datos");
            }
            
            // Notificar evento
            controladorPrincipal.getGestorEventos().notificarCambio("CLIENTE_CREADO");
            
            JOptionPane.showMessageDialog(null, 
                "Cliente creado exitosamente", 
                "Éxito", 
                JOptionPane.INFORMATION_MESSAGE);
            
            return nuevoCliente;
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, 
                "Error al crear cliente: " + e.getMessage(), 
                "Error", 
                JOptionPane.ERROR_MESSAGE);
            return null;
        }
    }
    
    /**
     * Comando: Buscar cliente por DNI
     * @param dni DNI del cliente
     * @return Cliente encontrado o null
     */
    public Cliente buscarClientePorDni(String dni) {
        try {
            if (dni == null || dni.trim().isEmpty()) {
                throw new Exception("El DNI es obligatorio");
            }
            
            Cliente cliente = clienteDAO.obtenerClientePorDni(dni);
            
            if (cliente != null) {
                // Notificar evento
                controladorPrincipal.getGestorEventos().notificarCambio("CLIENTE_BUSCADO");
            }
            
            return cliente;
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, 
                "Error al buscar cliente: " + e.getMessage(), 
                "Error", 
                JOptionPane.ERROR_MESSAGE);
            return null;
        }
    }
    
    /**
     * Comando: Actualizar datos de un cliente
     * @param cliente Cliente con datos actualizados
     * @return true si se actualizó correctamente
     */
    public boolean actualizarCliente(Cliente cliente) {
        try {
            if (cliente == null) {
                throw new Exception("El cliente es obligatorio");
            }
            
            // Validar que el cliente existe
            Cliente clienteExistente = clienteDAO.obtenerClientePorDni(cliente.getDni());
            if (clienteExistente == null) {
                throw new Exception("El cliente no existe en la base de datos");
            }
            
            // Actualizar en la base de datos
            boolean actualizado = clienteDAO.actualizarCliente(cliente);
            if (!actualizado) {
                throw new Exception("Error al actualizar el cliente en la base de datos");
            }
            
            // Notificar evento
            controladorPrincipal.getGestorEventos().notificarCambio("CLIENTE_ACTUALIZADO");
            
            JOptionPane.showMessageDialog(null, 
                "Cliente actualizado exitosamente", 
                "Éxito", 
                JOptionPane.INFORMATION_MESSAGE);
            
            return true;
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, 
                "Error al actualizar cliente: " + e.getMessage(), 
                "Error", 
                JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }
    
    /**
     * Comando: Eliminar un cliente
     * @param dni DNI del cliente a eliminar
     * @return true si se eliminó correctamente
     */
    public boolean eliminarCliente(String dni) {
        try {
            if (dni == null || dni.trim().isEmpty()) {
                throw new Exception("El DNI es obligatorio");
            }
            
            // Verificar que el cliente existe
            Cliente clienteExistente = clienteDAO.obtenerClientePorDni(dni);
            if (clienteExistente == null) {
                throw new Exception("El cliente no existe en la base de datos");
            }
            
            // Confirmar eliminación
            int opcion = JOptionPane.showConfirmDialog(null, 
                "¿Está seguro que desea eliminar el cliente " + clienteExistente.getNombre() + " " + clienteExistente.getApellido() + "?",
                "Confirmar Eliminación",
                JOptionPane.YES_NO_OPTION);
            
            if (opcion != JOptionPane.YES_OPTION) {
                return false;
            }
            
            // Eliminar de la base de datos
            boolean eliminado = clienteDAO.eliminarCliente(dni);
            if (!eliminado) {
                throw new Exception("Error al eliminar el cliente de la base de datos");
            }
            
            // Notificar evento
            controladorPrincipal.getGestorEventos().notificarCambio("CLIENTE_ELIMINADO");
            
            JOptionPane.showMessageDialog(null, 
                "Cliente eliminado exitosamente", 
                "Éxito", 
                JOptionPane.INFORMATION_MESSAGE);
            
            return true;
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, 
                "Error al eliminar cliente: " + e.getMessage(), 
                "Error", 
                JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }
    
    /**
     * Comando: Buscar clientes por criterios
     * @param criterio Criterio de búsqueda
     * @param valor Valor a buscar
     * @return Lista de clientes encontrados
     */
    public List<Cliente> buscarClientes(String criterio, String valor) {
        try {
            if (criterio == null || criterio.trim().isEmpty()) {
                throw new Exception("El criterio de búsqueda es obligatorio");
            }
            if (valor == null || valor.trim().isEmpty()) {
                throw new Exception("El valor de búsqueda es obligatorio");
            }
            
            List<Cliente> clientes = new ArrayList<>();
            
            switch (criterio.toLowerCase()) {
                case "nombre":
                    clientes = clienteDAO.buscarClientesPorNombre(valor);
                    break;
                case "apellido":
                    clientes = clienteDAO.buscarClientesPorApellido(valor);
                    break;
                case "dni":
                    Cliente cliente = clienteDAO.obtenerClientePorDni(valor);
                    if (cliente != null) {
                        clientes.add(cliente);
                    }
                    break;
                default:
                    throw new Exception("Criterio de búsqueda no válido: " + criterio);
            }
            
            // Notificar evento
            controladorPrincipal.getGestorEventos().notificarCambio("CLIENTES_BUSCADOS");
            
            return clientes;
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, 
                "Error al buscar clientes: " + e.getMessage(), 
                "Error", 
                JOptionPane.ERROR_MESSAGE);
            return new ArrayList<>();
        }
    }
    
    /**
     * Comando: Obtener todos los clientes
     * @return Lista de todos los clientes
     */
    public List<Cliente> obtenerTodosLosClientes() {
        try {
            List<Cliente> clientes = clienteDAO.obtenerTodosLosClientes();
            
            // Notificar evento
            controladorPrincipal.getGestorEventos().notificarCambio("CLIENTES_OBTENIDOS");
            
            return clientes;
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, 
                "Error al obtener clientes: " + e.getMessage(), 
                "Error", 
                JOptionPane.ERROR_MESSAGE);
            return new ArrayList<>();
        }
    }
    
    /**
     * Comando: Mostrar información detallada de un cliente
     * @param dni DNI del cliente
     */
    public void mostrarInformacionCliente(String dni) {
        try {
            Cliente cliente = buscarClientePorDni(dni);
            
            if (cliente != null) {
                String informacion = String.format(
                    "INFORMACIÓN DEL CLIENTE\n\n" +
                    "Nombre: %s\n" +
                    "Apellido: %s\n" +
                    "DNI: %s\n" +
                    "Fecha de Nacimiento: %s\n" +
                    "Fecha de Registro: %s",
                    cliente.getNombre(),
                    cliente.getApellido(),
                    cliente.getDni(),
                    cliente.getFechaNacimiento().toString(),
                    cliente.getFechaRegistro().toString()
                );
                
                JOptionPane.showMessageDialog(null, informacion, "Información del Cliente", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(null, 
                    "Cliente no encontrado", 
                    "Información", 
                    JOptionPane.INFORMATION_MESSAGE);
            }
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, 
                "Error al mostrar información del cliente: " + e.getMessage(), 
                "Error", 
                JOptionPane.ERROR_MESSAGE);
        }
    }
    
    /**
     * Comando: Obtener estadísticas de clientes
     */
    public void mostrarEstadisticasClientes() {
        try {
            List<Cliente> todosLosClientes = obtenerTodosLosClientes();
            
            int totalClientes = todosLosClientes.size();
            int clientesEsteMes = 0;
            int clientesEsteAnio = 0;
            
            LocalDate ahora = LocalDate.now();
            LocalDate inicioMes = ahora.withDayOfMonth(1);
            LocalDate inicioAnio = ahora.withDayOfYear(1);
            
            for (Cliente cliente : todosLosClientes) {
                if (cliente.getFechaRegistro().isAfter(inicioMes) || cliente.getFechaRegistro().isEqual(inicioMes)) {
                    clientesEsteMes++;
                }
                if (cliente.getFechaRegistro().isAfter(inicioAnio) || cliente.getFechaRegistro().isEqual(inicioAnio)) {
                    clientesEsteAnio++;
                }
            }
            
            String estadisticas = String.format(
                "ESTADÍSTICAS DE CLIENTES\n\n" +
                "Total de clientes: %d\n" +
                "Clientes registrados este mes: %d\n" +
                "Clientes registrados este año: %d",
                totalClientes,
                clientesEsteMes,
                clientesEsteAnio
            );
            
            JOptionPane.showMessageDialog(null, estadisticas, "Estadísticas de Clientes", JOptionPane.INFORMATION_MESSAGE);
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, 
                "Error al obtener estadísticas: " + e.getMessage(), 
                "Error", 
                JOptionPane.ERROR_MESSAGE);
        }
    }
    
    /**
     * Implementación del patrón Observer
     * @param evento Evento recibido
     */
    @Override
    public void actualizar(String evento) {
        switch (evento) {
            case "SISTEMA_INICIADO":
                // Inicializar controlador de clientes si es necesario
                break;
            case "SISTEMA_CERRANDO":
                // Guardar estado del controlador de clientes
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
