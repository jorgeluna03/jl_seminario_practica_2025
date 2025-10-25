package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class TestConexion {
    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/SistemaAtencionDiferenciada?useSSL=false&serverTimezone=UTC";
        String user = "root";
        String password = "Oracle.01";

        try {
            // Carga del driver MySQL
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Intentar conexión
            Connection conn = DriverManager.getConnection(url, user, password);
            System.out.println("✅ Conexión exitosa a MySQL.");
            System.out.println("📊 Base de datos: SistemaAtencionDiferenciada");
            System.out.println("🔗 URL: " + url);
            conn.close();
        } catch (ClassNotFoundException e) {
            System.err.println("❌ Driver JDBC MySQL no encontrado.");
            System.err.println("💡 Asegúrate de tener la dependencia mysql-connector-java en tu pom.xml");
            e.printStackTrace();
        } catch (SQLException e) {
            System.err.println("❌ Error de conexión a MySQL:");
            System.err.println("💡 Verifica que:");
            System.err.println("   - MySQL esté ejecutándose en localhost:3306");
            System.err.println("   - La base de datos 'SistemaAtencionDiferenciada' exista");
            System.err.println("   - El usuario 'root' tenga permisos");
            e.printStackTrace();
        }
    }
}