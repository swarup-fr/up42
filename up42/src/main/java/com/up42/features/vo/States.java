package com.up42.features.vo;

import java.util.List;

public class States {
	private String name;
	private List<County> counties;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public List<County> getCounties() {
		return counties;
	}
	public void setCounties(List<County> counties) {
		this.counties = counties;
	}
	
}
