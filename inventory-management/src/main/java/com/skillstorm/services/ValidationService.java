package com.skillstorm.services;

import com.skillstorm.models.Item;
import com.skillstorm.models.Warehouse;

public class ValidationService {
	
	public boolean validateCapacity(Item item, Warehouse warehouse) {
		if (warehouse.getVolume() + item.getSize() <= warehouse.getCapacity()) {
			return true;
		}
		
		return false;
	}
	
	public boolean validateNums(double num) {
		if (num < 0.0) {
			return false;
		}
		return true;
	}

}
