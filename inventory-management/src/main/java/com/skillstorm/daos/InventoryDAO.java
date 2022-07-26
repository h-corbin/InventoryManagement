package com.skillstorm.daos;

import java.util.List;

import com.skillstorm.models.ExtendedInventory;
import com.skillstorm.models.Inventory;

public interface InventoryDAO {
	// create
	public Inventory save(Inventory inventory);
	
	// read
	public List<Inventory> findall();
	public List<ExtendedInventory> extendedFindAll();
	public List<ExtendedInventory> findItemsByWarehouseId(int id);
	public List<Inventory> findByItemId(int id);
	public Inventory findUnique(int warehouseId, int itemId);
	
	// update
	public void updateQuantity(Inventory inventory);
	public void updateLocation(Inventory inventory);
	
	// delete
	public void delete(Inventory inventory);
	public void delete(int warehouseId, int itemId);
	public void deleteMany(List<Inventory> inventoryRows);
}
