package com.gestor;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

// Esta clase es la única que habla directamente con la tabla "person".
// GestorTareas ya no guarda usuarios en memoria, se los pide a esta clase.
public class UsuarioDAO {

    // id fijo del type_person "Estándar" que se crea con el script SQL
    private static final int ID_TYPE_PERSON_DEFAULT = 1;

    public void crear(Usuario usuario) {
        String sql = "INSERT INTO person (nombre, id_type_person) VALUES (?, ?)";

        try (Connection conexion = Conexion.obtenerConexion();
             PreparedStatement ps = conexion.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, usuario.nombre);
            ps.setInt(2, ID_TYPE_PERSON_DEFAULT);
            ps.executeUpdate();

            try (ResultSet generadas = ps.getGeneratedKeys()) {
                if (generadas.next()) {
                    usuario.id = generadas.getInt(1);
                }
            }

        } catch (SQLException e) {
            mostrarError(e);
        }
    }

    public ArrayList<Usuario> listar() {
        ArrayList<Usuario> usuarios = new ArrayList<>();
        String sql = "SELECT id_person, nombre FROM person ORDER BY id_person";

        try (Connection conexion = Conexion.obtenerConexion();
             Statement st = conexion.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                Usuario usuario = new Usuario();
                usuario.id = rs.getInt("id_person");
                usuario.nombre = rs.getString("nombre");
                usuarios.add(usuario);
            }

        } catch (SQLException e) {
            mostrarError(e);
        }

        return usuarios;
    }

    private void mostrarError(SQLException e) {
        javax.swing.JOptionPane.showMessageDialog(null, "Error de base de datos: " + e.getMessage());
    }
}
