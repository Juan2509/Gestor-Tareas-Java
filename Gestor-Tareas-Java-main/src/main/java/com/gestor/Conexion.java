package com.gestor;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexion {

    // Datos de conexión a tu base de datos local
    private static final String URL = "jdbc:mysql://localhost:3306/gestor_tareas";
    private static final String USUARIO = "root";
    private static final String CONTRASENA = "031208"; // pon aquí tu contraseña de MySQL si tienes una

    public static Connection obtenerConexion() throws SQLException {
        return DriverManager.getConnection(URL, USUARIO, CONTRASENA);
    }
}
