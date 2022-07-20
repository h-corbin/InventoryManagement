package com.skillstorm.daos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;

import com.skillstorm.conf.InventoryManagementDBCreds;
import com.skillstorm.models.Item;

public class MySQLItemDAOImpl implements ItemDAO{

	/**
     * @param item a new Item to save to the database
     * @return the item with new generated id. Null in the event of failure
     */
	@Override
	public Item save(Item item) {
        String sql = "INSERT INTO Item (Name, Description, Size, Location) VALUES (?, ?, ?, ?)";

        try (Connection conn = InventoryManagementDBCreds.getInstance().getConnection()) {
            // start a transaction
            conn.setAutoCommit(false);
            PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, item.getName());
            ps.setString(2, item.getDescription());
            ps.setDouble(3, item.getSize());
            ps.setString(4, item.getLocation());

            int rowsAffected = ps.executeUpdate();
            // if 0 is returned, data did not save
            if (rowsAffected != 0) {
                ResultSet keys = ps.getGeneratedKeys();
                if (keys.next()) {
                    int key = keys.getInt(1);
                    item.setId(key);
                    conn.commit(); // commit transaction
                    return item;
                } else {conn.rollback();}
            } else {
                conn.rollback(); // rollback if nothing was returned
            }
        } catch (SQLException e) {
            return null;
        }

        return null;
    }

	/**
     * @return List of all items in the database. Null in the event of failure
     */
	@Override
    public List<Item> findall() {
        String sql = "SELECT * FROM Item";

        try (Connection conn = InventoryManagementDBCreds.getInstance().getConnection()) {
            LinkedList<Item> itemList = new LinkedList<>();
            PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ResultSet rs = ps. executeQuery();

            while(rs.next()) {
                Item item = new Item(rs.getInt("ItemId"), rs.getString("Name"), rs.getString("Description"), rs.getDouble("Size"), rs.getString("Location"));
                itemList.add(item);
            }
            return itemList;
        } catch (SQLException e) {
            return null;
        }
    };
    
    
    // helper for find methods
    private Item executeFind(String sql, Object findValue) {
        try (Connection conn = InventoryManagementDBCreds.getInstance().getConnection()) {
            PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setObject(1, findValue);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return new Item(rs.getInt("ItemId"), rs.getString("Name"), rs.getString("Description"), rs.getDouble("Size"), rs.getString("Location"));
            }
        } catch (SQLException e) {
            return null;
        }
        return null;
    }

    /**
     * @param id ItemId of the item to search for
     * @return Item object with the given id, if found. Null if not found
     */
	@Override
	public Item findById(int id) {
		String sql = "SELECT * FROM Item WHERE ItemId = ?";
		return executeFind(sql, id);
	}
	
	/**
     * @param name Name of the item to search for
     * @return Item object with the given name, if found. Null if not found
     */
	@Override
	public Item findByName(String name) {
		String sql = "SELECT * FROM Item WHERE Name = ?";
        return executeFind(sql, name);
	}
	
	
	
	// helper method for updates
    private void executeUpdate(String sql, Item item, Object updatedValue) {
        try (Connection conn = InventoryManagementDBCreds.getInstance().getConnection()) {
            conn.setAutoCommit(false); // start a transaction

            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setObject(1, updatedValue);
            ps.setInt(2, item.getId());

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
     * @param item Item object with name to be updated in the database
     */
	@Override
	public void updateName(Item item) {
		String sql = "UPDATE Item SET Name = ? WHERE ItemId = ?";
        executeUpdate(sql, item, item.getName());
	}

	/**
     * @param item Item object with description to be updated in the database
     */
	@Override
	public void updateDescription(Item item) {
		String sql = "UPDATE Item SET Description = ? WHERE ItemId = ?";
        executeUpdate(sql, item, item.getDescription());
	}

	/**
     * @param item Item object with size to be updated in the database
     */
	@Override
	public void updateSize(Item item) {
		String sql = "UPDATE Item SET Size = ? WHERE ItemId = ?";
        executeUpdate(sql, item, item.getSize());
	}
	
	/**
     * @param item Item object with location to be updated in the database
     */
	@Override
	public void updateLocation(Item item) {
		String sql = "UPDATE Item SET Location = ? WHERE ItemId = ?";
        executeUpdate(sql, item, item.getSize());
	}


	/**
     * @param id ItemId for row to delete in database
     */
	@Override
	public void delete(int id) {
		String sql = "DELETE FROM Item WHERE ItemId = ?";

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
	public void delete(Item item) {
		delete(item.getId());
	}

	@Override
	public void deleteMany(int[] ids) {
		for (int id : ids) {
            delete(id);
        }
	}

}
