package com.fr.core.vo;

import java.math.BigDecimal;

public class EntityVo {

	private String id;
	private String name;
	private BigDecimal marketPrice;
	private BigDecimal accrual;
	private BigDecimal marketValue;
	private BigDecimal marketPriceAllocationPct;
	private BigDecimal marketValueAllocationPct;
	private BigDecimal returnPct;
	
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
	public BigDecimal getMarketPrice() {
		return marketPrice;
	}
	public void setMarketPrice(BigDecimal marketPrice) {
		this.marketPrice = marketPrice;
	}
	public BigDecimal getAccrual() {
		return accrual;
	}
	public void setAccrual(BigDecimal accrual) {
		this.accrual = accrual;
	}
	public BigDecimal getMarketValue() {
		return marketValue;
	}
	public void setMarketValue(BigDecimal marketValue) {
		this.marketValue = marketValue;
	}
	public BigDecimal getMarketPriceAllocationPct() {
		return marketPriceAllocationPct;
	}
	public void setMarketPriceAllocationPct(BigDecimal marketPriceAllocationPct) {
		this.marketPriceAllocationPct = marketPriceAllocationPct;
	}
	public BigDecimal getMarketValueAllocationPct() {
		return marketValueAllocationPct;
	}
	public void setMarketValueAllocationPct(BigDecimal marketValueAllocationPct) {
		this.marketValueAllocationPct = marketValueAllocationPct;
	}
	public BigDecimal getReturnPct() {
		return returnPct;
	}
	public void setReturnPct(BigDecimal returnPct) {
		this.returnPct = returnPct;
	}
	
}
