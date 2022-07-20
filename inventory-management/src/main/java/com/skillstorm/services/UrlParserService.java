package com.skillstorm.services;

public class UrlParserService {

	public int extractIdFromURL(String url) {
		System.out.println(url);
		String[] splitString = url.split("/"); // split on /
		
		return Integer.parseInt(splitString[1]); // throws exception if it isn't an int
	}
}