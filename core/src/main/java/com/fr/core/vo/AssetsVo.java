package com.fr.core.vo;

import java.math.BigDecimal;

public class AssetsVo {

	private String id;
	private String name;
	private BigDecimal marketPrice;
	private BigDecimal accrual;
	private BigDecimal marketValue;
	private BigDecimal marketValueAllocationPct;
	private BigDecimal returnPct;
	
	@Override
  public boolean equals(Object o) {
      if (this == o) return true;
      if (o == null || getClass() != o.getClass()) return false;
      AssetsVo cls = (AssetsVo) o;
      return id.equalsIgnoreCase(cls.id) && name.equalsIgnoreCase(cls.name);
  }

  @Override
  public int hashCode() {
      return id.hashCode()+name.hashCode();
  }
	
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
