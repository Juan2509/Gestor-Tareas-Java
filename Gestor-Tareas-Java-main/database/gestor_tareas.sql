-- =========================================================
-- Base de datos: gestor_tareas
-- Estructura basada en el diagrama entregado
-- =========================================================

CREATE DATABASE IF NOT EXISTS gestor_tareas;
USE gestor_tareas;

-- Tipo de persona (ej: Estándar, Administrador...)
CREATE TABLE type_person (
    id_type_person INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(50) NOT NULL
);

-- Persona (reemplaza a "Usuario" en la app)
CREATE TABLE person (
    id_person INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    id_type_person INT NOT NULL,
    FOREIGN KEY (id_type_person) REFERENCES type_person(id_type_person)
);

-- Equipo (creada para respetar el diagrama; el menú aún no la usa)
CREATE TABLE team (
    id_team INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL
);

-- Relación equipo-persona (creada para respetar el diagrama; el menú aún no la usa)
CREATE TABLE team_person (
    id_team_person INT AUTO_INCREMENT PRIMARY KEY,
    id_team INT NOT NULL,
    id_person INT NOT NULL,
    FOREIGN KEY (id_team) REFERENCES team(id_team),
    FOREIGN KEY (id_person) REFERENCES person(id_person)
);

-- Estado de la tarea (Por realizar / En proceso / Finalizado)
CREATE TABLE status_task (
    id_status_task INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(50) NOT NULL
);

-- "Assessment" de la tarea = tu prioridad actual (Alta / Media / Baja)
CREATE TABLE assement_task (
    id_assement_task INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(50) NOT NULL
);

-- Tarea (reemplaza a "Tarea" en la app)
CREATE TABLE task (
    id_task INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(150) NOT NULL,
    id_status_task INT NOT NULL,
    id_assement_task INT NOT NULL,
    id_person INT NULL,
    FOREIGN KEY (id_status_task) REFERENCES status_task(id_status_task),
    FOREIGN KEY (id_assement_task) REFERENCES assement_task(id_assement_task),
    FOREIGN KEY (id_person) REFERENCES person(id_person)
);

-- =========================================================
-- Datos iniciales (obligatorios para que la app funcione)
-- =========================================================

INSERT INTO type_person (nombre) VALUES ('Estándar');

INSERT INTO status_task (nombre) VALUES
    ('Por realizar'),
    ('En proceso'),
    ('Finalizado');

INSERT INTO assement_task (nombre) VALUES
    ('Alta'),
    ('Media'),
    ('Baja');
