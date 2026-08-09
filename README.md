# Proyecto de Java - Gestor de Tareas

## Autor

**Juan José Rangel**

## Descripción

Este proyecto consiste en un gestor de tareas desarrollado en Java.

La idea del proyecto es simular de una manera sencilla el funcionamiento de herramientas de gestión de tareas como Jira o Trello, permitiendo crear usuarios, registrar tareas, asignarlas y controlar su estado y prioridad.

La aplicación utiliza `JOptionPane` para mostrar los menús, mensajes y opciones al usuario, por lo que no es necesario trabajar directamente desde la consola.

## Tecnologías utilizadas

- Java
- Swing / JOptionPane
- ArrayList
- Programación orientada a objetos
- Enum para las prioridades
- Git y GitHub

## Funcionalidades

El programa cuenta con un menú principal con las siguientes opciones:

### 1. Crear usuario

Permite registrar un nuevo usuario dentro del gestor.

El usuario solamente necesita ingresar su nombre y posteriormente queda disponible para asignarle tareas.

### 2. Crear tarea

Permite crear una nueva tarea.

Al crearla se solicita:

- Nombre de la tarea
- Prioridad

Las prioridades disponibles son:

- Alta
- Media
- Baja

Todas las tareas nuevas comienzan con el estado:

`Por realizar`

### 3. Asignar tarea

Permite seleccionar una tarea existente y asignarla a uno de los usuarios registrados.

De esta manera cada tarea puede quedar relacionada con el usuario responsable de realizarla.

### 4. Cambiar estado

Permite modificar el estado de una tarea.

Los estados disponibles son:

- Por realizar
- En proceso
- Finalizado

Al seleccionar una tarea también se muestra el usuario que tiene asignada la tarea y su estado actual.

### 5. Ver tareas por usuario

Permite seleccionar un usuario y consultar las tareas que tiene asignadas.

### 6. Ver tareas por prioridad

Permite seleccionar una prioridad y consultar las tareas que pertenecen a esa categoría.

También se muestra el estado de cada tarea.

### 7. Ver usuarios

Muestra todos los usuarios que han sido registrados en el sistema.

### 8. Salir

Cierra el menú principal y finaliza la ejecución del programa.

## Estructura del proyecto

El proyecto está organizado de la siguiente manera:

```text
Gestor-Tareas-Java/
│
├── src/
│   └── main/
│       └── java/
│           └── com/
│               └── gestor/
│                   ├── GestorTareas.java
│                   ├── Main.java
│                   ├── Prioridad.java
│                   ├── Tarea.java
│                   └── Usuario.java
│
├── .gitignore
└── pom.xml
