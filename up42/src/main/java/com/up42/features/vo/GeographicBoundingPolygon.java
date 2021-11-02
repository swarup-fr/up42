package com.up42.features.vo;

import java.util.List;

public class GeographicBoundingPolygon {
	private List<List<List<Double>>> coordinates;
	private String type;
	public List<List<List<Double>>> getCoordinates() {
		return coordinates;
	}
	public void setCoordinates(List<List<List<Double>>> coordinates) {
		this.coordinates = coordinates;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	
}
