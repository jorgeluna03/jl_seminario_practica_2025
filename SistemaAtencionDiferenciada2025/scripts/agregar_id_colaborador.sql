-- Script para agregar campo id_colaborador a la tabla colaborador
-- Ejecutar en la base de datos: sistemaatenciondiferenciada

USE sistemaatenciondiferenciada;

-- Agregar campo id_colaborador como auto-incremento
ALTER TABLE `colaborador` 
ADD COLUMN `id_colaborador` INT AUTO_INCREMENT UNIQUE FIRST;

-- O si prefieres que sea la clave primaria en lugar de legajo:
-- ALTER TABLE `colaborador` 
-- DROP PRIMARY KEY,
-- ADD COLUMN `id_colaborador` INT AUTO_INCREMENT PRIMARY KEY FIRST;

-- Verificar que se agregó correctamente
DESCRIBE `colaborador`;

-- Ver los datos actualizados
SELECT id_colaborador, legajo, nombre, apellido, idRol FROM colaborador LIMIT 10;

