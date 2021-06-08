package com.fr.core.model;

import java.util.LinkedHashMap;

public class BusinessEntity {
	private String id;
	private String name;
	private String ticker;
	private String cusip;
	private Double units;
	private Double unitPrice;
	private Double unitCost;
	private Double marketValue;
	private Double marketPrice;
	private Double accrual;
	private Double marketValueAllocationPct;
	private Double marketPriceAllocationPct;
	private Double costBasis;
	private Double gainLossUnrealized;
	private Double annualIncome;
	private Double yieldToMaturityPct;
	private Double currentYieldPct;
	private LinkedHashMap<String, Double> periodRtnMap;
	private String style;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getTicker() {
		return ticker;
	}
	public void setTicker(String ticker) {
		this.ticker = ticker;
	}
	public String getCusip() {
		return cusip;
	}
	public void setCusip(String cusip) {
		this.cusip = cusip;
	}
	public Double getUnits() {
		return units;
	}
	public void setUnits(Double units) {
		this.units = units;
	}
	public Double getUnitPrice() {
		return unitPrice;
	}
	public void setUnitPrice(Double unitPrice) {
		this.unitPrice = unitPrice;
	}
	public Double getUnitCost() {
		return unitCost;
	}
	public void setUnitCost(Double unitCost) {
		this.unitCost = unitCost;
	}
	public Double getMarketValue() {
		return marketValue;
	}
	public void setMarketValue(Double marketValue) {
		this.marketValue = marketValue;
	}
	public Double getMarketPrice() {
		return marketPrice;
	}
	public void setMarketPrice(Double marketPrice) {
		this.marketPrice = marketPrice;
	}
	public Double getAccrual() {
		return accrual;
	}
	public void setAccrual(Double accrual) {
		this.accrual = accrual;
	}
	public Double getMarketValueAllocationPct() {
		return marketValueAllocationPct;
	}
	public void setMarketValueAllocationPct(Double marketValueAllocationPct) {
		this.marketValueAllocationPct = marketValueAllocationPct;
	}
	public Double getMarketPriceAllocationPct() {
		return marketPriceAllocationPct;
	}
	public void setMarketPriceAllocationPct(Double marketPriceAllocationPct) {
		this.marketPriceAllocationPct = marketPriceAllocationPct;
	}
	public Double getCostBasis() {
		return costBasis;
	}
	public void setCostBasis(Double costBasis) {
		this.costBasis = costBasis;
	}
	public Double getGainLossUnrealized() {
		return gainLossUnrealized;
	}
	public void setGainLossUnrealized(Double gainLossUnrealized) {
		this.gainLossUnrealized = gainLossUnrealized;
	}
	public Double getAnnualIncome() {
		return annualIncome;
	}
	public void setAnnualIncome(Double annualIncome) {
		this.annualIncome = annualIncome;
	}
	public Double getYieldToMaturityPct() {
		return yieldToMaturityPct;
	}
	public void setYieldToMaturityPct(Double yieldToMaturityPct) {
		this.yieldToMaturityPct = yieldToMaturityPct;
	}
	public Double getCurrentYieldPct() {
		return currentYieldPct;
	}
	public void setCurrentYieldPct(Double currentYieldPct) {
		this.currentYieldPct = currentYieldPct;
	}
	public LinkedHashMap<String, Double> getPeriodRtnMap() {
		return periodRtnMap;
	}
	public void setPeriodRtnMap(LinkedHashMap<String, Double> periodRtnMap) {
		this.periodRtnMap = periodRtnMap;
	}
	public String getStyle() {
		return style;
	}
	public void setStyle(String style) {
		this.style = style;
	}
	
}
