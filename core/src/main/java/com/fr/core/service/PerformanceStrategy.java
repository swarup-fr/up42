package com.fr.core.service;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;

import com.fr.core.vo.AccountsVo;
import com.fr.core.vo.ResponseVo;
import com.fr.util.APIGateway;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

@Service
public class PerformanceStrategy implements MergeStrategy{
	
	public static final Logger logger = Logger.getLogger(PerformanceStrategy.class);

	@Value("${webfocus.url}")
	private String webfocusUrl;
	

	@Autowired
	private APIGateway ag;
	
	@Autowired
	private CacheDataServiceImpl cds;
	
	@Override
	public ResponseVo getReportData(String filterParameter)  {
			Map<String,String> paramsMap =extractInputParams(filterParameter);
			
			String url = formatUrl(webfocusUrl,paramsMap);

			String responeToParse = null;
			try {
				responeToParse = ag.restTemplateGet(url);
				if(responeToParse.contains("ERROR")) {
					return buildErrorResponseVo(responeToParse);
				}
			}catch(RestClientException e) {
				return buildErrorResponseVo("E1012 : SECTOR ALLOC RestClientException.");
			}catch(URISyntaxException e) {
				return buildErrorResponseVo("E1013 : SECTOR ALLOC URISyntaxException.");
			}
			
			List<Map<String,String>> dataList =  convertToJsonMap(responeToParse,cds.getFieldsList(101L, 529L));
			
			ResponseVo rv = new ResponseVo();
			rv.setErrors(null);
			rv.setStatus(200);
			rv.setRecords(dataList);
			return rv;
	}

	private ResponseVo buildErrorResponseVo(String responeToParse) {
		ResponseVo rv = new ResponseVo();
		rv.setErrors(responeToParse);
		rv.setStatus(500);
		rv.setRecords(null);
		return rv;
	}
	
	private AccountsVo merge(List<Map<String,String>> retCalcList) {
		
		AccountsVo avo = new AccountsVo();
		avo.setAccountNo(retCalcList.get(0).get("accounts.id"));
		avo.setAccountsToDate(retCalcList.get(0).get("accounts.toDate"));
		avo.setAccountsFromDate(retCalcList.get(0).get("periods.fromDate"));
		avo.setAccountsFromDateAdjusted(retCalcList.get(0).get("accounts.SD_ADJ"));
				
//		ObjectMapper om=new ObjectMapper();	
//		String json=null;
//		try {
//			json = om.writeValueAsString(avo);
//		} catch (JsonProcessingException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		System.out.println("ASPP json: " + json);
//		logger.info(json);
		return avo;
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
	
	private Map<String,String> extractInputParams(String filterParameter){
		Gson gson = new Gson();
		JsonObject jsonObject = gson.fromJson(filterParameter, JsonObject.class);

		Map<String,String> pamasMap = new LinkedHashMap<String, String>();
		for(String p :jsonObject.keySet()) {
			pamasMap.put(p,jsonObject.get(p).getAsString());
		}
		return pamasMap;
	}
	
//	private Map<String, List<Map<String, String>>>  getGroupList(List<Map<String, String>> retCalcList,
//			String grpBy1,String grpBy2,String grpBy3,String grpBy4) {
//		Map<String,List<Map<String,String>>> mapGrp = new LinkedHashMap<String, List<Map<String,String>>>();
//		for(Map<String,String> rm :retCalcList) {
//			String gp4 = rm.get(grpBy4).substring(0,rm.get(grpBy4).indexOf("."));
//			if(mapGrp.containsKey(rm.get(grpBy1)+"~~"+rm.get(grpBy2)+"~~"+rm.get(grpBy3)+"~~"+gp4)) {
//				List<Map<String,String>> rcl = mapGrp.get(rm.get(grpBy1)+"~~"+rm.get(grpBy2)+"~~"+rm.get(grpBy3)+"~~"+gp4);
//				rcl.add(rm);
//				mapGrp.put(rm.get(rm.get(grpBy1)+"~~"+rm.get(grpBy2)+"~~"+rm.get(grpBy3)+"~~"+gp4), rcl);
//			}else {
//				List<Map<String,String>> rcl = new ArrayList<Map<String,String>>();
//				rcl.add(rm);
//				mapGrp.put(rm.get(grpBy1)+"~~"+rm.get(grpBy2)+"~~"+rm.get(grpBy3)+"~~"+gp4, rcl);
//			}
//		}
//		return mapGrp;
//	}
	
	private List<Map<String,String>> convertToJsonMap(String respJsonString,String responseToParse) {
		Gson gson = new Gson();
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
					String[] fields = res.split("::");
					String val=null!=recordobj && (null!=recordobj.get(fields[0]) && (!recordobj.get(fields[0]).isJsonNull())) ? recordobj.get(fields[0]).getAsString():"";
					jsonMap.put(fields[1], val);
				}
				jsonList.add(jsonMap);
			}
		}
		return jsonList;
	}
}
