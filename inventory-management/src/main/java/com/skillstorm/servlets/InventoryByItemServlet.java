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
import com.skillstorm.models.Inventory;

@WebServlet(urlPatterns = "/inventory/item")
public class InventoryByItemServlet  extends HttpServlet {

	private static final long serialVersionUID = -8130648736890659190L;

	InventoryDAO dao = new MySQLInventoryDAOImpl();
	ObjectMapper mapper = new ObjectMapper();

	@Override
	// get inventory by by ItemID
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		InputStream reqBody = req.getInputStream(); // get JSON request body
		Inventory inventory = mapper.readValue(reqBody, Inventory.class); // translate to object
		
		List<Inventory> inventoryList = dao.findByItemId(inventory.getItemId());
		if (inventoryList != null) {
			resp.setContentType("application/json");
			resp.getWriter().print(mapper.writeValueAsString(inventoryList));
		} else {
			resp.setStatus(404); // not found
		}
		
	}
}
