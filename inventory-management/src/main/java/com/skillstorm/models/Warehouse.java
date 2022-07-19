package com.skillstorm.models;

public class Warehouse {
	
	private int id;
	private String name;
	private String location;
	private double capacity;
	private double volume;
	
	
	public Warehouse() {
		super();
	}


	public Warehouse(String name) {
		this(name, 0, 0);
	}


	public Warehouse(String name, double capacity) {
		this(0, name, "", capacity, 0.0);
	}
	
	public Warehouse(String name, String location, double capacity) {
		this(0, name, location, capacity, 0.0);
	}


	public Warehouse(String name, double capacity, double volume) {
		this(0, name, "", capacity, volume);
	}
	
	public Warehouse(String name, String location, double capacity, double volume) {
		this(0, name, location, capacity, volume);
	}
	
	public Warehouse(int id, String name, String location, double capacity, double volume) {
		super();
		this.id = id;
		this.name = name;
		this.location = location;
		this.capacity = capacity;
		this.volume = volume;
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
	
	public String getLocation() {
		return location;
	}


	public void setLocation(String location) {
		this.location = location;
	}

	public double getCapacity() {
		return capacity;
	}


	public void setCapacity(double capacity) {
		this.capacity = capacity;
	}


	public double getVolume() {
		return volume;
	}


	public void setVolume(double volume) {
		this.volume = volume;
	}


	@Override
	public String toString() {
		return "Warehouse [id=" + id + ", name=" + name + ", location=" + location + ", capacity=" + capacity
				+ ", volume=" + volume + "]";
	}

	
	
	
	
	
	

}
