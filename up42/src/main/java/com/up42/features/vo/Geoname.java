package com.up42.features.vo;

import java.util.List;

public class Geoname {
	private String name;
    private List<States> states;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public List<States> getStates() {
		return states;
	}
	public void setStates(List<States> states) {
		this.states = states;
	}
    
}
