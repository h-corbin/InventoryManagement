package com.skillstorm.filters;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletResponse;

@WebFilter(urlPatterns = "/*")
public class CORSFilter implements Filter{
	
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {}
	@Override
	public void destroy() {}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletResponse res = (HttpServletResponse) response;
		
		// allow Angular access to servlets
		res.addHeader("Access-Control-Allow-Origin", "*");
		// allow all http requests
		res.addHeader("Access-Control-Allow-Methods", "*");
		res.addHeader("Access-Control-Allow-Headers", "*");
		
		// pass request along filter chain
		chain.doFilter(request, response);
	}

}
