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
	// update
	public void updateName(Warehouse warehouse);
	public void updateCapacity(Warehouse warehouse);
	public void updateVolume(Warehouse warehouse);
	public void updateAddress(Warehouse warehouse);
	
	// delete
	public int delete(Warehouse warehouse);
	public int delete(int id);
	public int deleteMany(int[] ids);
}
