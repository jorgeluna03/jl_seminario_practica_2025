package dao;

import modelo.BoxAtencion;
import util.ConexionMySQL;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class BoxAtencionDAO {
    
    public boolean registrar(BoxAtencion box) {
        String sql = "INSERT INTO box_atencion (idBox, nombre, estado, idColaborador) VALUES (?, ?, ?, ?)";
        try (Connection conn = ConexionMySQL.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setInt(1, box.getIdBox());
            ps.setString(2, box.getNombre());
            ps.setString(3, box.getEstado());
            if (box.getColaboradorAsignado() != null) {
                ps.setInt(4, box.getColaboradorAsignado().getId());
            } else {
                ps.setNull(4, Types.INTEGER);
            }
            
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error al registrar box: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
    
    public Optional<BoxAtencion> buscarPorId(int idBox) {
        String sql = "SELECT idBox, nombre, estado, idColaborador FROM box_atencion WHERE idBox = ?";
        try (Connection conn = ConexionMySQL.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setInt(1, idBox);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    BoxAtencion box = new BoxAtencion();
                    box.setIdBox(rs.readInt("idBox"));
                    box.setNombre(rs.getString("nombre"));
                    box.setEstado(rs.getString("estado"));
                    // Colaborador opcional: mantener null si no hay tabla/DAO de colaborador aún
                    return Optional.of(box);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al buscar box: " + e.getMessage());
            e.printStackTrace();
        }
        return Optional.empty();
    }
    
    public List<BoxAtencion> listarTodos() {
        List<BoxAtencion> lista = new ArrayList<>();
        String sql = "SELECT idBox, nombre, estado, idColaborador FROM box_atencion ORDER BY idBox";
        try (Connection conn = ConexionMySQL.conectar();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            
            while (rs.next()) {
                BoxAtencion box = new BoxAtencion();
                box.setIdBox(rs.getInt("idBox"));
                box.setNombre(rs.getString("nombre"));
                box.setEstado(rs.getString("estado"));
                // Colaborador opcional
                lista.add(box);
            }
        } catch (SQLException e) {
            System.err.println("Error al listar boxes: " + e.getMessage());
            e.printStackTrace();
        }
        return lista;
    }
    
    public boolean actualizarEstado(int idBox, String estado) {
        String sql = "UPDATE box_atencion SET estado = ? WHERE idBox = ?";
        try (Connection conn = ConexionMySQL.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setString(1, estado);
            ps.setInt(2, idBox);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error al actualizar estado de box: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
    
    public boolean asignarColaborador(int idBox, Integer idColaborador) {
        String sql = "UPDATE box_atencion SET idColaborador = ? WHERE idBox = ?";
        try (Connection conn = ConexionMySQL.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            if (idColaborador != null) {
                ps.setInt(1, idColaborador);
            } else {
                ps.setNull(1, Types.INTEGER);
            }
            ps.setInt(2, idBox);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error al asignar colaborador a box: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
}


