package dao;

import modelo.Cliente;
import modelo.Turnero;
import util.ConexionMySQL;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class TurneroDAO {
    
    public boolean guardarTurnero(Turnero turno) {
        String sql = "INSERT INTO turno (idTurno, codigoTurno, dniCliente, fecha, estado, idBox) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = ConexionMySQL.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setInt(1, turno.getIdTurnero());
            ps.setString(2, turno.getCodigoTurno());
            ps.setString(3, turno.getCliente() != null ? turno.getCliente().getDni() : null);
            ps.setDate(4, Date.valueOf(turno.getFecha() != null ? turno.getFecha() : LocalDate.now()));
            ps.setString(5, turno.getEstado());
            if (turno.getIdBox() != null) {
                ps.setInt(6, turno.getIdBox());
            } else {
                ps.setNull(6, Types.INTEGER);
            }
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error al guardar turno: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
    
    public Optional<Turnero> buscarPorCodigo(String codigoTurno) {
        String sql = "SELECT idTurno, codigoTurno, dniCliente, fecha, estado, idBox FROM turno WHERE codigoTurno = ?";
        try (Connection conn = ConexionMySQL.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setString(1, codigoTurno);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Turnero t = new Turnero();
                    t.setIdTurnero(rs.getInt("idTurno"));
                    t.setCodigoTurno(rs.getString("codigoTurno"));
                    t.setFecha(rs.getDate("fecha").toLocalDate());
                    t.setEstado(rs.getString("estado"));
                    int idBox = rs.getInt("idBox");
                    t.setIdBox(rs.wasNull() ? null : idBox);
                    
                    // Resolver cliente por DNI si se requiere (usando ClienteDAO)
                    String dni = rs.getString("dniCliente");
                    if (dni != null) {
                        ClienteDAO cdao = new ClienteDAO();
                        Cliente c = cdao.obtenerClientePorDni(dni);
                        t.setCliente(c);
                    }
                    return Optional.of(t);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al buscar turno por código: " + e.getMessage());
            e.printStackTrace();
        }
        return Optional.empty();
    }
    
    public boolean actualizarEstado(String codigoTurno, String nuevoEstado) {
        String sql = "UPDATE turno SET estado = ? WHERE codigoTurno = ?";
        try (Connection conn = ConexionMySQL.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setString(1, nuevoEstado);
            ps.setString(2, codigoTurno);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error al actualizar estado de turno: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
    
    public boolean asignarBox(String codigoTurno, Integer idBox) {
        String sql = "UPDATE turno SET idBox = ? WHERE codigoTurno = ?";
        try (Connection conn = ConexionMySQL.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            if (idBox != null) {
                ps.setInt(1, idBox);
            } else {
                ps.setNull(1, Types.INTEGER);
            }
            ps.setString(2, codigoTurno);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error al asignar box a turno: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
    
    public List<Turnero> listarEnCola() {
        List<Turnero> lista = new ArrayList<>();
        String sql = "SELECT idTurno, codigoTurno, dniCliente, fecha, estado, idBox " +
                     "FROM turno WHERE estado IN ('EN_ESPERA','LLAMADO','EN_ATENCION') " +
                     "ORDER BY fecha ASC, idTurno ASC";
        try (Connection conn = ConexionMySQL.conectar();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            
            while (rs.next()) {
                Turnero t = new Turnero();
                t.setIdTurnero(rs.getInt("idTurno"));
                t.setCodigoTurno(rs.getString("codigoTurno"));
                t.setFecha(rs.getDate("fecha").toLocalDate());
                t.setEstado(rs.getString("estado"));
                int idBox = rs.getInt("idBox");
                t.setIdBox(rs.wasNull() ? null : idBox);
                
                String dni = rs.getString("dniCliente");
                if (dni != null) {
                    ClienteDAO cdao = new ClienteDAO();
                    Cliente c = cdao.obtenerClientePorDni(dni);
                    t.setCliente(c);
                }
                lista.add(t);
            }
        } catch (SQLException e) {
            System.err.println("Error al listar turnos en cola: " + e.getMessage());
            e.printStackTrace();
        }
        return lista;
    }
}


