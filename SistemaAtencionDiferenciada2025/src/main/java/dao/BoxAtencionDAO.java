package dao;

import modelo.BoxAtencion;
import util.ConexionMySQL;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

public class BoxAtencionDAO {
    
    // Mapa para almacenar el último índice usado en round-robin por cada rol
    // Clave: rol, Valor: último índice usado para distribuir turnos
    private static final Map<Integer, Integer> ultimoIndicePorRol = new ConcurrentHashMap<>();
    
    /**
     * Obtiene todos los colaboradores asignados a boxes de atención (estado = 'Asignado')
     * @return Lista de colaboradores con su información básica (legajo, nombre, apellido, idBox)
     */
    public List<ColaboradorInfo> listarColaboradoresAsignados() {
        List<ColaboradorInfo> colaboradores = new ArrayList<>();
        String sql = "SELECT DISTINCT c.legajo, c.nombre, c.apellido, b.idBox " +
                     "FROM sistemaatenciondiferenciada.box_atencion b " +
                     "INNER JOIN sistemaatenciondiferenciada.colaborador c ON b.id_colaborador = c.id_colaborador " +
                     "WHERE b.estado = 'Asignado' " +
                     "AND b.id_colaborador IS NOT NULL " +
                     "ORDER BY c.nombre, c.apellido";
        
        try (Connection conn = ConexionMySQL.conectar();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            
            while (rs.next()) {
                ColaboradorInfo info = new ColaboradorInfo();
                info.legajo = rs.getInt("legajo");
                info.nombre = rs.getString("nombre");
                info.apellido = rs.getString("apellido");
                info.idBox = rs.getInt("idBox");
                colaboradores.add(info);
            }
            
        } catch (SQLException e) {
            System.err.println("Error al listar colaboradores asignados: " + e.getMessage());
            e.printStackTrace();
        }
        
        return colaboradores;
    }
    
    /**
     * Obtiene el idBox asignado a un colaborador por su legajo
     */
    public Optional<Integer> obtenerIdBoxPorLegajo(int legajo) {
        String sql = "SELECT b.idBox " +
                     "FROM sistemaatenciondiferenciada.box_atencion b " +
                     "INNER JOIN sistemaatenciondiferenciada.colaborador c ON b.id_colaborador = c.id_colaborador " +
                     "WHERE b.estado = 'Asignado' " +
                     "AND c.legajo = ? " +
                     "LIMIT 1";
        
        try (Connection conn = ConexionMySQL.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setInt(1, legajo);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(rs.getInt("idBox"));
                }
            }
            
        } catch (SQLException e) {
            System.err.println("Error al obtener idBox por legajo: " + e.getMessage());
            e.printStackTrace();
        }
        
        return Optional.empty();
    }
    
    /**
     * Clase auxiliar para almacenar información de colaboradores asignados
     */
    public static class ColaboradorInfo {
        public int legajo;
        public String nombre;
        public String apellido;
        public int idBox;
        
        @Override
        public String toString() {
            return nombre + " " + apellido + " (Legajo: " + legajo + ")";
        }
    }
    
    public boolean registrar(BoxAtencion box) {
        String sql = "INSERT INTO box_atencion (idBox, nombre, estado, id_colaborador) VALUES (?, ?, ?, ?)";
        try (Connection conn = ConexionMySQL.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setInt(1, box.getIdBox());
            ps.setString(2, box.getNombre());
            ps.setString(3, box.getEstado());
            if (box.getColaboradorAsignado() != null) {
                ps.setInt(4, box.getColaboradorAsignado().getLegajo());
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
        String sql = "SELECT idBox, nombre, estado, id_colaborador FROM box_atencion WHERE idBox = ?";
        try (Connection conn = ConexionMySQL.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setInt(1, idBox);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    BoxAtencion box = new BoxAtencion();
                    box.setIdBox(rs.getInt("idBox"));
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
        String sql = "SELECT idBox, nombre, estado, id_colaborador FROM box_atencion ORDER BY idBox";
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
        String sql = "UPDATE box_atencion SET id_colaborador = ? WHERE idBox = ?";
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
    
    /**
     * Busca el mejor box disponible considerando todos los roles desde el requerido hasta el peor (rol 5)
     * Prioriza el mejor rol disponible (rol 1 es mejor, rol 5 es peor)
     * Distribuye los turnos entre boxes disponibles para balancear la carga
     * Si no hay boxes con el rol requerido, busca el siguiente mejor rol disponible
     * 
     * @param idRolRequerido ID del rol requerido (ideal)
     * @return Optional con el idBox encontrado, o empty si no hay ninguno disponible
     */
    public Optional<Integer> buscarBoxDisponiblePorRol(int idRolRequerido) {
        System.out.println("\n=== BUSCANDO BOX DISPONIBLE ===");
        System.out.println("Rol requerido (máximo): " + idRolRequerido);
        System.out.println("Buscaré TODOS los boxes con rol <= " + idRolRequerido + " (pueden atender este turno)");
        System.out.println("Nota: Boxes con mejor rol (menor número) también pueden atender turnos que requieren rol mayor");
        
        try (Connection conn = ConexionMySQL.conectar()) {
            // 1. Buscar TODOS los boxes disponibles que puedan atender este turno
            // Un box puede atender si: su rol <= idRolRequerido (rol mejor o igual)
            // Ejemplo: si requiere rol 3, boxes con rol 1, 2 o 3 pueden atender
            // Los boxes con mejor rol (menor número) también pueden atender turnos que requieren rol mayor
            String sql = "SELECT b.idBox, COUNT(t.idTurnero) as cantidadTurnos, c.idRol " +
                         "FROM sistemaatenciondiferenciada.box_atencion b " +
                         "INNER JOIN sistemaatenciondiferenciada.colaborador c ON b.id_colaborador = c.id_colaborador " +
                         "LEFT JOIN sistemaatenciondiferenciada.turnero t ON b.idBox = t.idBox " +
                         "   AND t.estado IN ('EN_ESPERA', 'LLAMADO', 'EN_ATENCION') " +
                         "WHERE b.estado = 'Asignado' " +
                         "AND c.idRol >= 1 AND c.idRol <= ? " +
                         "GROUP BY b.idBox, c.idRol " +
                         "ORDER BY cantidadTurnos ASC, c.idRol ASC, b.idBox ASC";
            
            System.out.println("SQL para buscar TODOS los boxes disponibles: " + sql);
            System.out.println("Parámetro idRolRequerido: " + idRolRequerido);
            
            java.util.List<Integer> todosLosBoxes = new java.util.ArrayList<>();
            java.util.Map<Integer, Integer> turnosPorBox = new java.util.HashMap<>();
            java.util.Map<Integer, Integer> rolPorBox = new java.util.HashMap<>();
            
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setInt(1, idRolRequerido);
                
                try (ResultSet rs = ps.executeQuery()) {
                    while (rs.next()) {
                        int idBox = rs.getInt("idBox");
                        int cantidadTurnos = rs.getInt("cantidadTurnos");
                        int rolEncontrado = rs.getInt("idRol");
                        
                        System.out.println("  Box " + idBox + ": " + cantidadTurnos + " turnos en cola (rol " + rolEncontrado + ")");
                        
                        todosLosBoxes.add(idBox);
                        turnosPorBox.put(idBox, cantidadTurnos);
                        rolPorBox.put(idBox, rolEncontrado);
                    }
                }
            }
            
            if (todosLosBoxes.isEmpty()) {
                System.out.println("⚠ No se encontró ningún box disponible con rol <= " + idRolRequerido);
                realizarDiagnostico(idRolRequerido, conn);
                return Optional.empty();
            }
            
            System.out.println("Total de boxes disponibles (rol <= " + idRolRequerido + "): " + todosLosBoxes.size());
            
            // 2. Ordenar boxes por: primero por carga (menos turnos), luego por rol (menor = mejor)
            // Esto prioriza boxes con menos carga, pero también considera usar boxes con mejor rol
            todosLosBoxes.sort((b1, b2) -> {
                int turnos1 = turnosPorBox.get(b1);
                int turnos2 = turnosPorBox.get(b2);
                // Primero ordenar por cantidad de turnos (menos turnos = mejor)
                if (turnos1 != turnos2) {
                    return Integer.compare(turnos1, turnos2);
                }
                // Si tienen los mismos turnos, ordenar por rol (menor = mejor)
                int rol1 = rolPorBox.get(b1);
                int rol2 = rolPorBox.get(b2);
                if (rol1 != rol2) {
                    return Integer.compare(rol1, rol2);
                }
                // Si tienen el mismo rol y misma carga, ordenar por idBox
                return Integer.compare(b1, b2);
            });
            
            System.out.println("Boxes ordenados por carga y rol:");
            for (Integer boxId : todosLosBoxes) {
                System.out.println("  - Box " + boxId + ": " + turnosPorBox.get(boxId) + " turnos (rol " + rolPorBox.get(boxId) + ")");
            }
            
            // 3. Distribuir usando round-robin entre TODOS los boxes disponibles
            // Esto asegura que todos los boxes se usen, no solo los que tienen menos turnos
            Integer idBoxSeleccionado;
            
            if (todosLosBoxes.size() > 1) {
                // Usar idRolRequerido como clave para el round-robin
                // Esto permite rotar entre todos los boxes disponibles para este tipo de turno
                int ultimoIndice = ultimoIndicePorRol.getOrDefault(idRolRequerido, -1);
                int siguienteIndice = (ultimoIndice + 1) % todosLosBoxes.size();
                ultimoIndicePorRol.put(idRolRequerido, siguienteIndice);
                
                idBoxSeleccionado = todosLosBoxes.get(siguienteIndice);
                int rolSeleccionado = rolPorBox.get(idBoxSeleccionado);
                int turnosSeleccionado = turnosPorBox.get(idBoxSeleccionado);
                
                System.out.println("  → Distribuyendo carga (round-robin entre TODOS los " + todosLosBoxes.size() + " boxes disponibles): ");
                System.out.println("     Seleccionado Box " + idBoxSeleccionado + 
                                 " (posición " + siguienteIndice + " de " + todosLosBoxes.size() + 
                                 ", " + turnosSeleccionado + " turnos actuales, rol " + rolSeleccionado + ")");
                System.out.println("  → Lista completa de boxes disponibles: " + todosLosBoxes);
                
                if (rolSeleccionado == idRolRequerido) {
                    System.out.println("✓ Box seleccionado con rol requerido (" + idRolRequerido + ")");
                } else if (rolSeleccionado < idRolRequerido) {
                    System.out.println("✓ Box seleccionado con mejor rol (" + rolSeleccionado + " < " + idRolRequerido + ")");
                } else {
                    System.out.println("⚠ Box seleccionado con rol mayor (" + rolSeleccionado + " > " + idRolRequerido + ")");
                }
            } else if (todosLosBoxes.size() == 1) {
                idBoxSeleccionado = todosLosBoxes.get(0);
                int rolSeleccionado = rolPorBox.get(idBoxSeleccionado);
                System.out.println("  → Solo un box disponible: Box " + idBoxSeleccionado + " (rol " + rolSeleccionado + ")");
            } else {
                System.out.println("  → No hay boxes disponibles");
                return Optional.empty();
            }
            
            return Optional.of(idBoxSeleccionado);
        } catch (SQLException e) {
            System.err.println("❌ ERROR al buscar box disponible por rol:");
            System.err.println("   Mensaje: " + e.getMessage());
            System.err.println("   SQL State: " + e.getSQLState());
            System.err.println("   Error Code: " + e.getErrorCode());
            System.err.println("   Verifica que:");
            System.err.println("   - La tabla box_atencion tenga la columna 'id_colaborador'");
            System.err.println("   - La tabla colaborador tenga la columna 'idRol'");
            System.err.println("   - Los boxes tengan colaboradores asignados (id_colaborador no sea NULL)");
            e.printStackTrace();
        }
        return Optional.empty();
    }
    
    /**
     * Realiza un diagnóstico para entender por qué no se encontraron boxes disponibles
     */
    private void realizarDiagnostico(int idRol, Connection conn) {
        try {
            System.out.println("\n=== DIAGNÓSTICO ===");
            
            // 1. Verificar si hay boxes en total
            String sql1 = "SELECT COUNT(*) as total FROM sistemaatenciondiferenciada.box_atencion";
            try (PreparedStatement ps = conn.prepareStatement(sql1);
                 ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    int total = rs.getInt("total");
                    System.out.println("1. Total de boxes en la BD: " + total);
                }
            }
            
            // 2. Verificar boxes con estado 'Asignado'
            String sql2 = "SELECT COUNT(*) as total FROM sistemaatenciondiferenciada.box_atencion WHERE estado = 'Asignado'";
            try (PreparedStatement ps = conn.prepareStatement(sql2);
                 ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    int total = rs.getInt("total");
                    System.out.println("2. Boxes con estado 'Asignado': " + total);
                }
            }
            
            // 3. Verificar boxes con colaboradores asignados
            String sql3 = "SELECT COUNT(*) as total FROM sistemaatenciondiferenciada.box_atencion WHERE id_colaborador IS NOT NULL";
            try (PreparedStatement ps = conn.prepareStatement(sql3);
                 ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    int total = rs.getInt("total");
                    System.out.println("3. Boxes con colaborador asignado (id_colaborador NOT NULL): " + total);
                }
            }
            
            // 4. Verificar colaboradores con el idRol requerido
            String sql4 = "SELECT COUNT(*) as total FROM sistemaatenciondiferenciada.colaborador WHERE idRol = ?";
            try (PreparedStatement ps = conn.prepareStatement(sql4)) {
                ps.setInt(1, idRol);
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        int total = rs.getInt("total");
                        System.out.println("4. Colaboradores con idRol = " + idRol + ": " + total);
                    }
                }
            }
            
            // 5. Verificar la combinación: boxes 'Asignado' + colaboradores con el rol
            String sql5 = "SELECT b.idBox, b.nombre, b.estado, b.id_colaborador, c.legajo, c.idRol " +
                         "FROM sistemaatenciondiferenciada.box_atencion b " +
                         "LEFT JOIN sistemaatenciondiferenciada.colaborador c ON b.id_colaborador = c.id_colaborador " +
                         "WHERE b.estado = 'Asignado'";
            try (PreparedStatement ps = conn.prepareStatement(sql5);
                 ResultSet rs = ps.executeQuery()) {
                System.out.println("5. Detalle de boxes 'Asignado':");
                boolean encontroUno = false;
                while (rs.next()) {
                    encontroUno = true;
                    int idBox = rs.getInt("idBox");
                    String nombre = rs.getString("nombre");
                    String estado = rs.getString("estado");
                    int idColab = rs.getInt("id_colaborador");
                    boolean idColabNull = rs.wasNull();
                    int legajo = rs.getInt("legajo");
                    boolean legajoNull = rs.wasNull();
                    int rolColab = rs.getInt("idRol");
                    boolean rolNull = rs.wasNull();
                    
                    System.out.println("   - Box " + idBox + " (" + nombre + "): estado=" + estado + 
                                     ", id_colaborador=" + (idColabNull ? "NULL" : idColab) +
                                     ", colaborador.legajo=" + (legajoNull ? "NULL" : legajo) +
                                     ", colaborador.idRol=" + (rolNull ? "NULL" : rolColab));
                }
                if (!encontroUno) {
                    System.out.println("   (No hay boxes con estado 'Asignado')");
                }
            }
            
            // 6. Verificar qué colaboradores existen en la BD
            String sql6 = "SELECT legajo, idRol FROM sistemaatenciondiferenciada.colaborador ORDER BY legajo";
            try (PreparedStatement ps = conn.prepareStatement(sql6);
                 ResultSet rs = ps.executeQuery()) {
                System.out.println("\n6. Colaboradores disponibles en la BD:");
                boolean hayColaboradores = false;
                while (rs.next()) {
                    hayColaboradores = true;
                    int legajo = rs.getInt("legajo");
                    int rolColab = rs.getInt("idRol");
                    boolean rolNull = rs.wasNull();
                    System.out.println("   - Legajo: " + legajo + ", idRol: " + (rolNull ? "NULL" : rolColab));
                }
                if (!hayColaboradores) {
                    System.out.println("   (No hay colaboradores en la BD)");
                }
            }
            
            // 7. Verificar correspondencia entre boxes y colaboradores
            System.out.println("\n7. Verificación de correspondencia:");
            String sql7 = "SELECT DISTINCT b.id_colaborador FROM sistemaatenciondiferenciada.box_atencion b WHERE b.id_colaborador IS NOT NULL";
            try (PreparedStatement ps = conn.prepareStatement(sql7);
                 ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    int idColabBox = rs.getInt("id_colaborador");
                    // Verificar si existe en colaborador por id_colaborador
                    String sqlVerificar = "SELECT legajo, idRol FROM sistemaatenciondiferenciada.colaborador WHERE id_colaborador = ?";
                    try (PreparedStatement psVerificar = conn.prepareStatement(sqlVerificar)) {
                        psVerificar.setInt(1, idColabBox);
                        try (ResultSet rsVerificar = psVerificar.executeQuery()) {
                            if (rsVerificar.next()) {
                                int legajo = rsVerificar.getInt("legajo");
                                int rolColab = rsVerificar.getInt("idRol");
                                boolean rolNull = rsVerificar.wasNull();
                                System.out.println("   ✓ Box.id_colaborador = " + idColabBox + " → Colaborador existe (legajo: " + legajo + ", idRol: " + (rolNull ? "NULL" : rolColab) + ")");
                            } else {
                                System.err.println("   ❌ Box.id_colaborador = " + idColabBox + " → NO EXISTE en tabla colaborador");
                                System.err.println("      Este colaborador no existe en la tabla colaborador.legajo");
                            }
                        }
                    }
                }
            }
            
            // 8. Recomendaciones
            System.out.println("\n=== RECOMENDACIONES ===");
            System.out.println("Para que el sistema funcione correctamente:");
            System.out.println("1. Asegúrate de tener boxes con estado 'Asignado'");
            System.out.println("2. Verifica que los valores en box_atencion.id_colaborador existan en colaborador.id_colaborador");
            System.out.println("3. Asegúrate de que los colaboradores tengan idRol asignado");
            System.out.println("4. Ejemplo SQL para corregir:");
            System.out.println("   -- Primero verifica qué colaboradores tienes:");
            System.out.println("   SELECT legajo, idRol FROM colaborador;");
            System.out.println("   -- Luego actualiza los boxes con legajos válidos:");
            System.out.println("   UPDATE box_atencion SET id_colaborador = (SELECT id_colaborador FROM colaborador WHERE idRol = " + idRol + " LIMIT 1) WHERE idBox = 1;");
            
        } catch (SQLException e) {
            System.err.println("Error durante el diagnóstico: " + e.getMessage());
        }
    }
}


