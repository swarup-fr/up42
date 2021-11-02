package com.up42.features.vo;

public class Archive {
	private boolean offLine;
	private int size;
    private boolean onLine;
	public boolean isOffLine() {
		return offLine;
	}
	public void setOffLine(boolean offLine) {
		this.offLine = offLine;
	}
	public int getSize() {
		return size;
	}
	public void setSize(int size) {
		this.size = size;
	}
	public boolean isOnLine() {
		return onLine;
	}
	public void setOnLine(boolean onLine) {
		this.onLine = onLine;
	}
    
}
