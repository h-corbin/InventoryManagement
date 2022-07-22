package com.skillstorm.models;

public class Inventory {
	
	int itemId;
	int warehouseId;
	int quantity;
	private String location;
	
	public Inventory() {}
	
	public Inventory(int itemId, int warehouseId) {
		this(itemId, warehouseId, 0, "");
	}
	
	public Inventory(int itemId, int warehouseId, int quantity) {
		this(itemId, warehouseId, quantity, "");
	}

	public Inventory(int itemId, int warehouseId, int quantity, String location) {
		super();
		this.itemId = itemId;
		this.warehouseId = warehouseId;
		this.quantity = quantity;
		this.location = location;
	}

	public int getItemId() {
		return itemId;
	}

	public void setItemId(int itemId) {
		this.itemId = itemId;
	}

	public int getWarehouseId() {
		return warehouseId;
	}

	public void setWarehouseId(int warehouseId) {
		this.warehouseId = warehouseId;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	
	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	@Override
	public String toString() {
		return "Inventory [itemId=" + itemId + ", warehouseId=" + warehouseId + ", quantity=" + quantity + ", location="
				+ location + "]";
	}

	
	

}
