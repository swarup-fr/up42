package com.fr.core.service;

import com.fr.core.vo.ReportFilter;
import com.fr.core.vo.ResponseVo;

public interface MergeStrategy {

	public ResponseVo getReportData(ReportFilter filterParameter) throws Exception;
}
