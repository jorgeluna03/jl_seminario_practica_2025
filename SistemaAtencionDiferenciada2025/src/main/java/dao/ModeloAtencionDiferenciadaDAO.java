package dao;

import modelo.ModeloAtencionDiferenciada;
import util.ConexionMySQL;

import java.sql.*;
import java.util.Optional;

public class ModeloAtencionDiferenciadaDAO {
    
    public Optional<String> obtenerSegmentoPorDni(String dni) {
        String query = "SELECT segmento FROM sistemaatenciondiferenciada.modeloatenciondiferenciada " +
                      "WHERE idCliente = ? ORDER BY idModeloAtencion DESC LIMIT 1";
        
        try (Connection conn = ConexionMySQL.conectar();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            
            stmt.setString(1, dni);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                String segmento = rs.getString("segmento");
                if (segmento != null && !segmento.trim().isEmpty()) {
                    return Optional.of(segmento.trim().toUpperCase());
                }
            }
            
        } catch (SQLException e) {
            System.err.println("Error al consultar segmento del cliente: " + e.getMessage());
            e.printStackTrace();
        }
        
        return Optional.empty();
    }
    
    public Integer obtenerSegmentoScorePorDni(String dni) {
        Optional<String> segmentoOpt = obtenerSegmentoPorDni(dni);
        
        if (segmentoOpt.isPresent()) {
            String segmento = segmentoOpt.get();
            
            switch (segmento) {
                case "ALTO":
                    return 1;
                case "MEDIO ALTO":
                    return 2;
                case "MEDIO":
                    return 3;
                case "MEDIO BAJO":
                    return 4;
                case "BAJO":
                    return 5;
                default:
                    return 5; // Por defecto, el menos prioritario
            }
        }
        
        return 5; // Si no tiene segmento, asumimos BAJO (menos prioritario)
    }
    
    public Optional<ModeloAtencionDiferenciada> buscarPorDni(String dni) {
        ModeloAtencionDiferenciada modelo = null;
        String query = "SELECT frecuencia, recencia, monto, idModelo, idCliente, segmento " +
                      "FROM sistemaatenciondiferenciada.modeloatenciondiferenciada " +
                      "WHERE idCliente = ? ORDER BY idModeloAtencion DESC LIMIT 1";
        
        try (Connection conn = ConexionMySQL.conectar();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            
            stmt.setString(1, dni);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                // Nota: Este modelo simplificado requiere instanciar Cliente e idModelo
                // Por ahora, creamos un modelo básico
                modelo = new ModeloAtencionDiferenciada();
                modelo.setFrecuencia(rs.getInt("frecuencia"));
                modelo.setRecencia(rs.getInt("recencia"));
                modelo.setMonto(rs.getDouble("monto"));
                
                String segmento = rs.getString("segmento");
                if (segmento != null) {
                    modelo.setSegmento(segmento.trim().toUpperCase());
                }
                
                // Nota: idModelo y cliente requerirían consultas adicionales o cambios en el modelo
                // Por ahora, dejamos los valores por defecto
            }
            
        } catch (SQLException e) {
            System.err.println("Error al consultar modelo de atención diferenciada: " + e.getMessage());
            e.printStackTrace();
        }
        
        return Optional.ofNullable(modelo);
    }
    
    /**
     * Obtiene el idModeloAtencion más reciente para un cliente por su idCliente
     */
    public Optional<Integer> obtenerIdModeloAtencionPorIdCliente(String idCliente) {
        String query = "SELECT idModeloAtencion " +
                      "FROM sistemaatenciondiferenciada.modeloatenciondiferenciada " +
                      "WHERE idCliente = ? ORDER BY idModeloAtencion DESC LIMIT 1";
        
        try (Connection conn = ConexionMySQL.conectar();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            
            stmt.setString(1, idCliente);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                return Optional.of(rs.getInt("idModeloAtencion"));
            }
            
        } catch (SQLException e) {
            System.err.println("Error al consultar idModeloAtencion por idCliente: " + e.getMessage());
            e.printStackTrace();
        }
        
        return Optional.empty();
    }
}

