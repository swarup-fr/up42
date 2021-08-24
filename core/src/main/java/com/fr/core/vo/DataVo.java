package com.fr.core.vo;

import java.util.List;
import java.util.Map;

public class DataVo implements Data{

	private List<Map<String,String>> dataList;

	public List<Map<String, String>> getDataList() {
		return dataList;
	}

	public void setDataList(List<Map<String, String>> dataList) {
		this.dataList = dataList;
	}
	
}
