package com.fr.core.service;

import org.springframework.stereotype.Component;

import com.fr.core.vo.ReportFilter;
import com.fr.core.vo.ResponseVo;

@Component
public class MergeContext {

	private MergeStrategy ms;
	
	public void setMergeContext(MergeStrategy ms) {
		this.ms = ms;
	}
	
	public ResponseVo executeMergeStrategy(String filterParameter) throws Exception {
		return ms.getReportData(filterParameter);
	}
}
