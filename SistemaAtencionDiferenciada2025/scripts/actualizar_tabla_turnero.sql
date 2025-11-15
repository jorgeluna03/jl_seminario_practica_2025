-- Script para actualizar la tabla turnero con los nuevos campos
-- Ejecutar en la base de datos: sistemaatenciondiferenciada

USE sistemaatenciondiferenciada;

-- Agregar nuevos campos a la tabla turnero
ALTER TABLE `turnero` 
ADD COLUMN `idBox` INT NULL AFTER `estado`,
ADD COLUMN `idGestion` INT NULL AFTER `idBox`,
ADD COLUMN `prioridad` INT NULL AFTER `idGestion`,
ADD COLUMN `segmentoScore` INT NULL AFTER `prioridad`,
ADD COLUMN `score` INT NULL AFTER `segmentoScore`,
ADD COLUMN `orden` INT NULL AFTER `score`;

-- Crear índices para mejorar el rendimiento de las consultas
CREATE INDEX `idx_turnero_idBox` ON `turnero` (`idBox`);
CREATE INDEX `idx_turnero_idGestion` ON `turnero` (`idGestion`);
CREATE INDEX `idx_turnero_score` ON `turnero` (`score`);
CREATE INDEX `idx_turnero_estado_fecha` ON `turnero` (`estado`, `fecha`);
CREATE INDEX `idx_turnero_box_orden` ON `turnero` (`idBox`, `orden`);

-- Agregar foreign key constraints
ALTER TABLE `turnero` 
ADD CONSTRAINT `fk_turnero_box` 
  FOREIGN KEY (`idBox`) 
  REFERENCES `box_atencion` (`idBox`) 
  ON DELETE RESTRICT 
  ON UPDATE RESTRICT,
ADD CONSTRAINT `fk_turnero_gestion` 
  FOREIGN KEY (`idGestion`) 
  REFERENCES `gestiones` (`idGestion`) 
  ON DELETE RESTRICT 
  ON UPDATE RESTRICT;

-- Verificar que los campos se agregaron correctamente
DESCRIBE `turnero`;

