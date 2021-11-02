package com.up42.features.vo;

import java.util.List;

public class Feature {
	private String type;
	private List<Double> bbox;
	private Geometry geometry;
	private Properties properties;
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public List<Double> getBbox() {
		return bbox;
	}
	public void setBbox(List<Double> bbox) {
		this.bbox = bbox;
	}
	public Geometry getGeometry() {
		return geometry;
	}
	public void setGeometry(Geometry geometry) {
		this.geometry = geometry;
	}
	public Properties getProperties() {
		return properties;
	}
	public void setProperties(Properties properties) {
		this.properties = properties;
	}
	
	
}
