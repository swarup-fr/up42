package com.up42.features.vo;

public class Services {

	private boolean wmts;
	private String download;
	private boolean wcs;
	private boolean wms;
	public boolean isWmts() {
		return wmts;
	}
	public void setWmts(boolean wmts) {
		this.wmts = wmts;
	}
	public String getDownload() {
		return download;
	}
	public void setDownload(String download) {
		this.download = download;
	}
	public boolean isWcs() {
		return wcs;
	}
	public void setWcs(boolean wcs) {
		this.wcs = wcs;
	}
	public boolean isWms() {
		return wms;
	}
	public void setWms(boolean wms) {
		this.wms = wms;
	}
	
	
}
