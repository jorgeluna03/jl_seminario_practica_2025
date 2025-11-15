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
    
    /**
     * Obtiene el máximo idTurnero de la base de datos
     * @return El máximo idTurnero, o 0 si no hay turnos
     */
    public int obtenerMaximoIdTurnero() {
        String sql = "SELECT MAX(idTurnero) as maxId FROM sistemaatenciondiferenciada.turnero";
        try (Connection conn = ConexionMySQL.conectar();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            
            if (rs.next()) {
                int maxId = rs.getInt("maxId");
                return rs.wasNull() ? 0 : maxId;
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener máximo idTurnero: " + e.getMessage());
            e.printStackTrace();
        }
        return 0;
    }
    
    /**
     * Obtiene el máximo número de código de turno de la base de datos
     * Los códigos tienen formato "N0001", "P0002", etc.
     * Extrae el número al final del código y retorna el máximo
     * @return El máximo número de código de turno, o 0 si no hay turnos
     */
    public int obtenerMaximoNumeroCodigoTurno() {
        String sql = "SELECT codigoTurno FROM sistemaatenciondiferenciada.turnero ORDER BY codigoTurno DESC LIMIT 1";
        try (Connection conn = ConexionMySQL.conectar();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            
            if (rs.next()) {
                String codigoTurno = rs.getString("codigoTurno");
                if (codigoTurno != null && !codigoTurno.trim().isEmpty()) {
                    // Extraer el número del código (ej: "N0001" -> 1, "P0025" -> 25)
                    try {
                        // Remover el prefijo (N o P) y convertir a número
                        String numeroStr = codigoTurno.substring(1); // Saltar el primer carácter (N o P)
                        int numero = Integer.parseInt(numeroStr);
                        System.out.println("Último código de turno en BD: " + codigoTurno + " -> número: " + numero);
                        return numero;
                    } catch (NumberFormatException | StringIndexOutOfBoundsException e) {
                        System.err.println("Error al parsear código de turno: " + codigoTurno);
                        // Buscar todos los códigos y extraer el máximo número
                        return obtenerMaximoNumeroDeTodosLosCodigos(conn);
                    }
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener máximo código de turno: " + e.getMessage());
            e.printStackTrace();
        }
        return 0;
    }
    
    /**
     * Obtiene el máximo número de todos los códigos de turno
     * Si el último código no se puede parsear, busca en todos los códigos
     */
    private int obtenerMaximoNumeroDeTodosLosCodigos(Connection conn) {
        String sql = "SELECT codigoTurno FROM sistemaatenciondiferenciada.turnero WHERE codigoTurno IS NOT NULL";
        int maxNumero = 0;
        try (PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            
            while (rs.next()) {
                String codigoTurno = rs.getString("codigoTurno");
                if (codigoTurno != null && codigoTurno.length() > 1) {
                    try {
                        String numeroStr = codigoTurno.substring(1);
                        int numero = Integer.parseInt(numeroStr);
                        if (numero > maxNumero) {
                            maxNumero = numero;
                        }
                    } catch (NumberFormatException e) {
                        // Ignorar códigos que no se pueden parsear
                    }
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener máximo número de códigos: " + e.getMessage());
        }
        return maxNumero;
    }
    
    public boolean guardarTurnero(Turnero turno) {
        // Validaciones previas
        if (turno == null) {
            System.err.println("ERROR: Turnero es null");
            return false;
        }
        
        if (turno.getCodigoTurno() == null || turno.getCodigoTurno().trim().isEmpty()) {
            System.err.println("ERROR: Código de turno es null o vacío");
            return false;
        }
        
        if (turno.getCliente() == null || turno.getCliente().getIdCliente() == null) {
            System.err.println("ERROR: Cliente o idCliente es null");
            return false;
        }
        
        System.out.println("=== INTENTANDO GUARDAR TURNO ===");
        System.out.println("Código: " + turno.getCodigoTurno());
        System.out.println("idCliente: " + turno.getCliente().getIdCliente());
        System.out.println("DNI Cliente: " + turno.getCliente().getDni());
        System.out.println("Estado: " + turno.getEstado());
        System.out.println("idGestion: " + turno.getIdGestion());
        System.out.println("Prioridad: " + turno.getPrioridad());
        System.out.println("SegmentoScore: " + turno.getSegmentoScore());
        System.out.println("Score: " + turno.getScore());
        System.out.println("idBox: " + turno.getIdBox());
        
        // Primero intentar con todos los campos nuevos (incluyendo orden)
        String sql = "INSERT INTO sistemaatenciondiferenciada.turnero " +
                     "(idTurnero, codigoTurno, idCliente, fecha, estado, idBox, idGestion, prioridad, segmentoScore, score, orden) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        
        System.out.println("SQL: " + sql);
        
        try (Connection conn = ConexionMySQL.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            System.out.println("Conexión establecida correctamente");
            
            ps.setInt(1, turno.getIdTurnero());
            ps.setString(2, turno.getCodigoTurno());
            // Usar idCliente (PK) en lugar de dni
            ps.setString(3, turno.getCliente() != null ? turno.getCliente().getIdCliente() : null);
            ps.setDate(4, Date.valueOf(turno.getFecha() != null ? turno.getFecha() : LocalDate.now()));
            ps.setString(5, turno.getEstado() != null ? turno.getEstado() : "EN_ESPERA");
            
            // idBox
            if (turno.getIdBox() != null) {
                ps.setInt(6, turno.getIdBox());
            } else {
                ps.setNull(6, Types.INTEGER);
            }
            
            // idGestion
            if (turno.getIdGestion() != null) {
                ps.setInt(7, turno.getIdGestion());
            } else {
                ps.setNull(7, Types.INTEGER);
            }
            
            // prioridad
            if (turno.getPrioridad() != null) {
                ps.setInt(8, turno.getPrioridad());
            } else {
                ps.setNull(8, Types.INTEGER);
            }
            
            // segmentoScore
            if (turno.getSegmentoScore() != null) {
                ps.setInt(9, turno.getSegmentoScore());
            } else {
                ps.setNull(9, Types.INTEGER);
            }
            
            // score
            if (turno.getScore() != null) {
                ps.setInt(10, turno.getScore());
            } else {
                ps.setNull(10, Types.INTEGER);
            }
            
            // orden (puede ser NULL inicialmente, se asignará al reordenar)
            if (turno.getOrden() != null) {
                ps.setInt(11, turno.getOrden());
            } else {
                ps.setNull(11, Types.INTEGER);
            }
            
            System.out.println("Ejecutando INSERT...");
            System.out.println("Orden inicial: " + turno.getOrden());
            int filasAfectadas = ps.executeUpdate();
            System.out.println("✓ Filas afectadas: " + filasAfectadas);
            
            if (filasAfectadas > 0) {
                System.out.println("✓✓✓ TURNO GUARDADO EXITOSAMENTE EN BD ✓✓✓");
                return true;
            } else {
                System.err.println("⚠ ADVERTENCIA: executeUpdate() retornó 0 filas afectadas");
                return false;
            }
            
        } catch (SQLException e) {
            System.err.println("❌ ERROR SQL al guardar turno:");
            System.err.println("   Mensaje: " + e.getMessage());
            System.err.println("   SQL State: " + e.getSQLState());
            System.err.println("   Error Code: " + e.getErrorCode());
            e.printStackTrace();
            
            // Si falla, intentar con solo los campos básicos (por si no se ejecutó el script SQL)
            System.out.println("\n--- Intentando guardar con campos básicos ---");
            try {
                boolean resultado = guardarTurneroBasico(turno);
                if (resultado) {
                    System.out.println("✓ Guardado con campos básicos (faltan campos nuevos en BD)");
                }
                return resultado;
            } catch (Exception e2) {
                System.err.println("❌ Error también al guardar con campos básicos: " + e2.getMessage());
                e2.printStackTrace();
                return false;
            }
        } catch (Exception e) {
            System.err.println("❌ ERROR GENERAL al guardar turno: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
    
    /**
     * Método alternativo para guardar solo con campos básicos (si la tabla no tiene los campos nuevos)
     */
    private boolean guardarTurneroBasico(Turnero turno) {
        String sql = "INSERT INTO sistemaatenciondiferenciada.turnero " +
                     "(idTurnero, codigoTurno, idCliente, fecha, estado) " +
                     "VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = ConexionMySQL.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setInt(1, turno.getIdTurnero());
            ps.setString(2, turno.getCodigoTurno());
            // Usar idCliente (PK) en lugar de dni
            ps.setString(3, turno.getCliente() != null ? turno.getCliente().getIdCliente() : null);
            ps.setDate(4, Date.valueOf(turno.getFecha() != null ? turno.getFecha() : LocalDate.now()));
            ps.setString(5, turno.getEstado() != null ? turno.getEstado() : "EN_ESPERA");
            
            int filasAfectadas = ps.executeUpdate();
            System.out.println("Turno guardado con campos básicos. Filas afectadas: " + filasAfectadas);
            return filasAfectadas > 0;
        } catch (SQLException e) {
            System.err.println("Error al guardar turno básico: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
    
    public Optional<Turnero> buscarPorCodigo(String codigoTurno) {
        String sql = "SELECT idTurnero, codigoTurno, idCliente, fecha, estado, idBox, idGestion, prioridad, segmentoScore, score " +
                     "FROM sistemaatenciondiferenciada.turnero WHERE codigoTurno = ?";
        try (Connection conn = ConexionMySQL.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setString(1, codigoTurno);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Turnero t = new Turnero();
                    t.setIdTurnero(rs.getInt("idTurnero"));
                    t.setCodigoTurno(rs.getString("codigoTurno"));
                    t.setFecha(rs.getDate("fecha").toLocalDate());
                    t.setEstado(rs.getString("estado"));
                    
                    // idBox
                    int idBox = rs.getInt("idBox");
                    t.setIdBox(rs.wasNull() ? null : idBox);
                    
                    // idGestion
                    int idGestion = rs.getInt("idGestion");
                    t.setIdGestion(rs.wasNull() ? null : idGestion);
                    
                    // prioridad
                    int prioridad = rs.getInt("prioridad");
                    t.setPrioridad(rs.wasNull() ? null : prioridad);
                    
                    // segmentoScore
                    int segmentoScore = rs.getInt("segmentoScore");
                    t.setSegmentoScore(rs.wasNull() ? null : segmentoScore);
                    
                    // score
                    int score = rs.getInt("score");
                    t.setScore(rs.wasNull() ? null : score);
                    
                    // Resolver cliente por idCliente (PK) usando ClienteDAO
                    String idCliente = rs.getString("idCliente");
                    if (idCliente != null) {
                        ClienteDAO cdao = new ClienteDAO();
                        Cliente c = cdao.obtenerClientePorIdCliente(idCliente);
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
        String sql = "UPDATE sistemaatenciondiferenciada.turnero SET estado = ? WHERE codigoTurno = ?";
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
        System.out.println("=== ASIGNANDO BOX A TURNO ===");
        System.out.println("Código Turno: " + codigoTurno);
        System.out.println("idBox: " + idBox);
        
        String sql = "UPDATE sistemaatenciondiferenciada.turnero SET idBox = ? WHERE codigoTurno = ?";
        System.out.println("SQL: " + sql);
        
        try (Connection conn = ConexionMySQL.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            System.out.println("Conexión establecida correctamente");
            
            if (idBox != null) {
                ps.setInt(1, idBox);
                System.out.println("idBox asignado: " + idBox);
            } else {
                ps.setNull(1, Types.INTEGER);
                System.out.println("idBox es NULL (se asignará NULL)");
            }
            ps.setString(2, codigoTurno);
            System.out.println("Buscando turno con código: " + codigoTurno);
            
            int filasAfectadas = ps.executeUpdate();
            System.out.println("Filas afectadas al asignar box: " + filasAfectadas);
            
            if (filasAfectadas > 0) {
                System.out.println("✓✓✓ BOX ASIGNADO EXITOSAMENTE ✓✓✓");
                return true;
            } else {
                System.err.println("⚠ ADVERTENCIA: No se encontró turno con código " + codigoTurno + " para asignar box");
                // Verificar si el turno existe
                String sqlVerificar = "SELECT codigoTurno FROM sistemaatenciondiferenciada.turnero WHERE codigoTurno = ?";
                try (PreparedStatement psVerificar = conn.prepareStatement(sqlVerificar)) {
                    psVerificar.setString(1, codigoTurno);
                    try (java.sql.ResultSet rs = psVerificar.executeQuery()) {
                        if (rs.next()) {
                            System.out.println("El turno " + codigoTurno + " SÍ existe en la BD");
                        } else {
                            System.err.println("❌ El turno " + codigoTurno + " NO existe en la BD");
                        }
                    }
                }
                return false;
            }
        } catch (SQLException e) {
            System.err.println("❌ ERROR SQL al asignar box a turno:");
            System.err.println("   Mensaje: " + e.getMessage());
            System.err.println("   SQL State: " + e.getSQLState());
            System.err.println("   Error Code: " + e.getErrorCode());
            e.printStackTrace();
            return false;
        } catch (Exception e) {
            System.err.println("❌ ERROR GENERAL al asignar box a turno: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
    
    public List<Turnero> listarEnCola() {
        List<Turnero> lista = new ArrayList<>();
        String sql = "SELECT idTurnero, codigoTurno, idCliente, fecha, estado, idBox, idGestion, prioridad, segmentoScore, score, orden " +
                     "FROM sistemaatenciondiferenciada.turnero " +
                     "WHERE estado IN ('EN_ESPERA','LLAMADO','EN_ATENCION') " +
                     "ORDER BY COALESCE(score, 9999) ASC, fecha ASC, idTurnero ASC";
        try (Connection conn = ConexionMySQL.conectar();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            
            while (rs.next()) {
                Turnero t = new Turnero();
                t.setIdTurnero(rs.getInt("idTurnero"));
                t.setCodigoTurno(rs.getString("codigoTurno"));
                t.setFecha(rs.getDate("fecha").toLocalDate());
                t.setEstado(rs.getString("estado"));
                
                // idBox
                int idBox = rs.getInt("idBox");
                t.setIdBox(rs.wasNull() ? null : idBox);
                
                // idGestion
                int idGestion = rs.getInt("idGestion");
                t.setIdGestion(rs.wasNull() ? null : idGestion);
                
                // prioridad
                int prioridad = rs.getInt("prioridad");
                t.setPrioridad(rs.wasNull() ? null : prioridad);
                
                // segmentoScore
                int segmentoScore = rs.getInt("segmentoScore");
                t.setSegmentoScore(rs.wasNull() ? null : segmentoScore);
                
                // score
                int score = rs.getInt("score");
                t.setScore(rs.wasNull() ? null : score);
                
                // orden
                int orden = rs.getInt("orden");
                t.setOrden(rs.wasNull() ? null : orden);
                
                // Resolver cliente por idCliente (PK)
                String idCliente = rs.getString("idCliente");
                if (idCliente != null) {
                    ClienteDAO cdao = new ClienteDAO();
                    Cliente c = cdao.obtenerClientePorIdCliente(idCliente);
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
    
    /**
     * Lista todos los turnos de un box específico ordenados por score (menor = más prioritario)
     * @param idBox ID del box
     * @return Lista de turnos ordenados por score, fecha e idTurnero
     */
    public List<Turnero> listarTurnosPorBox(Integer idBox) {
        List<Turnero> lista = new ArrayList<>();
        String sql = "SELECT idTurnero, codigoTurno, idCliente, fecha, estado, idBox, idGestion, prioridad, segmentoScore, score, orden " +
                     "FROM sistemaatenciondiferenciada.turnero " +
                     "WHERE idBox = ? " +
                     "AND estado IN ('EN_ESPERA','LLAMADO','EN_ATENCION') " +
                     "ORDER BY COALESCE(orden, 9999) ASC, COALESCE(score, 9999) ASC, fecha ASC, idTurnero ASC";
        try (Connection conn = ConexionMySQL.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setInt(1, idBox);
            
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Turnero t = new Turnero();
                    t.setIdTurnero(rs.getInt("idTurnero"));
                    t.setCodigoTurno(rs.getString("codigoTurno"));
                    t.setFecha(rs.getDate("fecha").toLocalDate());
                    t.setEstado(rs.getString("estado"));
                    
                    int idBoxValue = rs.getInt("idBox");
                    t.setIdBox(rs.wasNull() ? null : idBoxValue);
                    
                    int idGestion = rs.getInt("idGestion");
                    t.setIdGestion(rs.wasNull() ? null : idGestion);
                    
                    int prioridad = rs.getInt("prioridad");
                    t.setPrioridad(rs.wasNull() ? null : prioridad);
                    
                    int segmentoScore = rs.getInt("segmentoScore");
                    t.setSegmentoScore(rs.wasNull() ? null : segmentoScore);
                    
                    int score = rs.getInt("score");
                    t.setScore(rs.wasNull() ? null : score);
                    
                    int orden = rs.getInt("orden");
                    t.setOrden(rs.wasNull() ? null : orden);
                    
                    String idCliente = rs.getString("idCliente");
                    if (idCliente != null) {
                        ClienteDAO cdao = new ClienteDAO();
                        Cliente c = cdao.obtenerClientePorIdCliente(idCliente);
                        t.setCliente(c);
                    }
                    lista.add(t);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al listar turnos por box: " + e.getMessage());
            e.printStackTrace();
        }
        return lista;
    }
    
    /**
     * Actualiza el orden de un turno en la cola
     * @param codigoTurno Código del turno
     * @param orden Nuevo orden (1 = primero, 2 = segundo, etc.)
     * @return true si se actualizó correctamente
     */
    public boolean actualizarOrden(String codigoTurno, Integer orden) {
        String sql = "UPDATE sistemaatenciondiferenciada.turnero SET orden = ? WHERE codigoTurno = ?";
        try (Connection conn = ConexionMySQL.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            if (orden != null) {
                ps.setInt(1, orden);
                System.out.println("Actualizando orden en BD: turno " + codigoTurno + " → orden " + orden);
            } else {
                ps.setNull(1, Types.INTEGER);
                System.out.println("Actualizando orden en BD: turno " + codigoTurno + " → orden NULL");
            }
            ps.setString(2, codigoTurno);
            int filasAfectadas = ps.executeUpdate();
            if (filasAfectadas > 0) {
                System.out.println("✓ Orden actualizado correctamente en BD para turno " + codigoTurno);
            } else {
                System.err.println("⚠ No se encontró el turno " + codigoTurno + " para actualizar su orden");
            }
            return filasAfectadas > 0;
        } catch (SQLException e) {
            System.err.println("❌ ERROR al actualizar orden de turno en BD: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
    
    /**
     * Lista solo los turnos EN_ESPERA de un box específico (para reordenamiento)
     * @param idBox ID del box
     * @return Lista de turnos EN_ESPERA ordenados por score, fecha e idTurnero
     */
    public List<Turnero> listarTurnosEnEsperaPorBox(Integer idBox) {
        List<Turnero> lista = new ArrayList<>();
        String sql = "SELECT idTurnero, codigoTurno, idCliente, fecha, estado, idBox, idGestion, prioridad, segmentoScore, score, orden " +
                     "FROM sistemaatenciondiferenciada.turnero " +
                     "WHERE idBox = ? " +
                     "AND estado = 'EN_ESPERA' " +
                     "ORDER BY COALESCE(score, 9999) ASC, fecha ASC, idTurnero ASC";
        try (Connection conn = ConexionMySQL.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setInt(1, idBox);
            
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Turnero t = new Turnero();
                    t.setIdTurnero(rs.getInt("idTurnero"));
                    t.setCodigoTurno(rs.getString("codigoTurno"));
                    t.setFecha(rs.getDate("fecha").toLocalDate());
                    t.setEstado(rs.getString("estado"));
                    
                    int idBoxValue = rs.getInt("idBox");
                    t.setIdBox(rs.wasNull() ? null : idBoxValue);
                    
                    int idGestion = rs.getInt("idGestion");
                    t.setIdGestion(rs.wasNull() ? null : idGestion);
                    
                    int prioridad = rs.getInt("prioridad");
                    t.setPrioridad(rs.wasNull() ? null : prioridad);
                    
                    int segmentoScore = rs.getInt("segmentoScore");
                    t.setSegmentoScore(rs.wasNull() ? null : segmentoScore);
                    
                    int score = rs.getInt("score");
                    t.setScore(rs.wasNull() ? null : score);
                    
                    int orden = rs.getInt("orden");
                    t.setOrden(rs.wasNull() ? null : orden);
                    
                    // Resolver cliente por idCliente (PK)
                    String idCliente = rs.getString("idCliente");
                    if (idCliente != null) {
                        ClienteDAO cdao = new ClienteDAO();
                        Cliente c = cdao.obtenerClientePorIdCliente(idCliente);
                        t.setCliente(c);
                    }
                    lista.add(t);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al listar turnos en espera por box: " + e.getMessage());
            e.printStackTrace();
        }
        return lista;
    }
    
    /**
     * Reordena solo los turnos EN_ESPERA de un box según su score
     * Los turnos LLAMADO o EN_ATENCION mantienen su orden original
     * Asigna orden 1 al más prioritario, orden 2 al siguiente, etc.
     * @param idBox ID del box
     * @return true si se reordenó correctamente
     */
    public boolean reordenarColaPorBox(Integer idBox) {
        // Obtener turnos LLAMADO y EN_ATENCION para mantener su orden
        List<Turnero> turnosEnAtencion = new ArrayList<>();
        String sqlAtencion = "SELECT idTurnero, codigoTurno, idCliente, fecha, estado, idBox, idGestion, prioridad, segmentoScore, score, orden " +
                            "FROM sistemaatenciondiferenciada.turnero " +
                            "WHERE idBox = ? " +
                            "AND estado IN ('LLAMADO', 'EN_ATENCION') " +
                            "ORDER BY COALESCE(orden, 9999) ASC, fecha ASC, idTurnero ASC";
        
        try (Connection conn = ConexionMySQL.conectar();
             PreparedStatement ps = conn.prepareStatement(sqlAtencion)) {
            
            ps.setInt(1, idBox);
            
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Turnero t = new Turnero();
                    t.setIdTurnero(rs.getInt("idTurnero"));
                    t.setCodigoTurno(rs.getString("codigoTurno"));
                    t.setFecha(rs.getDate("fecha").toLocalDate());
                    t.setEstado(rs.getString("estado"));
                    t.setIdBox(idBox);
                    
                    int idGestion = rs.getInt("idGestion");
                    t.setIdGestion(rs.wasNull() ? null : idGestion);
                    
                    int prioridad = rs.getInt("prioridad");
                    t.setPrioridad(rs.wasNull() ? null : prioridad);
                    
                    int segmentoScore = rs.getInt("segmentoScore");
                    t.setSegmentoScore(rs.wasNull() ? null : segmentoScore);
                    
                    int score = rs.getInt("score");
                    t.setScore(rs.wasNull() ? null : score);
                    
                    int orden = rs.getInt("orden");
                    t.setOrden(rs.wasNull() ? null : orden);
                    
                    // Resolver cliente por idCliente (PK)
                    String idCliente = rs.getString("idCliente");
                    if (idCliente != null) {
                        ClienteDAO cdao = new ClienteDAO();
                        Cliente c = cdao.obtenerClientePorIdCliente(idCliente);
                        t.setCliente(c);
                    }
                    turnosEnAtencion.add(t);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al listar turnos en atención: " + e.getMessage());
            e.printStackTrace();
        }
        
        // Obtener solo turnos EN_ESPERA para reordenar
        List<Turnero> turnosEnEspera = listarTurnosEnEsperaPorBox(idBox);
        
        if (turnosEnEspera.isEmpty()) {
            System.out.println("No hay turnos EN_ESPERA para reordenar en el box " + idBox);
            return true;
        }
        
        // Ordenar turnos EN_ESPERA por score (menor = más prioritario)
        turnosEnEspera.sort((t1, t2) -> {
            Integer score1 = t1.getScore() != null ? t1.getScore() : 9999;
            Integer score2 = t2.getScore() != null ? t2.getScore() : 9999;
            int comparacion = score1.compareTo(score2);
            if (comparacion != 0) {
                return comparacion;
            }
            // Si tienen el mismo score, ordenar por fecha
            int comparacionFecha = t1.getFecha().compareTo(t2.getFecha());
            if (comparacionFecha != 0) {
                return comparacionFecha;
            }
            // Si tienen la misma fecha, ordenar por idTurnero
            return Integer.compare(t1.getIdTurnero(), t2.getIdTurnero());
        });
        
        // Mostrar información de debug
        System.out.println("\n=== REORDENAMIENTO DE COLA ===");
        System.out.println("Turnos EN_ATENCION/LLAMADO: " + turnosEnAtencion.size());
        for (Turnero t : turnosEnAtencion) {
            System.out.println("  - " + t.getCodigoTurno() + " (orden: " + t.getOrden() + ", estado: " + t.getEstado() + ")");
        }
        System.out.println("Turnos EN_ESPERA a reordenar: " + turnosEnEspera.size());
        for (Turnero t : turnosEnEspera) {
            String clienteInfo = (t.getCliente() != null) ? 
                t.getCliente().getNombre() + " " + t.getCliente().getApellido() : "N/A";
            System.out.println("  - " + t.getCodigoTurno() + " | Cliente: " + clienteInfo + 
                             " | Score: " + t.getScore() + " | Prioridad: " + t.getPrioridad() + 
                             " | SegmentoScore: " + t.getSegmentoScore());
        }
        
        // Actualizar el orden de los turnos EN_ESPERA
        // Los turnos LLAMADO/EN_ATENCION mantienen su orden actual
        // Los turnos EN_ESPERA se reordenan empezando después de los que están en atención
        boolean todoOk = true;
        int ordenInicial = turnosEnAtencion.size() + 1; // Empezar después de los que están en atención
        int orden = ordenInicial;
        
        System.out.println("Orden inicial para EN_ESPERA: " + ordenInicial);
        for (Turnero turno : turnosEnEspera) {
            boolean actualizado = actualizarOrden(turno.getCodigoTurno(), orden);
            if (actualizado) {
                turno.setOrden(orden);
                String clienteInfo = (turno.getCliente() != null) ? 
                    turno.getCliente().getNombre() + " " + turno.getCliente().getApellido() : "N/A";
                System.out.println("✓ Turno " + turno.getCodigoTurno() + " (" + clienteInfo + ") → orden " + orden + 
                                 " (score: " + turno.getScore() + ", prioridad: " + turno.getPrioridad() + 
                                 ", segmentoScore: " + turno.getSegmentoScore() + ")");
            } else {
                todoOk = false;
                System.err.println("❌ Error al actualizar orden del turno " + turno.getCodigoTurno());
            }
            orden++;
        }
        System.out.println("===============================\n");
        
        System.out.println("Reordenamiento completado: " + turnosEnAtencion.size() + " turnos en atención (orden mantenido) + " + 
                          turnosEnEspera.size() + " turnos EN_ESPERA reordenados");
        
        return todoOk;
    }
    
    /**
     * Lista todos los turnos en cola con información del colaborador asignado al box
     * Retorna una lista con: Colaborador, código de turno, Cliente, Orden
     * @return Lista de objetos con información de la cola de atención
     */
    public List<ColaAtencionInfo> listarColaAtencionCompleta() {
        List<ColaAtencionInfo> lista = new ArrayList<>();
        String sql = "SELECT t.codigoTurno, t.idCliente, t.orden, t.idBox, " +
                     "c.nombre as nombreCliente, c.apellido as apellidoCliente, " +
                     "col.nombre as nombreColaborador, col.apellido as apellidoColaborador, col.legajo " +
                     "FROM sistemaatenciondiferenciada.turnero t " +
                     "LEFT JOIN sistemaatenciondiferenciada.cliente c ON t.idCliente = c.idCliente " +
                     "LEFT JOIN sistemaatenciondiferenciada.box_atencion b ON t.idBox = b.idBox " +
                     "LEFT JOIN sistemaatenciondiferenciada.colaborador col ON b.id_colaborador = col.id_colaborador " +
                     "WHERE t.estado IN ('EN_ESPERA', 'LLAMADO', 'EN_ATENCION') " +
                     "ORDER BY t.idBox ASC, COALESCE(t.orden, 9999) ASC, COALESCE(t.score, 9999) ASC, t.fecha ASC, t.idTurnero ASC";
        
        try (Connection conn = ConexionMySQL.conectar();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            
            while (rs.next()) {
                ColaAtencionInfo info = new ColaAtencionInfo();
                
                // Código de turno
                info.codigoTurno = rs.getString("codigoTurno");
                
                // Cliente (nombre + apellido)
                String nombreCliente = rs.getString("nombreCliente");
                String apellidoCliente = rs.getString("apellidoCliente");
                if (nombreCliente != null && apellidoCliente != null) {
                    info.cliente = nombreCliente.trim() + " " + apellidoCliente.trim();
                } else if (nombreCliente != null) {
                    info.cliente = nombreCliente.trim();
                } else if (apellidoCliente != null) {
                    info.cliente = apellidoCliente.trim();
                } else {
                    info.cliente = "N/A";
                }
                
                // Colaborador (nombre + apellido)
                String nombreColaborador = rs.getString("nombreColaborador");
                String apellidoColaborador = rs.getString("apellidoColaborador");
                if (nombreColaborador != null && apellidoColaborador != null) {
                    info.colaborador = nombreColaborador.trim() + " " + apellidoColaborador.trim();
                } else if (nombreColaborador != null) {
                    info.colaborador = nombreColaborador.trim();
                } else if (apellidoColaborador != null) {
                    info.colaborador = apellidoColaborador.trim();
                } else {
                    info.colaborador = "Sin asignar";
                }
                
                // Orden
                Integer orden = rs.getObject("orden") != null ? rs.getInt("orden") : null;
                info.orden = orden != null ? orden : 0;
                
                lista.add(info);
            }
            
        } catch (SQLException e) {
            System.err.println("Error al listar cola de atención completa: " + e.getMessage());
            e.printStackTrace();
        }
        
        return lista;
    }
    
    /**
     * Clase auxiliar para almacenar información de la cola de atención
     */
    public static class ColaAtencionInfo {
        public String colaborador;
        public String codigoTurno;
        public String cliente;
        public int orden;
        
        @Override
        public String toString() {
            return "ColaAtencionInfo{" +
                    "colaborador='" + colaborador + '\'' +
                    ", codigoTurno='" + codigoTurno + '\'' +
                    ", cliente='" + cliente + '\'' +
                    ", orden=" + orden +
                    '}';
        }
    }
}


