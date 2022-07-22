package com.skillstorm.daos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;

import com.skillstorm.conf.InventoryManagementDBCreds;
import com.skillstorm.models.ExtendedInventory;
import com.skillstorm.models.Inventory;

public class MySQLInventoryDAOImpl implements InventoryDAO{

	/**
	 * @param inventory a new inventory object to save to the database
	 * @return the inventory object with new generated id. Null in the event of failure
	 */
	@Override
	public Inventory save(Inventory inventory) {
		String sql = "INSERT INTO Inventory (ItemId, WarehouseId, Quantity, Location) VALUES (?, ?, ?, ?)";
		
		try (Connection conn = InventoryManagementDBCreds.getInstance().getConnection()) {
			// start a transaction
			conn.setAutoCommit(false);
			PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			ps.setInt(1, inventory.getItemId());
			ps.setInt(2, inventory.getWarehouseId());
			ps.setInt(3, inventory.getQuantity());
			ps.setString(4, inventory.getLocation());
			
			int rowsAffected = ps.executeUpdate();
			// if 0 is returned, data did not save
			if (rowsAffected != 0) {
				conn.commit(); // commit transaction
				return inventory;
			} else {
				conn.rollback(); // rollback if nothing was returned
			} 
		} catch (SQLException e) {
			return null;
		}
		
		return null;
	}
	

	
	// helper for find methods
	private List<Inventory> executeFind(String sql, Object findValue) {
		try (Connection conn = InventoryManagementDBCreds.getInstance().getConnection()) {
			LinkedList<Inventory> inventoryList = new LinkedList<>();
			PreparedStatement ps = conn.prepareStatement(sql);
			if (findValue != null) ps.setObject(1, findValue);
			ResultSet rs = ps. executeQuery();
			
			while(rs.next()) {
				Inventory inventory = new Inventory(rs.getInt("ItemId"), rs.getInt("WarehouseId"), rs.getInt("Quantity"));
				inventoryList.add(inventory);
			}
	
			return inventoryList;
		} catch (SQLException e) {
			return null;
		}
	}
	
	
	/**
	 * @return List of all inventory rows in the database. Null in the event of failure
	 */
	@Override
	public List<Inventory> findall() {
		String sql = "SELECT * FROM Inventory";
		return executeFind(sql, null);
	}
	
	
	
	/**
	 * @param id ItemId of the warehouse to search for
	 * @return Warehouse object with the given id, if found. Null if not found
	 */
	@Override
	public List<Inventory> findByItemId(int id) {
		String sql = "SELECT * FROM Inventory WHERE ItemId = ?";
		return executeFind(sql, id);
	}
	
	
	// helper for extended find methods
	private List<ExtendedInventory> executeExtendedFind(String sql, Integer findValue) {
		try (Connection conn = InventoryManagementDBCreds.getInstance().getConnection()) {
			LinkedList<ExtendedInventory> inventoryList = new LinkedList<>();		
			PreparedStatement ps = conn.prepareStatement(sql);
			if (findValue != null) ps.setObject(1, findValue);
			ResultSet rs = ps. executeQuery();
			
			
			while(rs.next()) {
				ExtendedInventory inventory = new ExtendedInventory(rs.getInt("Item.ItemId"), rs.getInt("Warehouse.WarehouseId"), rs.getInt("Inventory.Quantity"),
						rs.getString("Inventory.Location"), rs.getString("Warehouse.Name"), rs.getString("Item.Name"),  rs.getString("Item.Description"));
				inventoryList.add(inventory);
			}
	
			return inventoryList;
		} catch (SQLException e) {
			return null;
		}
	}
	
	
	/**
	 * @return List of rows in Inventory table, inner join with Warehouse and Item. Null in the event of failure
	 */
	@Override
	public List<ExtendedInventory> extendedFindAll() {
		String sql = "SELECT Inventory.Quantity, Inventory.Location, Warehouse.WarehouseId, Item.ItemId, "
				+ "Warehouse.Name, Item.Name, Item.Description "
				+ "FROM Inventory "
				+ "JOIN Warehouse ON Inventory.WarehouseId = Warehouse.WarehouseId "
				+ "JOIN Item ON Inventory.ItemId = Item.ItemId";
		return executeExtendedFind(sql, null);
	}

	/**
	 * @param id WarehouseId of the warehouse to search for
	 * @return Warehouse object with the given id, if found. Null if not found
	 */
	@Override
	public List<ExtendedInventory> findItemsByWarehouseId(int id) {
		String sql = "SELECT Inventory.Quantity, Inventory.Location, Warehouse.WarehouseId, Item.ItemId, "
				+ "Warehouse.Name, Item.Name, Item.Description "
				+ "FROM Inventory "
				+ "JOIN Warehouse ON Inventory.WarehouseId = Warehouse.WarehouseId "
				+ "JOIN Item ON Inventory.ItemId = Item.ItemId "
				+ "WHERE Warehouse.WarehouseID = ?";
		return executeExtendedFind(sql, id);
	}

	

	/**
	 * @param warehouseId WarehouseId of the inventory row to search for
	 * @param itemId ItemId of the inventory row to search for
	 * @return Inventory row with composite key matching the given warehouseId and itemId, if found. Null if not found
	 */
	@Override
	public Inventory findUnique(int warehouseId, int itemId) {
		String sql = "SELECT * FROM Inventory WHERE WarehouseId = ? AND ItemId = ?";
		try (Connection conn = InventoryManagementDBCreds.getInstance().getConnection()) {
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setObject(1, warehouseId);
			ps.setObject(2, itemId);
			ResultSet rs = ps. executeQuery();
			
			if (rs.next()) {
				return new Inventory(rs.getInt("ItemId"), rs.getInt("WarehouseId"), rs.getInt("Quantity"));
			}
	
		} catch (SQLException e) {
			return null;
		}
		return null; // indicates failure
	}

	
	// helper method for updates
    private void executeUpdate(String sql, Inventory inventory, Object updatedValue) {
    	try (Connection conn = InventoryManagementDBCreds.getInstance().getConnection()) {
			conn.setAutoCommit(false); // start a transaction
			
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setObject(1, updatedValue);
			ps.setInt(2, inventory.getWarehouseId());
			ps.setInt(3, inventory.getItemId() );
			
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
	 * @param inventory Inventory object with quantity to be updated in the database
	 */
	@Override
	public void updateQuantity(Inventory inventory) {
		String sql = "UPDATE Inventory SET Quantity = ? WHERE WarehouseId = ? AND ItemId = ?";
		executeUpdate(sql, inventory, inventory.getQuantity());
		
	}
	
	/**
     * @param inventory Inventory object with location to be updated in the database
     */
	@Override
	public void updateLocation(Inventory inventory) {
		String sql = "UPDATE Inventory SET Location = ? WHERE WarehouseId = ? AND ItemId = ?";
        executeUpdate(sql, inventory, inventory.getLocation());
	}


	/**
	 * @param warehouseId WarehouseId of the inventory row to delete
	 * @param itemId ItemId of the inventory row to delete
	 */
	@Override
	public void delete(int warehouseId, int itemId) {
		String sql = "DELETE FROM Inventory WHERE WarehouseId = ? AND ItemId = ?";
		
		try (Connection conn = InventoryManagementDBCreds.getInstance().getConnection()) {
			conn.setAutoCommit(false); // start a transaction
			
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, warehouseId);
			ps.setInt(2, itemId);
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
	public void delete(Inventory inventory) {
		delete(inventory.getWarehouseId(), inventory.getItemId());
	}

	@Override
	public void deleteMany(List<Inventory> inventoryRows) {
		for (Inventory i: inventoryRows) {
			delete(i);
		}
	}

	
}
