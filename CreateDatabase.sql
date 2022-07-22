
DROP DATABASE IF EXISTS InventoryManagement;
CREATE DATABASE InventoryManagement;
USE InventoryManagement;

CREATE TABLE Warehouse (
  WarehouseId INT  NOT NULL AUTO_INCREMENT,
  Name VARCHAR(45),
  Address VARCHAR(45),
  Capacity DECIMAL(12,4),
  CurrentVolume DECIMAL(12,4),
  PRIMARY KEY (WarehouseId)
);


CREATE TABLE Item (
  ItemId INT NOT NULL AUTO_INCREMENT,
  Name VARCHAR(45),
  Description VARCHAR(500),
  Size DECIMAL(12,4) NOT NULL,
  PRIMARY KEY (itemId)
);


CREATE TABLE Inventory (
  ItemId INT NOT NULL,
  WarehouseId INT NOT NULL,
  Quantity INT NOT NULL,
  Location VARCHAR(45),
  PRIMARY KEY (ItemId, WarehouseId),
  INDEX warehouse_idx (WarehouseId ASC) VISIBLE,
  CONSTRAINT item FOREIGN KEY (ItemId) REFERENCES Item (ItemId),
  CONSTRAINT warehouse FOREIGN KEY (WarehouseId) REFERENCES Warehouse (WarehouseId)
);


DELIMITER $$
USE `InventoryManagement`$$
CREATE DEFINER = CURRENT_USER TRIGGER `InventoryManagement`.`Inventory_AFTER_INSERT` AFTER INSERT ON `Inventory` FOR EACH ROW
BEGIN
	UPDATE Warehouse
    SET CurrentVolume = CurrentVolume + 
    (NEW.QUANTITY * (SELECT Size FROM Item WHERE ItemID = NEW.ItemID))
    WHERE WarehouseId = new.WarehouseId;
END$$
DELIMITER ;


DELIMITER $$
USE `InventoryManagement`$$
CREATE DEFINER = CURRENT_USER TRIGGER `InventoryManagement`.`Inventory_AFTER_UPDATE` AFTER UPDATE ON `Inventory` FOR EACH ROW
BEGIN
    UPDATE Warehouse
    SET CurrentVolume = CurrentVolume + 
    (NEW.QUANTITY - OLD.QUANTITY) * 
    (SELECT Size FROM Item WHERE ItemID = NEW.ItemID);
END$$
DELIMITER ;


DELIMITER $$
USE `InventoryManagement`$$
CREATE DEFINER = CURRENT_USER TRIGGER `InventoryManagement`.`Inventory_AFTER_DELETE` AFTER DELETE ON `Inventory` FOR EACH ROW
BEGIN
    UPDATE Warehouse
    SET CurrentVolume = CurrentVolume - 
    OLD.QUANTITY * (SELECT Size FROM Item WHERE ItemID = OLD.ItemID);
END$$
DELIMITER ;
