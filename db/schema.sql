-- ============================================================
-- RPG Manager - Schema MySQL
-- Programación Multinivel 2026-I
-- ============================================================

CREATE DATABASE IF NOT EXISTS rpg_manager;
USE rpg_manager;

-- Tabla principal de personajes
CREATE TABLE IF NOT EXISTS personaje (
    id         INT AUTO_INCREMENT PRIMARY KEY,
    nombre     VARCHAR(100) NOT NULL,
    tipo       ENUM('Guerrero', 'Mago', 'Arquero') NOT NULL,
    nivel      INT          NOT NULL DEFAULT 1,
    vida       INT          NOT NULL DEFAULT 100,
    ataque     INT          NOT NULL DEFAULT 10
);

-- Tabla de inventario (funcionalidad extra ⭐)
CREATE TABLE IF NOT EXISTS inventario (
    id             INT AUTO_INCREMENT PRIMARY KEY,
    id_personaje   INT          NOT NULL,
    nombre_item    VARCHAR(100) NOT NULL,
    tipo_item      VARCHAR(50),
    FOREIGN KEY (id_personaje) REFERENCES personaje(id) ON DELETE CASCADE
);
-- Datos de prueba (mínimo 3 personajes para la demo)
INSERT INTO personaje (nombre, tipo, nivel, vida, ataque) VALUES
('Aragorn',  'Guerrero', 5, 150, 30),
('Gandalf',  'Mago',     8, 90,  50),
('Legolas',  'Arquero',  6, 110, 40);

INSERT INTO inventario (id_personaje, nombre_item, tipo_item) VALUES
(1, 'Espada de acero', 'arma'),
(2, 'Baston magico',   'arma'),
(3, 'Arco élfico',     'arma');