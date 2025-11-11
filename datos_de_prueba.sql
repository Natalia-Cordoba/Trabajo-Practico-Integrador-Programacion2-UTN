INSERT INTO Microchip (id, eliminado, codigo, fechaImplantacion, veterinaria, observaciones)
VALUES (1, FALSE, 'ABCD-12345678901234567890', '2023-05-10', 'Vet Central', 'Chip en sitio estándar. Revisión anual necesaria.');

INSERT INTO Microchip (id, eliminado, codigo, fechaImplantacion, veterinaria, observaciones)
VALUES (2, FALSE, 'ABCD-12345678901234567891', '2024-05-10', 'Veterinaria Vecinal', 'Lectura OK. Implante estándar');

INSERT INTO Microchip (id, eliminado, codigo, fechaImplantacion, veterinaria, observaciones)
VALUES (3, FALSE, 'ABCD-12345678901234567892', '2025-05-10', 'Vet Central', 'Posición verificada por rayos X. Correcta.');

INSERT INTO Microchip (id, eliminado, codigo, fechaImplantacion, veterinaria, observaciones)
VALUES (4, FALSE, 'ABCD-12345678901234567893', '2023-05-11', 'Veterinaria Arena', 'Verificado para viajes internacionales.');

INSERT INTO Microchip (id, eliminado, codigo, fechaImplantacion, veterinaria, observaciones)
VALUES (5, FALSE, 'ABCD-12345678901234567894', '2023-05-12', 'Mascotas felices', 'Lectura OK. Implante estándar');

INSERT INTO Mascota (id, eliminado, nombre, especie, raza, fechaNacimiento, duenio, microchip_id)
VALUES (101, FALSE, "Firulais", "Perro", 'Labrador', '2020-08-20', 'Juan Pérez', 1);

INSERT INTO Mascota (id, eliminado, nombre, especie, raza, fechaNacimiento, duenio, microchip_id)
VALUES (102, FALSE, 'Cocoa', 'Gato', 'Himalayo', '2021-05-17', 'Constanza Moran', 2);

INSERT INTO Mascota (id, eliminado, nombre, especie, raza, fechaNacimiento, duenio, microchip_id)
VALUES (103, FALSE, 'Greta', "Perro", 'Beagle', '2023-06-27', 'Fernanda Antinanco', 3);

INSERT INTO Mascota (id, eliminado, nombre, especie, raza, fechaNacimiento, duenio, microchip_id)
VALUES (104, FALSE, 'Nina', 'Gato', 'Pelo semi-largo sin raza específica', '2024-01-06', 'Vicente Castro', 4);

INSERT INTO Mascota (id, eliminado, nombre, especie, raza, fechaNacimiento, duenio, microchip_id)
VALUES (105, FALSE, 'Morgan', 'Perro', 'Jack Russel', '2025-01-15', 'Telma Parra', 5);