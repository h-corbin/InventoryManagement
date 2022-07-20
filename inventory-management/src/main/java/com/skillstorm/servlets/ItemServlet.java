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
import com.skillstorm.daos.ItemDAO;
import com.skillstorm.daos.MySQLItemDAOImpl;
import com.skillstorm.models.Item;
import com.skillstorm.services.UrlParserService;

@WebServlet(urlPatterns = "/item/*")
public class ItemServlet extends HttpServlet{

	private static final long serialVersionUID = -954487132744010736L;
	
	ItemDAO dao = new MySQLItemDAOImpl();
	ObjectMapper mapper = new ObjectMapper();
	UrlParserService urlService = new UrlParserService();
	

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		try {
			int id = urlService.extractIdFromURL(req.getPathInfo());
			// if URL path has an id, request is for a specific item
			Item item = dao.findById(id);
			if (item != null) {
				resp.setContentType("application/json");
				resp.getWriter().print(mapper.writeValueAsString(item));
			} else {
				resp.setStatus(404); // unable to find item
			}
		} catch (Exception e) { // request is for all items
			List<Item> itemList = dao.findall();
			resp.setContentType("application/json");
			resp.getWriter().print(mapper.writeValueAsString(itemList));
		}
	}
	
	@Override
	// create new entry in database
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		InputStream reqBody = req.getInputStream(); // get JSON request body
		Item item = mapper.readValue(reqBody, Item.class); // translate to object
		item = dao.save(item); // add to database, get back object with generated key
		
		if (item == null) { // unable to insert into database
			resp.setStatus(500);
		}
		resp.setContentType("application/json");
		resp.getWriter().print(mapper.writeValueAsString(item));
	}
	
	@Override
	// update entry in database
	protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		InputStream reqBody = req.getInputStream(); // get JSON request body
		Item item = mapper.readValue(reqBody, Item.class); // translate to Warehouse object
		
		// update all attributes
		dao.updateName(item);
		dao.updateDescription(item);
		dao.updateSize(item);
		dao.updateLocation(item);
		resp.setStatus(200);
		
	}
	
	@Override
	// delete entry in database
	protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		InputStream reqBody = req.getInputStream(); // get JSON request body
		Item item = mapper.readValue(reqBody, Item.class); // translate to Warehouse object
		dao.delete(item);
		resp.setStatus(200);
	}

}
