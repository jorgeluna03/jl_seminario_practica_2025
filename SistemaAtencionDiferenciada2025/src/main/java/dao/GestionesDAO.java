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
        // Ordenar por nombreGestion (ORDER BY 1 significa ordenar por la primera columna, que es nombreGestion)
        String query = "SELECT nombreGestion, idGestion, prioridad, orden, esDiferencial " +
                      "FROM sistemaatenciondiferenciada.gestiones ORDER BY nombreGestion";
        
        System.out.println("=== LISTANDO TODAS LAS GESTIONES ===");
        System.out.println("SQL: " + query);
        
        try (Connection conn = ConexionMySQL.conectar();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            
            int contador = 0;
            while (rs.next()) {
                try {
                    contador++;
                    
                    // Leer campos de forma segura
                    int idGestion = rs.getInt("idGestion");
                    String nombreGestion = rs.getString("nombreGestion");
                    int prioridad = rs.getInt("prioridad");
                    int orden = rs.getInt("orden");
                    boolean esDiferencial = rs.getBoolean("esDiferencial");
                    
                    // Verificar si hay valores null problemáticos
                    if (nombreGestion == null) {
                        System.err.println("⚠ ADVERTENCIA: Gestion " + contador + " tiene nombreGestion NULL, usando 'SIN NOMBRE'");
                        nombreGestion = "SIN NOMBRE (idGestion=" + idGestion + ")";
                    }
                    
                    Gestiones gestion = new Gestiones(
                        idGestion,
                        nombreGestion,
                        prioridad,
                        orden,
                        esDiferencial
                    );
                    gestiones.add(gestion);
                    System.out.println("  Gestion " + contador + ": idGestion=" + gestion.getIdGestion() + 
                                     ", nombreGestion=" + gestion.getNombreGestion() + 
                                     " (length=" + (nombreGestion != null ? nombreGestion.length() : 0) + ")");
                    
                } catch (SQLException e) {
                    System.err.println("❌ ERROR al leer gestion " + contador + ":");
                    System.err.println("   Mensaje: " + e.getMessage());
                    System.err.println("   SQL State: " + e.getSQLState());
                    System.err.println("   Error Code: " + e.getErrorCode());
                    e.printStackTrace();
                    // Continuar con la siguiente fila en lugar de detener el bucle
                } catch (Exception e) {
                    System.err.println("❌ ERROR INESPERADO al leer gestion " + contador + ":");
                    System.err.println("   Mensaje: " + e.getMessage());
                    e.printStackTrace();
                    // Continuar con la siguiente fila
                }
            }
            
            System.out.println("Total de gestiones encontradas: " + contador);
            System.out.println("Total de gestiones agregadas a la lista: " + gestiones.size());
            
        } catch (SQLException e) {
            System.err.println("❌ ERROR al listar gestiones: " + e.getMessage());
            System.err.println("SQL State: " + e.getSQLState());
            System.err.println("Error Code: " + e.getErrorCode());
            e.printStackTrace();
        }
        
        System.out.println("Total de gestiones retornadas: " + gestiones.size());
        return gestiones;
    }
}

