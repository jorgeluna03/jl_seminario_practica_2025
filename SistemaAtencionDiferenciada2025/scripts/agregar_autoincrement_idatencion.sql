-- Script para agregar AUTO_INCREMENT a la columna idAtencion en la tabla registraratencion
-- Ejecutar este script si la tabla no tiene AUTO_INCREMENT configurado

USE sistemaatenciondiferenciada;

-- Verificar estructura actual de la tabla
SHOW CREATE TABLE registraratencion;

-- Agregar AUTO_INCREMENT a idAtencion
-- Primero obtener el máximo idAtencion actual
SET @max_id = (SELECT IFNULL(MAX(idAtencion), 0) FROM registraratencion);

-- Modificar la columna para agregar AUTO_INCREMENT
ALTER TABLE registraratencion 
MODIFY COLUMN idAtencion INT NOT NULL AUTO_INCREMENT;

-- Opcional: Ajustar el AUTO_INCREMENT para que comience después del máximo actual
SET @next_id = @max_id + 1;
SET @sql = CONCAT('ALTER TABLE registraratencion AUTO_INCREMENT = ', @next_id);
PREPARE stmt FROM @sql;
EXECUTE stmt;
DEALLOCATE PREPARE stmt;

-- Verificar que se aplicó correctamente
SHOW CREATE TABLE registraratencion;

