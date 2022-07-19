package com.skillstorm.daos;

import java.util.List;

import com.skillstorm.models.Warehouse;

public interface WarehouseDAO {
	
	// create
	public Warehouse save(Warehouse warehouse);
	
	// read
	public List<Warehouse> findall();
	public Warehouse findById(int id);
	public Warehouse findByName(String name);
	public Warehouse findByLocation(String name);
	
	// update
	public void updateName(Warehouse warehouse);
	public void updateCapacity(Warehouse warehouse);
	public void updateVolume(Warehouse warehouse);
	public void updateLocation(Warehouse warehouse);
	
	// delete
	public void delete(Warehouse warehouse);
	public void delete(int id);
	public void deleteMany(int[] ids);
}
