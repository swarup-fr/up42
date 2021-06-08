package com.fr.core.vo;

import java.util.List;

public class PeriodsVo {

	private String periodsIndex;
	private String period;
	private Integer periodMonths;
	private List<ClassesVo> classes;
	
	public List<ClassesVo> getClasses() {
		return classes;
	}
	public void setClasses(List<ClassesVo> classes) {
		this.classes = classes;
	}
	public String getPeriodsIndex() {
		return periodsIndex;
	}
	public void setPeriodsIndex(String periodsIndex) {
		this.periodsIndex = periodsIndex;
	}
	public String getPeriod() {
		return period;
	}
	public void setPeriod(String period) {
		this.period = period;
	}
	public Integer getPeriodMonths() {
		return periodMonths;
	}
	public void setPeriodMonths(Integer periodMonths) {
		this.periodMonths = periodMonths;
	}

	@Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PeriodsVo cls = (PeriodsVo) o;
        return periodsIndex.equalsIgnoreCase(cls.periodsIndex) && period.equalsIgnoreCase(cls.period) && periodMonths==cls.periodMonths;
    }

    @Override
    public int hashCode() {
        return periodsIndex.hashCode()+period.hashCode()+periodMonths;
    }
}
