package com.fr.core.vo;

import io.swagger.annotations.ApiModelProperty;

public class ReportFilter {

	@ApiModelProperty(notes = "Account number.")
	private String accountNumber ;
	private String toDate;
	private String periods;
	private String sectors;
	private String sectorSchema;
	private String classes;
	private String excludeUninvestedCash="N";
	private String excludeInterperiodAssets="N";
	private String demograhics;
	
	public String isExcludeInterperiodAssets() {
		return excludeInterperiodAssets;
	}
	public void setExcludeInterperiodAssets(String excludeInterperiodAssets) {
		this.excludeInterperiodAssets = excludeInterperiodAssets;
	}
	
	public String getAccountNumber() {
		return accountNumber;
	}
	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}
	public String getToDate() {
		return toDate;
	}
	public void setToDate(String toDate) {
		this.toDate = toDate;
	}
	public String getPeriods() {
		return periods;
	}
	public void setPeriods(String periods) {
		this.periods = periods;
	}
	public String getSectors() {
		return sectors;
	}
	public void setSectors(String sectors) {
		this.sectors = sectors;
	}
	public String getSectorSchema() {
		return sectorSchema;
	}
	public void setSectorSchema(String sectorSchema) {
		this.sectorSchema = sectorSchema;
	}
	public String getClasses() {
		return classes;
	}
	public void setClasses(String classes) {
		this.classes = classes;
	}
	public String isExcludeUninvestedCash() {
		return excludeUninvestedCash;
	}
	public void setExcludeUninvestedCash(String excludeUninvestedCash) {
		this.excludeUninvestedCash = excludeUninvestedCash;
	}
	public String getDemograhics() {
		return demograhics;
	}
	public void setDemograhics(String demograhics) {
		this.demograhics = demograhics;
	}
	
}
