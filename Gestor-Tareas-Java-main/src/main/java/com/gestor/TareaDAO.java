package com.gestor;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

// Esta clase es la única que habla directamente con la tabla "task".
// GestorTareas ya no guarda tareas en memoria, se las pide a esta clase.
public class TareaDAO {

    public void crear(Tarea tarea) {
        String sql = "INSERT INTO task (nombre, id_status_task, id_assement_task) VALUES ("
            + "?, "
            + "(SELECT id_status_task FROM status_task WHERE nombre = ?), "
            + "(SELECT id_assement_task FROM assement_task WHERE nombre = ?))";

        try (Connection conexion = Conexion.obtenerConexion();
             PreparedStatement ps = conexion.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, tarea.nombre);
            ps.setString(2, tarea.estado);
            ps.setString(3, tarea.prioridad);
            ps.executeUpdate();

            try (ResultSet generadas = ps.getGeneratedKeys()) {
                if (generadas.next()) {
                    tarea.id = generadas.getInt(1);
                }
            }

        } catch (SQLException e) {
            mostrarError(e);
        }
    }

    public ArrayList<Tarea> listar() {
        ArrayList<Tarea> tareas = new ArrayList<>();

        String sql = "SELECT t.id_task, t.nombre, s.nombre AS estado, a.nombre AS prioridad, "
            + "p.id_person, p.nombre AS nombre_persona "
            + "FROM task t "
            + "JOIN status_task s ON t.id_status_task = s.id_status_task "
            + "JOIN assement_task a ON t.id_assement_task = a.id_assement_task "
            + "LEFT JOIN person p ON t.id_person = p.id_person "
            + "ORDER BY t.id_task";

        try (Connection conexion = Conexion.obtenerConexion();
             Statement st = conexion.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                Tarea tarea = new Tarea();
                tarea.id = rs.getInt("id_task");
                tarea.nombre = rs.getString("nombre");
                tarea.estado = rs.getString("estado");
                tarea.prioridad = rs.getString("prioridad");

                int idPersona = rs.getInt("id_person");
                if (!rs.wasNull()) {
                    Usuario usuario = new Usuario();
                    usuario.id = idPersona;
                    usuario.nombre = rs.getString("nombre_persona");
                    tarea.usuario = usuario;
                }

                tareas.add(tarea);
            }

        } catch (SQLException e) {
            mostrarError(e);
        }

        return tareas;
    }

    public void asignarUsuario(int idTarea, int idUsuario) {
        String sql = "UPDATE task SET id_person = ? WHERE id_task = ?";

        try (Connection conexion = Conexion.obtenerConexion();
             PreparedStatement ps = conexion.prepareStatement(sql)) {

            ps.setInt(1, idUsuario);
            ps.setInt(2, idTarea);
            ps.executeUpdate();

        } catch (SQLException e) {
            mostrarError(e);
        }
    }

    public void actualizarEstado(int idTarea, String nuevoEstado) {
        String sql = "UPDATE task SET id_status_task = "
            + "(SELECT id_status_task FROM status_task WHERE nombre = ?) "
            + "WHERE id_task = ?";

        try (Connection conexion = Conexion.obtenerConexion();
             PreparedStatement ps = conexion.prepareStatement(sql)) {

            ps.setString(1, nuevoEstado);
            ps.setInt(2, idTarea);
            ps.executeUpdate();

        } catch (SQLException e) {
            mostrarError(e);
        }
    }

    private void mostrarError(SQLException e) {
        javax.swing.JOptionPane.showMessageDialog(null, "Error de base de datos: " + e.getMessage());
    }
}
