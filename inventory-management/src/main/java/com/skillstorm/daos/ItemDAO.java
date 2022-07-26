package com.skillstorm.daos;

import java.util.List;

import com.skillstorm.models.Item;

public interface ItemDAO {
		// create
		public Item save(Item Item);
		
		// read
		public List<Item> findall();
		public Item findById(int id);
		public Item findByName(String name);
		
		// update
		public void updateName(Item item);
		public void updateDescription(Item item);
		public void updateSize(Item item);
		
		// delete
		public boolean delete(Item item);
		public boolean delete(int id);
		public boolean deleteMany(int[] ids);
}
