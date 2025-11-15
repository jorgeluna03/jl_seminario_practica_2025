package dao;

import modelo.Cliente;
import util.ConexionMySQL;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ClienteDAO {
    
    public Cliente obtenerClientePorDni(String dniBuscado) {
        Cliente cliente = null;
        // Buscar por dniCliente pero obtener idCliente (PK) para usarlo en turnero
        String query = "SELECT idCliente, dniCliente, nombre, apellido, fechaNacimiento, fechaIngreso FROM sistemaatenciondiferenciada.cliente WHERE dniCliente = ? ";
       
        try (Connection conn = ConexionMySQL.conectar();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, dniBuscado);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                cliente = new Cliente(
                        rs.getString("idCliente"),
                        rs.getString("nombre"),
                        rs.getString("apellido"),
                        rs.getDate("fechaNacimiento").toLocalDate(),
                        rs.getString("dniCliente"),
                        rs.getDate("fechaIngreso").toLocalDate()
                );
            }

        } catch (SQLException e1) {
           System.err.println("Error al consultar cliente: " + e1.getMessage());
           e1.printStackTrace();
        }

        return cliente;
    }
    
    /**
     * Obtiene un cliente por su idCliente (PK)
     */
    public Cliente obtenerClientePorIdCliente(String idCliente) {
        Cliente cliente = null;
        String query = "SELECT idCliente, dniCliente, nombre, apellido, fechaNacimiento, fechaIngreso FROM sistemaatenciondiferenciada.cliente WHERE idCliente = ? ";
       
        try (Connection conn = ConexionMySQL.conectar();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, idCliente);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                cliente = new Cliente(
                        rs.getString("idCliente"),
                        rs.getString("nombre"),
                        rs.getString("apellido"),
                        rs.getDate("fechaNacimiento").toLocalDate(),
                        rs.getString("dniCliente"),
                        rs.getDate("fechaIngreso").toLocalDate()
                );
            }

        } catch (SQLException e1) {
           System.err.println("Error al consultar cliente por idCliente: " + e1.getMessage());
           e1.printStackTrace();
        }

        return cliente;
    }
    
    public boolean guardarCliente(Cliente cliente) {
        String query = "INSERT INTO Cliente (dni, nombre, apellido, fechaNacimiento, fechaIngreso) VALUES (?, ?, ?, ?, ?)";
        
        try (Connection conn = ConexionMySQL.conectar();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            
            stmt.setString(1, cliente.getDni());
            stmt.setString(2, cliente.getNombre());
            stmt.setString(3, cliente.getApellido());
            stmt.setDate(4, Date.valueOf(cliente.getFechaNacimiento()));
            stmt.setDate(5, Date.valueOf(cliente.getFechaIngreso()));
            
            int filasAfectadas = stmt.executeUpdate();
            return filasAfectadas > 0;
            
        } catch (SQLException e) {
            System.err.println("Error al guardar cliente: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
    
    public boolean actualizarCliente(Cliente cliente) {
        String query = "UPDATE Cliente SET nombre = ?, apellido = ?, fechaNacimiento = ?, fechaIngreso = ? WHERE dni = ?";
        
        try (Connection conn = ConexionMySQL.conectar();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            
            stmt.setString(1, cliente.getNombre());
            stmt.setString(2, cliente.getApellido());
            stmt.setDate(3, Date.valueOf(cliente.getFechaNacimiento()));
            stmt.setDate(4, Date.valueOf(cliente.getFechaIngreso()));
            stmt.setString(5, cliente.getDni());
            
            int filasAfectadas = stmt.executeUpdate();
            return filasAfectadas > 0;
            
        } catch (SQLException e) {
            System.err.println("Error al actualizar cliente: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
    
    public boolean eliminarCliente(String dni) {
        String query = "DELETE FROM Cliente WHERE dni = ?";
        
        try (Connection conn = ConexionMySQL.conectar();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            
            stmt.setString(1, dni);
            int filasAfectadas = stmt.executeUpdate();
            return filasAfectadas > 0;
            
        } catch (SQLException e) {
            System.err.println("Error al eliminar cliente: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
    
    public List<Cliente> buscarClientesPorNombre(String nombre) {
        List<Cliente> clientes = new ArrayList<>();
        String query = "SELECT dni, nombre, apellido, fechaNacimiento, fechaIngreso FROM Cliente WHERE nombre LIKE ?";
        
        try (Connection conn = ConexionMySQL.conectar();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            
            stmt.setString(1, "%" + nombre + "%");
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                Cliente cliente = new Cliente(
                    rs.getString("nombre"),
                    rs.getString("apellido"),
                    rs.getDate("fechaNacimiento").toLocalDate(),
                    rs.getString("dni"),
                    rs.getDate("fechaIngreso").toLocalDate()
                );
                clientes.add(cliente);
            }
            
        } catch (SQLException e) {
            System.err.println("Error al buscar clientes por nombre: " + e.getMessage());
            e.printStackTrace();
        }
        
        return clientes;
    }
    
    public List<Cliente> buscarClientesPorApellido(String apellido) {
        List<Cliente> clientes = new ArrayList<>();
        String query = "SELECT dni, nombre, apellido, fechaNacimiento, fechaIngreso FROM Cliente WHERE apellido LIKE ?";
        
        try (Connection conn = ConexionMySQL.conectar();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            
            stmt.setString(1, "%" + apellido + "%");
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                Cliente cliente = new Cliente(
                    rs.getString("nombre"),
                    rs.getString("apellido"),
                    rs.getDate("fechaNacimiento").toLocalDate(),
                    rs.getString("dni"),
                    rs.getDate("fechaIngreso").toLocalDate()
                );
                clientes.add(cliente);
            }
            
        } catch (SQLException e) {
            System.err.println("Error al buscar clientes por apellido: " + e.getMessage());
            e.printStackTrace();
        }
        
        return clientes;
    }
    
    public List<Cliente> obtenerTodosLosClientes() {
        List<Cliente> clientes = new ArrayList<>();
        String query = "SELECT dni, nombre, apellido, fechaNacimiento, fechaIngreso FROM Cliente";
        
        try (Connection conn = ConexionMySQL.conectar();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                Cliente cliente = new Cliente(
                    rs.getString("nombre"),
                    rs.getString("apellido"),
                    rs.getDate("fechaNacimiento").toLocalDate(),
                    rs.getString("dni"),
                    rs.getDate("fechaIngreso").toLocalDate()
                );
                clientes.add(cliente);
            }
            
        } catch (SQLException e) {
            System.err.println("Error al obtener todos los clientes: " + e.getMessage());
            e.printStackTrace();
        }
        
        return clientes;
    }
}