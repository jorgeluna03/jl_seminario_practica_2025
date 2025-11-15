package dao;

import modelo.Gestiones;
import util.ConexionMySQL;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class GestionesDAO {
    
    public Optional<Gestiones> buscarPorId(int idGestion) {
        Gestiones gestion = null;
        String query = "SELECT idGestion, nombreGestion, prioridad, orden, esDiferencial " +
                      "FROM sistemaatenciondiferenciada.gestiones WHERE idGestion = ?";
        
        try (Connection conn = ConexionMySQL.conectar();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            
            stmt.setInt(1, idGestion);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                gestion = new Gestiones(
                    rs.getInt("idGestion"),
                    rs.getString("nombreGestion"),
                    rs.getInt("prioridad"),
                    rs.getInt("orden"),
                    rs.getBoolean("esDiferencial")
                );
            }
            
        } catch (SQLException e) {
            System.err.println("Error al consultar gestión: " + e.getMessage());
            e.printStackTrace();
        }
        
        return Optional.ofNullable(gestion);
    }
    
    public Integer obtenerPrioridadPorIdGestion(int idGestion) {
        String query = "SELECT prioridad FROM sistemaatenciondiferenciada.gestiones WHERE idGestion = ?";
        
        try (Connection conn = ConexionMySQL.conectar();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            
            stmt.setInt(1, idGestion);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                return rs.getInt("prioridad");
            }
            
        } catch (SQLException e) {
            System.err.println("Error al consultar prioridad de gestión: " + e.getMessage());
            e.printStackTrace();
        }
        
        return null;
    }
    
    public List<Gestiones> listarTodas() {
        List<Gestiones> gestiones = new ArrayList<>();
        String query = "SELECT idGestion, nombreGestion, prioridad, orden, esDiferencial " +
                      "FROM sistemaatenciondiferenciada.gestiones ORDER BY prioridad ASC, orden ASC";
        
        try (Connection conn = ConexionMySQL.conectar();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            
            while (rs.next()) {
                Gestiones gestion = new Gestiones(
                    rs.getInt("idGestion"),
                    rs.getString("nombreGestion"),
                    rs.getInt("prioridad"),
                    rs.getInt("orden"),
                    rs.getBoolean("esDiferencial")
                );
                gestiones.add(gestion);
            }
            
        } catch (SQLException e) {
            System.err.println("Error al listar gestiones: " + e.getMessage());
            e.printStackTrace();
        }
        
        return gestiones;
    }
}

