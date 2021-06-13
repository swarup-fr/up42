package com.fr.core.vo;

import java.util.List;

public class CoverPageVo implements Data{

	private String accountNo;
	private String accountsToDate;
	private List<DemoGraphicVo> demoGraphics;
	
	public String getAccountNo() {
		return accountNo;
	}
	public void setAccountNo(String accountNo) {
		this.accountNo = accountNo;
	}
	public String getAccountsToDate() {
		return accountsToDate;
	}
	public void setAccountsToDate(String accountsToDate) {
		this.accountsToDate = accountsToDate;
	}
	public List<DemoGraphicVo> getDemoGraphics() {
		return demoGraphics;
	}
	public void setDemoGraphics(List<DemoGraphicVo> demoGraphics) {
		this.demoGraphics = demoGraphics;
	}
	
}
