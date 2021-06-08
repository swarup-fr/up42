package com.fr.core.model;

import java.util.List;

public class DataModel {
	private String id;
	private String key;
	private String value;
	private String header;
	private String classVal;
	private String anchorTag;
	private String inputValue;
	private List<DataModel> data;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public String getHeader() {
		return header;
	}
	public void setHeader(String header) {
		this.header = header;
	}
	public String getClassVal() {
		return classVal;
	}
	public void setClassVal(String classVal) {
		this.classVal = classVal;
	}
	public String getAnchorTag() {
		return anchorTag;
	}
	public void setAnchorTag(String anchorTag) {
		this.anchorTag = anchorTag;
	}
	public String getInputValue() {
		return inputValue;
	}
	public void setInputValue(String inputValue) {
		this.inputValue = inputValue;
	}
	public List<DataModel> getData() {
		return data;
	}
	public void setData(List<DataModel> data) {
		this.data = data;
	}
}
