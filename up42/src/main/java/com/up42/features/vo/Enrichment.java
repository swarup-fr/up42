package com.up42.features.vo;

import java.util.List;

public class Enrichment {

	private Naturallanguage naturallanguage;
    private List<Geoname> geonames;
	public Naturallanguage getNaturallanguage() {
		return naturallanguage;
	}
	public void setNaturallanguage(Naturallanguage naturallanguage) {
		this.naturallanguage = naturallanguage;
	}
	public List<Geoname> getGeonames() {
		return geonames;
	}
	public void setGeonames(List<Geoname> geonames) {
		this.geonames = geonames;
	}
    
}
