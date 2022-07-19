package com.skillstorm.conf;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class InventoryManagementDBCreds {
	private static InventoryManagementDBCreds instance;
	private String url;
	private String username;
	private String password;
	
	private InventoryManagementDBCreds() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver"); // JDBC Driver 
		
			// read the properties from application.properties
			try (InputStream input = InventoryManagementDBCreds.class.getClassLoader().getResourceAsStream("application.properties")) {
				// Properties object
				Properties props = new Properties();
				props.load(input);
				
				this.url = props.getProperty("db.url");
				this.username = props.getProperty("db.username");
				this.password = props.getProperty("db.password");
			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public static InventoryManagementDBCreds getInstance() {
		if (instance == null) { // Lazily initialise
			instance = new InventoryManagementDBCreds();
		}
		return instance;
	}

	public Connection getConnection() {
		try {
			return DriverManager.getConnection(url, username, password);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
}
