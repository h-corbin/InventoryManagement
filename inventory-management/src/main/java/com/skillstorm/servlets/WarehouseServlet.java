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
import com.skillstorm.daos.MySQLWarehouseDAOImpl;
import com.skillstorm.daos.WarehouseDAO;
import com.skillstorm.models.Warehouse;
import com.skillstorm.services.UrlParserService;

@WebServlet(urlPatterns = "/warehouse/*")
public class WarehouseServlet extends HttpServlet {

	private static final long serialVersionUID = -1255978588645665829L;
	
	WarehouseDAO dao = new MySQLWarehouseDAOImpl();
	ObjectMapper mapper = new ObjectMapper();
	UrlParserService urlService = new UrlParserService();
	

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		try {
			int id = urlService.extractIdFromURL(req.getPathInfo());
			// if URL path has an id, request is for a specific warehouse
			Warehouse warehouse = dao.findById(id);
			if (warehouse != null) {
				resp.setContentType("application/json");
				resp.getWriter().print(mapper.writeValueAsString(warehouse));
			} else {
				resp.setStatus(401); // unable to find warehouse
			}
		} catch (Exception e) { // request is for all warehouses
			List<Warehouse> warehouseList = dao.findall();
			resp.setContentType("application/json");
			resp.getWriter().print(mapper.writeValueAsString(warehouseList));
		}
	}
	
	@Override
	// create new entry in database
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		InputStream reqBody = req.getInputStream(); // get JSON request body
		Warehouse warehouse = mapper.readValue(reqBody, Warehouse.class); // translate to Warehouse object
		warehouse = dao.save(warehouse); // add to database, get back object with generated key
		
		if (warehouse == null) { // unable to insert into database
			resp.setStatus(500);
		}
		resp.setContentType("application/json");
		resp.getWriter().print(mapper.writeValueAsString(warehouse));
	}
	
	@Override
	// update entry in database
	protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		InputStream reqBody = req.getInputStream(); // get JSON request body
		Warehouse warehouse = mapper.readValue(reqBody, Warehouse.class); // translate to Warehouse object
		
		// update all attributes
		dao.updateName(warehouse);
		dao.updateCapacity(warehouse);
		dao.updateLocation(warehouse);
		dao.updateVolume(warehouse);
		resp.setStatus(200);
		
	}
	
	@Override
	// delete entry in database
	protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		InputStream reqBody = req.getInputStream(); // get JSON request body
		Warehouse warehouse = mapper.readValue(reqBody, Warehouse.class); // translate to Warehouse object
		dao.delete(warehouse);
		resp.setStatus(200);
	}
}
