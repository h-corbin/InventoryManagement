package com.skillstorm.models;

public class ExtendedInventory {
	
	int itemId;
	int warehouseId;
	int quantity;
	private String location;
	String warehouseName; 
	String itemName;
	String itemDescription;
	
	public ExtendedInventory() {}
	
	public ExtendedInventory(int itemId, int warehouseId, int quantity, String location, String warehouseName,
			String itemName, String itemDescription) {
		super();
		this.itemId = itemId;
		this.warehouseId = warehouseId;
		this.quantity = quantity;
		this.location = location;
		this.warehouseName = warehouseName;
		this.itemName = itemName;
		this.itemDescription = itemDescription;
	}

	public int getItemId() {
		return itemId;
	}

	public int getWarehouseId() {
		return warehouseId;
	}

	public int getQuantity() {
		return quantity;
	}

	public String getLocation() {
		return location;
	}

	public String getWarehouseName() {
		return warehouseName;
	}
	
	public String getItemName() {
		return itemName;
	}

	public String getItemDescription() {
		return itemDescription;
	}

	@Override
	public String toString() {
		return "ExtendedInventory [itemId=" + itemId + ", warehouseId=" + warehouseId + ", quantity=" + quantity
				+ ", location=" + location + ", warehouseName=" + warehouseName + ", itemName=" + itemName
				+ ", itemDescription=" + itemDescription + "]";
	}
	
	
	
	
}
