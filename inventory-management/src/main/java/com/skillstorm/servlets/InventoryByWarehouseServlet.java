package com.skillstorm.servlets;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.skillstorm.daos.InventoryDAO;
import com.skillstorm.daos.MySQLInventoryDAOImpl;
import com.skillstorm.models.ExtendedInventory;
import com.skillstorm.models.Warehouse;

@WebServlet(urlPatterns = "/inventory/warehouse")
public class InventoryByWarehouseServlet  extends HttpServlet {

	private static final long serialVersionUID = -6166710461978527468L;
	
	InventoryDAO dao = new MySQLInventoryDAOImpl();
	ObjectMapper mapper = new ObjectMapper();

	@Override
	// get inventory by by WarehouseID
	// using put request because Angular doesn't have method body on get request
	protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		InputStream reqBody = req.getInputStream(); // get JSON request body
		Warehouse warehouse = mapper.readValue(reqBody, Warehouse.class); // translate to object
		
		List<ExtendedInventory> inventoryList = dao.findItemsByWarehouseId(warehouse.getId());
		if (inventoryList != null) {
			resp.setContentType("application/json");
			resp.getWriter().print(mapper.writeValueAsString(inventoryList));
		} else {
			resp.setStatus(404); // not found
		}
		 
	}
}
