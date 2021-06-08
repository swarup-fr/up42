package com.fr.core.vo;

import java.util.List;

public class AccountsVo implements Data{

	private String accountNo;
	private String accountsToDate;
	private String accountsFromDate;
	private String accountsFromDateAdjusted;
	private List<PeriodsVo> periods;
	
//	@Override
//    public boolean equals(Object o) {
//        if (this == o) return true;
//        if (o == null || getClass() != o.getClass()) return false;
//        AccountsVo cls = (AccountsVo) o;
//        return accountNo.equalsIgnoreCase(cls.accountNo) && accountsToDate.equalsIgnoreCase(cls.accountsToDate)
//        		&& accountsFromDate.equalsIgnoreCase(cls.accountsFromDate) && accountsFromDateAdjusted.equalsIgnoreCase(cls.accountsFromDateAdjusted);
//    }
//
//    @Override
//    public int hashCode() {
//        return accountNo.hashCode()+accountsToDate.hashCode()+accountsFromDate.hashCode()+accountsFromDateAdjusted.hashCode();
//    }
	
	public List<PeriodsVo> getPeriods() {
		return periods;
	}
	public void setPeriods(List<PeriodsVo> periods) {
		this.periods = periods;
	}
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
	public String getAccountsFromDate() {
		return accountsFromDate;
	}
	public void setAccountsFromDate(String accountsFromDate) {
		this.accountsFromDate = accountsFromDate;
	}
	public String getAccountsFromDateAdjusted() {
		return accountsFromDateAdjusted;
	}
	public void setAccountsFromDateAdjusted(String accountsFromDateAdjusted) {
		this.accountsFromDateAdjusted = accountsFromDateAdjusted;
	}
	

}
