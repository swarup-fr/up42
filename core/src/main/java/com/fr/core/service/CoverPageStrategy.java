package com.fr.core.service;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;

import com.fr.core.vo.CoverPageVo;
import com.fr.core.vo.DemoGraphicVo;
import com.fr.core.vo.ResponseVo;
import com.fr.util.APIGateway;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

@Service
public class CoverPageStrategy implements MergeStrategy {

	public static final Logger logger = Logger.getLogger(CoverPageStrategy.class); 
	
	@Value("${webfocus.coverpage.url}")
	private String webfocusCoverPageUrl;
//	
//	@Value("${inputParam.mapping}")
//	private String inputParamMapping;
	
	@Value("${COVERPAGE.response}")
	private String coverPageResponse;
	
	@Autowired
	private APIGateway ag;

	@Override
	public ResponseVo getReportData(String filterParameter) throws Exception {
		// TODO Auto-generated method stub
		Map<String,String> paramsMap =extractInputParams(filterParameter);
		String url = formatUrl(webfocusCoverPageUrl,paramsMap);
		String responeToParse = null;
		try {
			responeToParse = ag.restTemplateGet(url);
			if(responeToParse.contains("ERROR")) {
				return buildErrorResponseVo(responeToParse);
			}
		}catch(RestClientException e) {
			return buildErrorResponseVo("E1010 : SECTOR ALLOC RestClientException.");
		}catch(URISyntaxException e) {
			return buildErrorResponseVo("E1011 : SECTOR ALLOC URISyntaxException.");
		}
		
		List<Map<String,String>> fieldList =  convertToJsonMap(responeToParse,coverPageResponse);
		String fieldsVal=paramsMap.get("demographics");//filterParameter.getDemographics();
		CoverPageVo avo = getCoverPageRequiredFields(fieldList, fieldsVal);
		ResponseVo rv = new ResponseVo();
		rv.setErrors(null);
		rv.setStatus(200);
		rv.setRecords(avo);
		return rv;
	}
	
//	@Override
//	public ResponseVo getReportData(ReportFilter filterParameter) throws Exception {
//		try {
//			String url = formatUrl(webfocusCoverPageUrl,filterParameter);
//			List<Map<String,String>> fieldList =  convertToJsonMap(ag.restTemplateGet(url),coverPageResponse);
//			String fieldsVal=filterParameter.getDemographics();
//			CoverPageVo avo = getCoverPageRequiredFields(fieldList, fieldsVal);
//			ResponseVo rv = new ResponseVo();
//			rv.setErrors(null);
//			rv.setStatus(200);
//			rv.setRecords(avo);
//			return rv;
//		}catch(Exception e) {
//			e.printStackTrace();
//		}
//		
//		return null;
//	}

	private CoverPageVo getCoverPageRequiredFields(List<Map<String, String>> fieldList, String fieldsVal) {
		// TODO Auto-generated method stub
		CoverPageVo coverObj=new CoverPageVo();
		List<DemoGraphicVo> demographicArray=new ArrayList<DemoGraphicVo>();
		DemoGraphicVo demograhicRow=new DemoGraphicVo();
		if(fieldsVal !="" && fieldsVal!=null) {
			Map<String, String> firstMap=fieldList.get(0);
			coverObj.setAccountNo(firstMap.get("accounts.id"));
			coverObj.setAccountsToDate(firstMap.get("accounts.toDate"));
			String[] fields=fieldsVal.split(",");
			List<String> lstFields = Arrays.asList(fields);  
			for (Map<String, String> map : fieldList) {
				demograhicRow=new DemoGraphicVo();
				String field=map.get("demographics.field");
				if(lstFields.contains(field)) {
					demograhicRow.setField(map.get("demographics.field"));
					demograhicRow.setFieldDescription(map.get("demographics.fieldDescription"));
					demograhicRow.setFieldValue(map.get("demographics.value"));
					demograhicRow.setFieldValueDescription(map.get("demographics.valueDesc"));
					demographicArray.add(demograhicRow);
				}
			}
			coverObj.setDemoGraphics(demographicArray);
		}
		return coverObj;
	}

//	private String formatUrl(String url,ReportFilter filterParameter) {
//		String[] params = inputParamMapping.split("~~");
//		for(String p :params) {
//			String[] pv = p.split(":");
//			Class<?> classObj = filterParameter.getClass();
//			  
//			try {
//				Method pvm = classObj.getDeclaredMethod(pv[1]);
//	            url = url.replace(pv[0],String.valueOf(pvm.invoke(filterParameter)));
//	        }catch(Exception e) {
//	        	e.printStackTrace();
//	        }
//		}
////		System.out.println(url);
//		return url;
//	}
	
	private List<Map<String,String>> convertToJsonMap(String respJsonString,String responseToParse) {
		Gson gson = new Gson();
//		System.out.println(respJsonString);
		JsonObject jsonObject = gson.fromJson(respJsonString, JsonObject.class);
		JsonElement jsonelement =  jsonObject.get("records");
		List<Map<String,String>> jsonList = new ArrayList<Map<String,String>>();
		if (jsonelement.isJsonArray()) {
			JsonArray recordArr = jsonelement.getAsJsonArray();
			for (JsonElement record : recordArr) {
				JsonObject recordobj = record.getAsJsonObject();
				String[] response = responseToParse.split("~~");
				Map<String,String> jsonMap = new HashMap<String,String>();
				for(String res : response) {
					if(recordobj.get(res) != null) {
						if(!recordobj.get(res).isJsonNull()) {
							String val=recordobj.get(res).getAsString();
							jsonMap.put(res, val);
						}else
							jsonMap.put(res, "");
					}else 
						jsonMap.put(res, "");
				}
				jsonList.add(jsonMap);
			}
		}
		return jsonList;
	}

	private Map<String,String> extractInputParams(String filterParameter){
		Gson gson = new Gson();
		JsonObject jsonObject = gson.fromJson(filterParameter, JsonObject.class);

		Map<String,String> pamasMap = new LinkedHashMap<String, String>();
		for(String p :jsonObject.keySet()) {
			pamasMap.put(p,jsonObject.get(p).getAsString());
		}
		return pamasMap;
	}
	
	
	private String formatUrl(String url,Map<String,String> paramsMap) {
		StringBuilder urlParams = new StringBuilder();
		for(String p :paramsMap.keySet()) {
			urlParams.append("&").append(p).append("=").append(paramsMap.get(p));
		}
		url = url+urlParams.toString();
		System.out.println(url);
		return url;
	}
	
	private ResponseVo buildErrorResponseVo(String responeToParse) {
		ResponseVo rv = new ResponseVo();
		rv.setErrors(responeToParse);
		rv.setStatus(500);
		rv.setRecords(null);
		return rv;
	}
}
