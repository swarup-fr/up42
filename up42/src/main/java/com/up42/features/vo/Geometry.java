package com.up42.features.vo;

import java.util.List;

public class Geometry{
    private String type;
    private List<List<List<Double>>> coordinates;
    private GeographicBoundingPolygon geographicBoundingPolygon;
    private boolean global;
    private CenterPoint centerPoint;
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public List<List<List<Double>>> getCoordinates() {
		return coordinates;
	}
	public void setCoordinates(List<List<List<Double>>> coordinates) {
		this.coordinates = coordinates;
	}
	public GeographicBoundingPolygon getGeographicBoundingPolygon() {
		return geographicBoundingPolygon;
	}
	public void setGeographicBoundingPolygon(GeographicBoundingPolygon geographicBoundingPolygon) {
		this.geographicBoundingPolygon = geographicBoundingPolygon;
	}
	public boolean isGlobal() {
		return global;
	}
	public void setGlobal(boolean global) {
		this.global = global;
	}
	public CenterPoint getCenterPoint() {
		return centerPoint;
	}
	public void setCenterPoint(CenterPoint centerPoint) {
		this.centerPoint = centerPoint;
	}
    
    
}
