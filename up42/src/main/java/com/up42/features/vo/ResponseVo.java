package com.up42.features.vo;

public class ResponseVo {

	private String id;
	private Object timeStamp;
	private Object endViewingDate;
	private Object beginViewingDate;
	private String missionName;
	
	public String getId() {
		return id;
	}
	public Object getTimeStamp() {
		return timeStamp;
	}
	public Object getEndViewingDate() {
		return endViewingDate;
	}
	public Object getBeginViewingDate() {
		return beginViewingDate;
	}
	public String getMissionName() {
		return missionName;
	}
	public void setId(String id) {
		this.id = id;
	}
	public void setTimeStamp(Object timeStamp) {
		this.timeStamp = timeStamp;
	}
	public void setEndViewingDate(Object endViewingDate) {
		this.endViewingDate = endViewingDate;
	}
	public void setBeginViewingDate(Object beginViewingDate) {
		this.beginViewingDate = beginViewingDate;
	}
	public void setMissionName(String missionName) {
		this.missionName = missionName;
	}
}
