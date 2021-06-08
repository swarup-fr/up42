package com.fr.core.vo;

import io.swagger.annotations.ApiModelProperty;

public class ResponseVo {

	@ApiModelProperty(notes = "API response status")
	private int status;
	
	@ApiModelProperty(notes = "API response errors")
	private String errors;
	
	@ApiModelProperty(notes = "API response data")
	private Data records;
	
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getErrors() {
		return errors;
	}
	public void setErrors(String errors) {
		this.errors = errors;
	}
	public Data getRecords() {
		return records;
	}
	public void setRecords(Data records) {
		this.records = records;
	}
	
	
}
