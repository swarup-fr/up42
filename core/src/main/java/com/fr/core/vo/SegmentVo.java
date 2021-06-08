package com.fr.core.vo;

import java.math.BigDecimal;
import java.util.List;

public class SegmentVo {

	private String id;
	private String name;
	private BigDecimal marketPrice;
	private BigDecimal accrual;
	private BigDecimal marketValue;
	private BigDecimal marketValueAllocationPct;
	private BigDecimal returnPct;
	private List<AssetsVo> assets;
	
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
	
	public List<AssetsVo> getAssets() {
		return assets;
	}
	public void setAssets(List<AssetsVo> assets) {
		this.assets = assets;
	}
	

	@Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SegmentVo seg = (SegmentVo) o;
        return id.equalsIgnoreCase(seg.id) && name.equalsIgnoreCase(seg.name) ;
    }

    @Override
    public int hashCode() {
        return id.hashCode()+name.hashCode();
    }
}
