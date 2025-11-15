-- Script para corregir la asignación de colaboradores a boxes
-- Ejecutar en la base de datos: sistemaatenciondiferenciada

USE sistemaatenciondiferenciada;

-- Asignar colaboradores válidos a los boxes según roles disponibles
-- Box 1: asignar colaborador con idRol = 1 (mejor)
UPDATE box_atencion 
SET id_colaborador = (SELECT id_colaborador FROM colaborador WHERE idRol = 1 LIMIT 1) 
WHERE idBox = 1;

-- Box 2: asignar colaborador con idRol = 2
UPDATE box_atencion 
SET id_colaborador = (SELECT id_colaborador FROM colaborador WHERE idRol = 2 LIMIT 1) 
WHERE idBox = 2;

-- Box 3: asignar colaborador con idRol = 3
UPDATE box_atencion 
SET id_colaborador = (SELECT id_colaborador FROM colaborador WHERE idRol = 3 LIMIT 1) 
WHERE idBox = 3;

-- Box 4: asignar colaborador con idRol = 4
UPDATE box_atencion 
SET id_colaborador = (SELECT id_colaborador FROM colaborador WHERE idRol = 4 LIMIT 1) 
WHERE idBox = 4;

-- Box 5: asignar colaborador con idRol = 5
UPDATE box_atencion 
SET id_colaborador = (SELECT id_colaborador FROM colaborador WHERE idRol = 5 LIMIT 1) 
WHERE idBox = 5;

-- Verificar que se asignaron correctamente
SELECT b.idBox, b.nombre, b.estado, b.id_colaborador, c.legajo, c.idRol 
FROM box_atencion b
LEFT JOIN colaborador c ON b.id_colaborador = c.id_colaborador
ORDER BY b.idBox;

