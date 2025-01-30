DROP DATABASE IF EXISTS ventas;
CREATE DATABASE ventas CHARACTER SET utf8mb4;
USE ventas;

CREATE TABLE cliente (
                         id INT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
                         nombre VARCHAR(100) NOT NULL,
                         apellido1 VARCHAR(100) NOT NULL,
                         apellido2 VARCHAR(100),
                         ciudad VARCHAR(100),
                         categoría INT UNSIGNED,
                         correo VARCHAR(100)

);

CREATE TABLE comercial (
                           id INT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
                           nombre VARCHAR(100) NOT NULL,
                           apellido1 VARCHAR(100) NOT NULL,
                           apellido2 VARCHAR(100),
                           comisión FLOAT
);

CREATE TABLE pedido (
                        id INT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
                        total DOUBLE NOT NULL,
                        fecha DATE,
                        id_cliente INT UNSIGNED NOT NULL,
                        id_comercial INT UNSIGNED NOT NULL,
                        FOREIGN KEY (id_cliente) REFERENCES cliente(id),
                        FOREIGN KEY (id_comercial) REFERENCES comercial(id)
);

INSERT INTO cliente VALUES(1, 'Aarón', 'Rivero', 'Gómez', 'Almería', 100, 'aaron.rivero@empresa.com');
INSERT INTO cliente VALUES(2, 'Adela', 'Salas', 'Díaz', 'Granada', 200, 'adela.salas@empresa.com');
INSERT INTO cliente VALUES(3, 'Adolfo', 'Rubio', 'Flores', 'Sevilla', NULL, 'adolfo.rubio@empresa.com');
INSERT INTO cliente VALUES(4, 'Adrián', 'Suárez', NULL, 'Jaén', 300, 'adrian.suarez@empresa.com');
INSERT INTO cliente VALUES(5, 'Marcos', 'Loyola', 'Méndez', 'Almería', 200, 'marcos.loyola@empresa.com');
INSERT INTO cliente VALUES(6, 'María', 'Santana', 'Moreno', 'Cádiz', 100, 'maria.santana@empresa.com');
INSERT INTO cliente VALUES(7, 'Pilar', 'Ruiz', NULL, 'Sevilla', 300, 'pilar.ruiz@empresa.com');
INSERT INTO cliente VALUES(8, 'Pepe', 'Ruiz', 'Santana', 'Huelva', 200, 'pepe.ruiz@empresa.com');
INSERT INTO cliente VALUES(9, 'Guillermo', 'López', 'Gómez', 'Granada', 225, 'guillermo.lopez@empresa.com');
INSERT INTO cliente VALUES(10, 'Daniel', 'Santana', 'Loyola', 'Sevilla', 125, 'daniel.santana@empresa.com');


INSERT INTO comercial VALUES(1, 'Daniel', 'Sáez', 'Vega', 0.15);
INSERT INTO comercial VALUES(2, 'Juan', 'Gómez', 'López', 0.13);
INSERT INTO comercial VALUES(3, 'Diego', 'Flores', 'Salas', 0.11);
INSERT INTO comercial VALUES(4, 'Marta', 'Herrera', 'Gil', 0.14);
INSERT INTO comercial VALUES(5, 'Antonio', 'Carretero', 'Ortega', 0.12);
INSERT INTO comercial VALUES(6, 'Manuel', 'Domínguez', 'Hernández', 0.13);
INSERT INTO comercial VALUES(7, 'Antonio', 'Vega', 'Hernández', 0.11);
INSERT INTO comercial VALUES(8, 'Alfredo', 'Ruiz', 'Flores', 0.05);


INSERT INTO pedido VALUES(1, 150.5, '2017-10-05', 5, 2);
INSERT INTO pedido VALUES(2, 270.65, '2016-09-10', 1, 5);
INSERT INTO pedido VALUES(3, 65.26, '2017-10-05', 2, 1);
INSERT INTO pedido VALUES(4, 110.5, '2016-08-17', 8, 3);
INSERT INTO pedido VALUES(5, 948.5, '2017-09-10', 5, 2);
INSERT INTO pedido VALUES(6, 2400.6, '2016-07-27', 7, 1);
INSERT INTO pedido VALUES(7, 5760, '2015-09-10', 2, 1);
INSERT INTO pedido VALUES(8, 1983.43, '2017-10-10', 4, 6);
INSERT INTO pedido VALUES(9, 2480.4, '2016-10-10', 8, 3);
INSERT INTO pedido VALUES(10, 250.45, '2015-06-27', 8, 2);
INSERT INTO pedido VALUES(11, 75.29, '2016-08-17', 3, 7);
INSERT INTO pedido VALUES(12, 3045.6, '2017-04-25', 2, 1);
INSERT INTO pedido VALUES(13, 545.75, '2019-01-25', 6, 1);
INSERT INTO pedido VALUES(14, 145.82, '2017-02-02', 6, 1);
INSERT INTO pedido VALUES(15, 370.85, '2019-03-11', 1, 5);
INSERT INTO pedido VALUES(16, 2389.23, '2019-03-11', 1, 5);
-- Pedidos con fechas dentro de los últimos 13 meses
INSERT INTO pedido VALUES(22, 840.50, '2024-01-15', 1, 4);  -- Cliente 1, Comercial 4
INSERT INTO pedido VALUES(23, 620.30, '2023-11-25', 1, 5);  -- Cliente 2, Comercial 5
INSERT INTO pedido VALUES(24, 900.40, '2023-12-15', 1, 7);  -- Cliente 3, Comercial 7
INSERT INTO pedido VALUES(25, 1150.25, '2023-06-30', 1, 2);  -- Cliente 4, Comercial 2
INSERT INTO pedido VALUES(26, 1750.60, '2023-10-10', 1, 6);  -- Cliente 5, Comercial 6
INSERT INTO pedido VALUES(27, 430.70, '2023-03-15', 1, 8);  -- Cliente 6, Comercial 8
INSERT INTO pedido VALUES(28, 330.85, '2023-07-01', 1, 1);  -- Cliente 7, Comercial 1
INSERT INTO pedido VALUES(29, 250.95, '2023-08-10', 1, 3);  -- Cliente 8, Comercial 3
INSERT INTO pedido VALUES(30, 650.20, '2024-02-10', 1, 4);  -- Cliente 9, Comercial 4
INSERT INTO pedido VALUES(31, 450.75, '2023-08-20', 10, 2); -- Cliente 10, Comercial 2
INSERT INTO pedido VALUES(32, 1200.80, '2023-07-14', 1, 6);  -- Cliente 1, Comercial 6
INSERT INTO pedido VALUES(33, 320.10, '2023-05-25', 2, 7);  -- Cliente 2, Comercial 7
INSERT INTO pedido VALUES(34, 2150.60, '2023-06-18', 3, 5);  -- Cliente 3, Comercial 5
INSERT INTO pedido VALUES(35, 1100.45, '2023-09-07', 4, 8);  -- Cliente 4, Comercial 8
INSERT INTO pedido VALUES(36, 760.25, '2024-01-30', 5, 3);  -- Cliente 5, Comercial 3
INSERT INTO pedido VALUES(37, 980.30, '2023-11-22', 6, 1);  -- Cliente 6, Comercial 1
INSERT INTO pedido VALUES(38, 870.40, '2023-09-14', 7, 4);  -- Cliente 7, Comercial 4
INSERT INTO pedido VALUES(39, 650.60, '2023-10-10', 8, 2);  -- Cliente 8, Comercial 2
INSERT INTO pedido VALUES(40, 1250.50, '2023-12-05', 9, 7);  -- Cliente 9, Comercial 7
INSERT INTO pedido VALUES(41, 1100.75, '2023-03-12', 10, 6); -- Cliente 10, Comercial 6

-- Últimos 3 meses
INSERT INTO pedido VALUES (50, 500.00, '2025-01-20', 1, 6);
INSERT INTO pedido VALUES (51, 750.00, '2025-02-05', 1, 6);

-- Últimos 6 meses
INSERT INTO pedido VALUES (52, 1200.00, '2024-11-10', 1, 6);
INSERT INTO pedido VALUES (53, 950.00, '2024-10-25', 1, 6);

-- Últimos 12 meses
INSERT INTO pedido VALUES (54, 1750.00, '2024-06-15', 1, 6);
INSERT INTO pedido VALUES (55, 800.00, '2024-04-05', 1, 6);

-- Últimos 5 años
INSERT INTO pedido VALUES (56, 2200.00, '2022-08-20', 1, 6);
INSERT INTO pedido VALUES (57, 3000.00, '2021-12-10', 1, 6);