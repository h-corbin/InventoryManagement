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
import com.skillstorm.models.Inventory;
import com.skillstorm.services.UrlParserService;

@WebServlet(urlPatterns = "/inventory/*")
public class InventoryServlet extends HttpServlet{

	private static final long serialVersionUID = 7113837821026482129L;

	InventoryDAO dao = new MySQLInventoryDAOImpl();
	ObjectMapper mapper = new ObjectMapper();
	UrlParserService urlService = new UrlParserService();
	

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		try {
			InputStream reqBody = req.getInputStream(); // get JSON request body
			Inventory inventory = mapper.readValue(reqBody, Inventory.class); // translate to object
			// if request has method body, looking for specific inventory row
			inventory = dao.findUnique(inventory.getWarehouseId(), inventory.getItemId());
		
			if (inventory != null) {
				resp.setContentType("application/json");
				resp.getWriter().print(mapper.writeValueAsString(inventory));
			} else {
				resp.setStatus(404); // not found
			}
		} catch (Exception e) { // request is for all inventory rows (extended)
			List<ExtendedInventory> inventoryList = dao.extendedFindAll();
			resp.setContentType("application/json");
			resp.getWriter().print(mapper.writeValueAsString(inventoryList));
		}
	}
	
	@Override
	// create new entry in database
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		InputStream reqBody = req.getInputStream(); // get JSON request body
		Inventory inventory = mapper.readValue(reqBody, Inventory.class); // translate to object
		inventory = dao.save(inventory); // add to database, get back object with generated key
		
		if (inventory == null) { // unable to insert into database
			resp.setStatus(500);
		} else { 
			resp.setContentType("application/json");
			resp.getWriter().print(mapper.writeValueAsString(inventory));
			resp.setStatus(201);
		}
	}
	
	@Override
	// update entry in database
	protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		InputStream reqBody = req.getInputStream(); // get JSON request body
		Inventory inventory = mapper.readValue(reqBody, Inventory.class); // translate to object
		
		// update all attributes
		dao.updateQuantity(inventory);
		dao.updateLocation(inventory);
		resp.setStatus(200);
		
	}
	
	@Override
	// delete entry in database
	protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		InputStream reqBody = req.getInputStream(); // get JSON request body
		Inventory inventory = mapper.readValue(reqBody, Inventory.class); // translate to object
		dao.delete(inventory);
		resp.setStatus(200);
	}
}
