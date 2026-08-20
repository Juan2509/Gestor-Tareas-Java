package com.gestor;

import java.util.ArrayList;
import javax.swing.JOptionPane;

public class GestorTareas {

    private UsuarioDAO usuarioDAO = new UsuarioDAO();
    private TareaDAO tareaDAO = new TareaDAO();

    public void crearUsuario() {
        Usuario nuevoUsuario = new Usuario();
        nuevoUsuario.nombre = JOptionPane.showInputDialog("Ingrese el nombre del usuario: ");

        if (nuevoUsuario.nombre == null || nuevoUsuario.nombre.isBlank()) {
            return;
        }

        usuarioDAO.crear(nuevoUsuario);
        JOptionPane.showMessageDialog(null, "Usuario creado correctamente");
    }

    public void crearTarea() {

        ArrayList<Usuario> usuarios = usuarioDAO.listar();

        if (usuarios.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Primero debes de crear un usuario");
            return;
        }

        Tarea nuevaTarea = new Tarea();
        nuevaTarea.nombre = JOptionPane.showInputDialog("Ingrese el nombre de la tarea: ");
        nuevaTarea.estado = "Por realizar";

        String[] prioridades = Prioridad.listar();
        nuevaTarea.prioridad = (String) JOptionPane.showInputDialog(
            null,
            "Seleccione la prioridaad: ", "Prioridad",
            JOptionPane.QUESTION_MESSAGE,
            null,
            prioridades,
            prioridades[0]
        );

        if (nuevaTarea.prioridad == null) {
            return;
        }

        tareaDAO.crear(nuevaTarea);
        JOptionPane.showMessageDialog(null, "Tarea creada correctamente");
    }

    public void asignarTarea() {

        ArrayList<Usuario> usuarios = usuarioDAO.listar();
        ArrayList<Tarea> tareas = tareaDAO.listar();

        if (usuarios.isEmpty() && tareas.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No hay ningún usuario o tarea registrada");
            return;
        }

        if (usuarios.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No hay usuarios registrados");
            return;
        }

        if (tareas.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No hay tareas registradas");
            return;
        }

        String listaTareas = "";

        for (int i = 0; i < tareas.size(); i++) {
            listaTareas += (i + 1) + ". " + tareas.get(i).nombre + "\n";
        }

        String entradaTarea = JOptionPane.showInputDialog(null, "Seleccione una tarea:\n\n" + listaTareas);

        if (entradaTarea == null) {
            return;
        }

        try {
            int numeroTarea = Integer.parseInt(entradaTarea);

            if (numeroTarea < 1 || numeroTarea > tareas.size()) {
                JOptionPane.showMessageDialog(null, "Seleccione una tarea válida");
            } else {
                JOptionPane.showMessageDialog(null, "Has elegido la tarea " + numeroTarea);
                Tarea tareaSeleccionada = tareas.get(numeroTarea - 1);

                String listaUsuarios = "";

                for (int i = 0; i < usuarios.size(); i++) {
                    listaUsuarios += (i + 1) + ". " + usuarios.get(i).nombre + "\n";
                }

                String entradaUsuario = JOptionPane.showInputDialog(
                    null,
                    "Seleccione un usuario:\n\n" + listaUsuarios
                );

                if (entradaUsuario == null) {
                    return;
                }

                try {

                    int numeroUsuario = Integer.parseInt(entradaUsuario);

                    if (numeroUsuario < 1 || numeroUsuario > usuarios.size()) {
                        JOptionPane.showMessageDialog(null, "Seleccione un usuario válido");
                    } else {
                        Usuario usuarioSeleccionado = usuarios.get(numeroUsuario - 1);
                        tareaDAO.asignarUsuario(tareaSeleccionada.id, usuarioSeleccionado.id);

                        JOptionPane.showMessageDialog(null, "Tarea asignada correctamente");
                    }

                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(null, "Debe ingresar un número");
                }
            }

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Debe ingresar un número.");
        }
    }

    public void cambiarEstado() {

        ArrayList<Tarea> tareas = tareaDAO.listar();

        if (tareas.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No hay tareas registradas");
            return;
        }

        String listaTareas = "";

        for (int i = 0; i < tareas.size(); i++) {
            Tarea tarea = tareas.get(i);

            if (tarea.usuario != null) {
                listaTareas += (i + 1) + ". " + tarea.nombre + " | Usuario: " + tarea.usuario.nombre + "\n";
            } else {
                listaTareas += (i + 1) + ". " + tarea.nombre + " | Sin asignar\n";
            }
        }

        String entradaTarea = JOptionPane.showInputDialog(null, "Seleccione una tarea:\n\n" + listaTareas);

        if (entradaTarea == null) {
            return;
        }

        try {

            int numeroTarea = Integer.parseInt(entradaTarea);

            if (numeroTarea < 1 || numeroTarea > tareas.size()) {
                JOptionPane.showMessageDialog(null, "Seleccione una tarea válida");
            } else {

                Tarea tareaSeleccionada = tareas.get(numeroTarea - 1);

                String usuarioAsignado;

                if (tareaSeleccionada.usuario != null) {
                    usuarioAsignado = tareaSeleccionada.usuario.nombre;
                } else {
                    usuarioAsignado = "Sin asignar";
                }

                String[] estados = {
                    "Por realizar", "En proceso", "Finalizado"
                };

                String nuevoEstado = (String) JOptionPane.showInputDialog(
                    null,
                    "Tarea: " + tareaSeleccionada.nombre
                    + "\nUsuario: " + usuarioAsignado
                    + "\nEstado actual: " + tareaSeleccionada.estado
                    + "\nSeleccione el nuevo estado:",
                    "Estado de la tarea",
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    estados,
                    estados[0]
                );

                if (nuevoEstado == null) {
                    return;
                }

                tareaDAO.actualizarEstado(tareaSeleccionada.id, nuevoEstado);
                JOptionPane.showMessageDialog(null, "Estado actualizado correctamente");
            }

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Debe ingresar un número");
        }
    }

    public void verTareasPorUsuario() {

        ArrayList<Usuario> usuarios = usuarioDAO.listar();

        if (usuarios.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No hay usuarios registrados");
            return;
        }

        String listaUsuarios = "";

        for (int i = 0; i < usuarios.size(); i++) {
            listaUsuarios += (i + 1) + ". " + usuarios.get(i).nombre + "\n";
        }

        String entradaUsuario = JOptionPane.showInputDialog(null, "Seleccione un usuario:\n\n" + listaUsuarios);

        if (entradaUsuario == null) {
            return;
        }

        try {

            int numeroUsuario = Integer.parseInt(entradaUsuario);

            if (numeroUsuario < 1 || numeroUsuario > usuarios.size()) {
                JOptionPane.showMessageDialog(null, "Seleccione un usuario válido");
            } else {
                Usuario usuarioSeleccionado = usuarios.get(numeroUsuario - 1);
                ArrayList<Tarea> tareas = tareaDAO.listar();

                String tareasUsuario = "";
                for (int i = 0; i < tareas.size(); i++) {

                    Tarea tarea = tareas.get(i);
                    if (tarea.usuario != null && tarea.usuario.id == usuarioSeleccionado.id) {
                        tareasUsuario += (i + 1) + ". " + tarea.nombre + "\n";
                    }
                }

                JOptionPane.showMessageDialog(null, "Tareas de " + usuarioSeleccionado.nombre + "\n\n" + tareasUsuario);
            }

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Debe ingresar un número");
        }
    }

    public void verTareasPorPrioridad() {

        ArrayList<Tarea> tareas = tareaDAO.listar();

        if (tareas.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No hay tareas registradas");
            return;
        }

        String[] prioridades = Prioridad.listar();

        String prioridadSeleccionada = (String) JOptionPane.showInputDialog(
            null,
            "Seleccione una prioridad:",
            "Prioridad",
            JOptionPane.QUESTION_MESSAGE,
            null,
            prioridades,
            prioridades[0]
        );

        if (prioridadSeleccionada == null) {
            return;
        }

        String tareasPrioridad = "";

        for (int i = 0; i < tareas.size(); i++) {

            if (tareas.get(i).prioridad.equals(prioridadSeleccionada)) {
                tareasPrioridad += (i + 1) + ". "
                    + tareas.get(i).nombre
                    + " - Estado: "
                    + tareas.get(i).estado
                    + "\n";
            }
        }

        if (tareasPrioridad.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No hay tareas con prioridad " + prioridadSeleccionada);
        } else {
            JOptionPane.showMessageDialog(null, "Tareas con prioridad " + prioridadSeleccionada + ":\n\n" + tareasPrioridad);
        }
    }

    public void verUsuarios() {

        ArrayList<Usuario> usuarios = usuarioDAO.listar();

        if (usuarios.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No hay usuarios registrados");
            return;
        }

        String listaUsuarios = "";

        for (int i = 0; i < usuarios.size(); i++) {
            listaUsuarios += (i + 1) + ". " + usuarios.get(i).nombre + "\n";
        }

        JOptionPane.showMessageDialog(null, "Usuarios registrados: \n\n" + listaUsuarios);
    }
}
