package dao;

import modelo.RegistrarAtencion;
import util.ConexionMySQL;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class RegistrarAtencionDAO {
    
    /**
     * Guarda un registro de atención en la base de datos
     */
    public boolean guardarRegistrarAtencion(RegistrarAtencion registro) {
        // Si idAtencion no es AUTO_INCREMENT, necesitamos calcularlo manualmente
        // Intentar primero con AUTO_INCREMENT, si falla, usar método manual
        String sql = "INSERT INTO sistemaatenciondiferenciada.registraratencion " +
                     "(fecha, idCliente, idCanal, idModeloAtencion, legajo, observaciones, idGestion) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?)";
        
        System.out.println("=== GUARDANDO REGISTRO DE ATENCIÓN ===");
        System.out.println("SQL: " + sql);
        
        try (Connection conn = ConexionMySQL.conectar();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            
            // fecha
            LocalDate fechaRegistro = registro.getFecha() != null ? registro.getFecha() : LocalDate.now();
            ps.setDate(1, Date.valueOf(fechaRegistro));
            System.out.println("Fecha: " + fechaRegistro);
            
            // idCliente (PK de la tabla cliente)
            String idCliente = null;
            if (registro.getCliente() != null && registro.getCliente().getIdCliente() != null) {
                idCliente = registro.getCliente().getIdCliente();
                ps.setString(2, idCliente);
                System.out.println("idCliente: " + idCliente);
            } else {
                ps.setNull(2, Types.VARCHAR);
                System.out.println("idCliente: NULL");
            }
            
            // idCanal (opcional, puede ser null si no se tiene)
            if (registro.getCanal() != null && registro.getCanal().getIdCanal() > 0) {
                ps.setInt(3, registro.getCanal().getIdCanal());
                System.out.println("idCanal: " + registro.getCanal().getIdCanal());
            } else {
                ps.setNull(3, Types.INTEGER);
                System.out.println("idCanal: NULL");
            }
            
            // idModeloAtencion (obtener del modelo de atención diferenciada del cliente)
            Integer idModeloAtencion = null;
            if (registro.getCliente() != null && registro.getCliente().getIdCliente() != null) {
                ModeloAtencionDiferenciadaDAO modeloDAO = new ModeloAtencionDiferenciadaDAO();
                idModeloAtencion = modeloDAO.obtenerIdModeloAtencionPorIdCliente(registro.getCliente().getIdCliente()).orElse(null);
            }
            if (idModeloAtencion != null) {
                ps.setInt(4, idModeloAtencion);
                System.out.println("idModeloAtencion: " + idModeloAtencion);
            } else {
                ps.setNull(4, Types.INTEGER);
                System.out.println("idModeloAtencion: NULL");
            }
            
            // legajo (del colaborador)
            Integer legajo = null;
            if (registro.getLegajo() != null) {
                legajo = registro.getLegajo().getLegajo();
                ps.setInt(5, legajo);
                System.out.println("legajo: " + legajo);
            } else {
                ps.setNull(5, Types.INTEGER);
                System.out.println("legajo: NULL");
            }
            
            // observaciones
            String observaciones = registro.getObservaciones();
            if (observaciones != null && !observaciones.trim().isEmpty()) {
                ps.setString(6, observaciones);
                System.out.println("observaciones: " + observaciones);
            } else {
                ps.setNull(6, Types.VARCHAR);
                System.out.println("observaciones: NULL");
            }
            
            // idGestion
            Integer idGestion = null;
            if (registro.getGestiones() != null && registro.getGestiones().getIdGestion() > 0) {
                idGestion = registro.getGestiones().getIdGestion();
                ps.setInt(7, idGestion);
                System.out.println("idGestion: " + idGestion);
            } else {
                ps.setNull(7, Types.INTEGER);
                System.out.println("idGestion: NULL");
            }
            
            System.out.println("Ejecutando INSERT...");
            int filasAfectadas = ps.executeUpdate();
            System.out.println("Filas afectadas: " + filasAfectadas);
            
            if (filasAfectadas > 0) {
                // Intentar obtener el idAtencion generado automáticamente
                try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        int idGenerado = generatedKeys.getInt(1);
                        registro.setIdAtencion(idGenerado);
                        System.out.println("✓ Registro de atención guardado con idAtencion (AUTO_INCREMENT): " + idGenerado);
                        return true;
                    } else {
                        // Si no se obtuvo la clave generada, calcular manualmente
                        System.out.println("⚠ No se obtuvo la clave generada, calculando idAtencion manualmente...");
                        int maxId = obtenerMaximoIdAtencion();
                        registro.setIdAtencion(maxId + 1);
                        System.out.println("✓ Registro de atención guardado con idAtencion (manual): " + registro.getIdAtencion());
                        return true;
                    }
                }
            }
            
        } catch (SQLException e) {
            // Si el error es por falta de AUTO_INCREMENT, intentar con método manual
            if (e.getMessage() != null && e.getMessage().contains("doesn't have a default value")) {
                System.err.println("⚠ Error: idAtencion no tiene AUTO_INCREMENT");
                System.err.println("   Intentando guardar con idAtencion calculado manualmente...");
                
                return guardarRegistrarAtencionManual(registro);
            } else {
                System.err.println("❌ ERROR al guardar registro de atención: " + e.getMessage());
                System.err.println("SQL State: " + e.getSQLState());
                System.err.println("Error Code: " + e.getErrorCode());
                e.printStackTrace();
                return false;
            }
        }
        
        return false;
    }
    
    /**
     * Método auxiliar para guardar registro de atención calculando idAtencion manualmente
     * (usado cuando la tabla no tiene AUTO_INCREMENT)
     */
    private boolean guardarRegistrarAtencionManual(RegistrarAtencion registro) {
        // Calcular el siguiente idAtencion
        int siguienteId = obtenerMaximoIdAtencion() + 1;
        registro.setIdAtencion(siguienteId);
        
        String sql = "INSERT INTO sistemaatenciondiferenciada.registraratencion " +
                     "(idAtencion, fecha, idCliente, idCanal, idModeloAtencion, legajo, observaciones, idGestion) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        
        System.out.println("=== GUARDANDO REGISTRO DE ATENCIÓN (MANUAL) ===");
        System.out.println("idAtencion calculado: " + siguienteId);
        System.out.println("SQL: " + sql);
        
        try (Connection conn = ConexionMySQL.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setInt(1, siguienteId);
            
            // fecha
            ps.setDate(2, Date.valueOf(registro.getFecha() != null ? registro.getFecha() : LocalDate.now()));
            
            // idCliente (PK de la tabla cliente)
            if (registro.getCliente() != null && registro.getCliente().getIdCliente() != null) {
                ps.setString(3, registro.getCliente().getIdCliente());
            } else {
                ps.setNull(3, Types.VARCHAR);
            }
            
            // idCanal
            if (registro.getCanal() != null && registro.getCanal().getIdCanal() > 0) {
                ps.setInt(4, registro.getCanal().getIdCanal());
            } else {
                ps.setNull(4, Types.INTEGER);
            }
            
            // idModeloAtencion
            Integer idModeloAtencion = null;
            if (registro.getCliente() != null && registro.getCliente().getIdCliente() != null) {
                ModeloAtencionDiferenciadaDAO modeloDAO = new ModeloAtencionDiferenciadaDAO();
                idModeloAtencion = modeloDAO.obtenerIdModeloAtencionPorIdCliente(registro.getCliente().getIdCliente()).orElse(null);
            }
            if (idModeloAtencion != null) {
                ps.setInt(5, idModeloAtencion);
            } else {
                ps.setNull(5, Types.INTEGER);
            }
            
            // legajo
            if (registro.getLegajo() != null) {
                ps.setInt(6, registro.getLegajo().getLegajo());
            } else {
                ps.setNull(6, Types.INTEGER);
            }
            
            // observaciones
            if (registro.getObservaciones() != null && !registro.getObservaciones().trim().isEmpty()) {
                ps.setString(7, registro.getObservaciones());
            } else {
                ps.setNull(7, Types.VARCHAR);
            }
            
            // idGestion
            if (registro.getGestiones() != null && registro.getGestiones().getIdGestion() > 0) {
                ps.setInt(8, registro.getGestiones().getIdGestion());
            } else {
                ps.setNull(8, Types.INTEGER);
            }
            
            int filasAfectadas = ps.executeUpdate();
            
            if (filasAfectadas > 0) {
                System.out.println("✓ Registro de atención guardado con idAtencion (manual): " + siguienteId);
                return true;
            }
            
        } catch (SQLException e) {
            System.err.println("❌ ERROR al guardar registro de atención (método manual): " + e.getMessage());
            System.err.println("SQL State: " + e.getSQLState());
            System.err.println("Error Code: " + e.getErrorCode());
            e.printStackTrace();
            return false;
        }
        
        return false;
    }
    
    /**
     * Obtiene todos los registros de atención
     */
    public List<RegistrarAtencion> listarTodos() {
        List<RegistrarAtencion> lista = new ArrayList<>();
        String sql = "SELECT idAtencion, fecha, idCliente, idCanal, idModeloAtencion, legajo, observaciones, idGestion " +
                     "FROM sistemaatenciondiferenciada.registraratencion " +
                     "ORDER BY fecha DESC, idAtencion DESC";
        
        try (Connection conn = ConexionMySQL.conectar();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            
            while (rs.next()) {
                RegistrarAtencion registro = new RegistrarAtencion();
                registro.setIdAtencion(rs.getInt("idAtencion"));
                
                Date fecha = rs.getDate("fecha");
                if (fecha != null) {
                    registro.setFecha(fecha.toLocalDate());
                }
                
                // Cargar cliente usando idCliente (PK)
                String idCliente = rs.getString("idCliente");
                if (idCliente != null) {
                    ClienteDAO clienteDAO = new ClienteDAO();
                    registro.setCliente(clienteDAO.obtenerClientePorIdCliente(idCliente));
                }
                
                // Cargar canal (si existe DAO)
                Integer idCanal = rs.getObject("idCanal") != null ? rs.getInt("idCanal") : null;
                if (idCanal != null) {
                    // TODO: Implementar CanalDAO si es necesario
                }
                
                // Cargar modelo de atención diferenciada
                Integer idModeloAtencion = rs.getObject("idModeloAtencion") != null ? rs.getInt("idModeloAtencion") : null;
                if (idModeloAtencion != null && registro.getCliente() != null) {
                    ModeloAtencionDiferenciadaDAO modeloDAO = new ModeloAtencionDiferenciadaDAO();
                    modeloDAO.buscarPorDni(registro.getCliente().getDni()).ifPresent(registro::setSegmento);
                }
                
                // Cargar colaborador (legajo)
                Integer legajo = rs.getObject("legajo") != null ? rs.getInt("legajo") : null;
                if (legajo != null) {
                    // TODO: Implementar ColaboradorDAO si es necesario
                    modelo.Colaborador colab = new modelo.Colaborador();
                    colab.setLegajo(legajo);
                    registro.setLegajo(colab);
                }
                
                // observaciones
                registro.setObservaciones(rs.getString("observaciones"));
                
                // Cargar gestión
                Integer idGestion = rs.getObject("idGestion") != null ? rs.getInt("idGestion") : null;
                if (idGestion != null) {
                    GestionesDAO gestionesDAO = new GestionesDAO();
                    gestionesDAO.buscarPorId(idGestion).ifPresent(registro::setGestiones);
                }
                
                lista.add(registro);
            }
            
        } catch (SQLException e) {
            System.err.println("Error al listar registros de atención: " + e.getMessage());
            e.printStackTrace();
        }
        
        return lista;
    }
    
    /**
     * Obtiene el máximo idAtencion para generar el siguiente
     */
    public int obtenerMaximoIdAtencion() {
        String sql = "SELECT MAX(idAtencion) as maxId FROM sistemaatenciondiferenciada.registraratencion";
        
        try (Connection conn = ConexionMySQL.conectar();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            
            if (rs.next() && rs.getObject("maxId") != null) {
                return rs.getInt("maxId");
            }
            
        } catch (SQLException e) {
            System.err.println("Error al obtener máximo idAtencion: " + e.getMessage());
            e.printStackTrace();
        }
        
        return 0;
    }
}

