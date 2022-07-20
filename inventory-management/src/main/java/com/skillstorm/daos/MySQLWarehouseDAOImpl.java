package com.skillstorm.daos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;

import com.skillstorm.conf.InventoryManagementDBCreds;
import com.skillstorm.models.Warehouse;


public class MySQLWarehouseDAOImpl implements WarehouseDAO{
	

	/**
	 * @param warehouse a new warehouse object to save to the database
	 * @return the warehouse object with new generated id. Null in the event of failure
	 */
	@Override
	public Warehouse save(Warehouse warehouse) {
		String sql = "INSERT INTO Warehouse (Name, Address, Capacity, CurrentVolume) VALUES (?, ?, ?, ?)";
		
		try (Connection conn = InventoryManagementDBCreds.getInstance().getConnection()) {
			// start a transaction
			conn.setAutoCommit(false);
			PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			ps.setString(1, warehouse.getName());
			ps.setString(2, warehouse.getAddress());
			ps.setDouble(3, warehouse.getCapacity());
			ps.setDouble(4, warehouse.getVolume());
			
			int rowsAffected = ps.executeUpdate();
			// if 0 is returned, data did not save
			if (rowsAffected != 0) {
				ResultSet keys = ps.getGeneratedKeys();
				if (keys.next()) {
					int key = keys.getInt(1);
					warehouse.setId(key);
					conn.commit(); // commit transaction
					return warehouse;
				} else {conn.rollback();}
			} else {
				conn.rollback(); // rollback if nothing was returned
			} 
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		
		return null;
	}
	
	
	
	/**
	 * @return List of all warehouses in the database. Null in the event of failure
	 */
	@Override
	public List<Warehouse> findall() {
		String sql = "SELECT * FROM Warehouse";
		
		// Connection will auto close in event of a failure
		// must create connection in every method
		try (Connection conn = InventoryManagementDBCreds.getInstance().getConnection()) {
			LinkedList<Warehouse> warehouseList = new LinkedList<>();
			PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			ResultSet rs = ps. executeQuery();
			
			while(rs.next()) {
				Warehouse warehouse = new Warehouse(rs.getInt("WarehouseId"), rs.getString("Name"), rs.getString("Address"), rs.getDouble("Capacity"), rs.getDouble("CurrentVolume"));
				warehouseList.add(warehouse);
			}
	
			return warehouseList;
		} catch (SQLException e) {
			return null;
		}
	}
	
	
	
	// helper for find methods
	private Warehouse executeFind(String sql, Object findValue) {
		try (Connection conn = InventoryManagementDBCreds.getInstance().getConnection()) {
			PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			ps.setObject(1, findValue);
			ResultSet rs = ps.executeQuery();
			
			if (rs.next()) {
				return new Warehouse(rs.getInt("WarehouseId"), rs.getString("Name"), rs.getString("Address"), rs.getDouble("Capacity"), rs.getDouble("CurrentVolume"));	
			}
		} catch (SQLException e) {
			return null;
		}
		return null;
	}

	/**
	 * @param id WarehouseId of the warehouse to search for
	 * @return Warehouse object with the given id, if found. Null if not found
	 */
	@Override
	public Warehouse findById(int id) {
		String sql = "SELECT * FROM Warehouse WHERE WarehouseId = ?";
		return executeFind(sql, id);
	}

	/**
	 * @param name name of the warehouse to search for
	 * @return Warehouse object with the given name, if found. Null if not found
	 */
	@Override
	public Warehouse findByName(String name) {
		String sql = "SELECT * FROM Warehouse WHERE Name = ?";
		return executeFind(sql, name);
	}
	
	
	// helper for update methods
	public void executeUpdate(String sql, Warehouse warehouse, Object updatedValue) {
		try (Connection conn = InventoryManagementDBCreds.getInstance().getConnection()) {
			conn.setAutoCommit(false); // start a transaction
			
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setObject(1, updatedValue); 
			ps.setInt(2, warehouse.getId());
			
			int rowsAffected = ps.executeUpdate();
			
			// if 0 is returned, data did not save
			if (rowsAffected != 0) {
				conn.commit(); // commit transaction
			} else {
				conn.rollback(); // rollback if nothing was returned
			} 
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * @param warehouse Warehouse object with name to be updated in the database
	 */
	@Override
	public void updateName(Warehouse warehouse) {
		String sql = "UPDATE Warehouse SET Name = ? WHERE WarehouseId = ?";
		executeUpdate(sql, warehouse, warehouse.getName());
	}

	/**
	 * @param warehouse Warehouse object with capacity field to be updated in the database
	 */
	@Override
	public void updateCapacity(Warehouse warehouse) {
		String sql = "UPDATE Warehouse SET Capacity = ? WHERE WarehouseId = ?";
		executeUpdate(sql, warehouse, warehouse.getCapacity());
	}

	
	/**
	 * @param warehouse Warehouse object with volume field to be updated in the database
	 */
	@Override
	public void updateVolume(Warehouse warehouse) {
		String sql = "UPDATE Warehouse SET CurrentVolume = ? WHERE WarehouseId = ?";
		executeUpdate(sql, warehouse, warehouse.getVolume());
	}
	
	/**
	 * @param warehouse Warehouse object with location field to be updated in the database
	 */
	@Override
	public void updateAddress(Warehouse warehouse) {
		String sql = "UPDATE Warehouse SET Address = ? WHERE WarehouseId = ?";
		executeUpdate(sql, warehouse, warehouse.getAddress());
	}


	/**
	 * @param id WarehouseId for row to delete in database
	 */
	@Override
	public void delete(int id) {
		String sql = "DELETE FROM Warehouse WHERE WarehouseId = ?";
		
		try (Connection conn = InventoryManagementDBCreds.getInstance().getConnection()) {
			conn.setAutoCommit(false); // start a transaction
			
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, id);
			int rowsAffected = ps.executeUpdate();
			
			if (rowsAffected != 0) {
				conn.commit(); // commit transaction
			} else {
				conn.rollback(); // rollback if nothing was returned
			} 
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	
	@Override
	public void delete(Warehouse warehouse) {
		delete(warehouse.getId());
	}

	@Override
	public void deleteMany(int[] ids) {
		for (int id : ids) {
			delete(id);
		}
	}
}
