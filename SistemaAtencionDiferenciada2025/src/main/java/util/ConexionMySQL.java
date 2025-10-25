/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionMySQL {
  
    private static final String URL = "jdbc:mysql://localhost:3306/SistemaAtencionDiferenciada?useSSL=false&serverTimezone=UTC";
    private static final String USUARIO = "root";
    private static final String PASS = "Oracle.01";

    public static Connection conectar() throws SQLException {
        return DriverManager.getConnection(URL, USUARIO, PASS);
    }
}