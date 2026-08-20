package com.gestor;

import javax.swing.JOptionPane;

public class Main {

    public static void main(String[] args) {

        GestorTareas gestor = new GestorTareas();

        JOptionPane.showMessageDialog(null, "Bienvenido al gestor de tareas");

        String menuPrincipal = "Elija una opción:\n 1. Crear usuario\n 2. Crear tarea\n 3. Asignar tarea\n 4. Cambiar estado\n 5. Ver tareas por usuario\n 6. Ver tareas por prioridad\n 7. Ver usuarios\n 8. Salir";

        int opcion = 0;

        while (opcion != 8) {

            String entrada = JOptionPane.showInputDialog(menuPrincipal);

            if (entrada == null) {
                break;
            }

            try {

                opcion = Integer.parseInt(entrada);

                if (opcion < 1 || opcion > 8) {
                    JOptionPane.showMessageDialog(null, "Ingrese una opción válida");
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Ingrese una opción válida");
            }

            switch (opcion) {
                case 1:
                    gestor.crearUsuario();
                    break;

                case 2:
                    gestor.crearTarea();
                    break;

                case 3:
                    gestor.asignarTarea();
                    break;

                case 4:
                    gestor.cambiarEstado();
                    break;

                case 5:
                    gestor.verTareasPorUsuario();
                    break;

                case 6:
                    gestor.verTareasPorPrioridad();
                    break;

                case 7:
                    gestor.verUsuarios();
                    break;

                default:
                    break;
            }
        }
    }
}