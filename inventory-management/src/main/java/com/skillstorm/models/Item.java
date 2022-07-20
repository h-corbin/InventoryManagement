package com.skillstorm.models;

public class Item {

	private int id; 
	private String name;
	private String description;
	private double size;
	private String location;
	
	public Item() {}

	public Item(String name, double size) {
		this(0, name, "", size, "");
	}

	public Item(String name, String description, double size) {
		this(0, name, description, size, "");
	}
	
	public Item(String name, double size, String location) {
		this(0, name, "", size, location);
	}
	
	public Item(String name, String description, double size, String location) {
		this(0, name, description, size, location);
	}
	
	public Item(int id, String name, String description, double size, String location) {
		super();
		this.id = id;
		this.name = name;
		this.description = description;
		this.size = size;
		this.location = location;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public double getSize() {
		return size;
	}

	public void setSize(double size) {
		this.size = size;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	@Override
	public String toString() {
		return "Item [id=" + id + ", name=" + name + ", description=" + description + ", size=" + size + ", location="
				+ location + "]";
	}
	
	
	
	
	
}
