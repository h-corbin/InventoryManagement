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

@WebServlet(urlPatterns = "/warehouse")
public class WarehouseServlet extends HttpServlet {

	private static final long serialVersionUID = -1255978588645665829L;
	
	WarehouseDAO dao = new MySQLWarehouseDAOImpl();
	ObjectMapper mapper = new ObjectMapper();
	
	

	@Override
	// return all 
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		List<Warehouse> warehouseList = dao.findall()
		resp.setContentType("application/json");
		resp.getWriter().print(mapper.writeValueAsString(warehouseList));
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		InputStream reqBody = req.getInputStream(); // get JSON request body
		
		Warehouse warehouse = mapper.readValue(reqBody, Warehouse.class); // translate to Warehouse object
		System.out.println(warehouse);
		
		resp.setContentType("application/json");
		resp.getWriter().print(mapper.writeValueAsString(warehouse));
	}
	
	@Override
	protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		super.doPut(req, resp);
	}
	
	@Override
	protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		super.doDelete(req, resp);
	}
}
