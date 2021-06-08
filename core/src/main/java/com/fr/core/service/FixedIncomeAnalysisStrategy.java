package com.fr.core.service;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.fr.core.vo.ReportFilter;
import com.fr.core.vo.ResponseVo;

@Service
public class FixedIncomeAnalysisStrategy implements MergeStrategy {

	public static final Logger logger = Logger.getLogger(FixedIncomeAnalysisStrategy.class);

	@Override
	public ResponseVo getReportData(ReportFilter filterParameter) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}
	
//	@Value("${webfocus.fianalysis.ficalc.url}")
//	private String webfocusFixedIncomeAnalysiscalcUrl;
//	
//	@Value("${FIANALYSIS.response}")
//	private String fiaResponse;
//	
//	@Value("${FIANALYSIS.MERGE.FICALC}")
//	private String fiAnalysisMergeFIcalc;
//	
//	@Autowired
//	private APIGateway ag;
//	  
//	@Override
//	public String getReportData(ReportFilter filterParameter) throws Exception {
//		// TODO Auto-generated method stub
//		
//		List<Map<String,String>> fiList =  convertToJsonMap(ag.restTemplateGet(webfocusFixedIncomeAnalysiscalcUrl),fiaResponse);
//		
//		return null;
//	}
//	
//	private List<Map<String,String>> convertToJsonMap(String respJsonString,String responseToParse) {
//		Gson gson = new Gson();
//		JsonObject jsonObject = gson.fromJson(respJsonString, JsonObject.class);
//		JsonElement jsonelement =  jsonObject.get("records");
//		List<Map<String,String>> jsonList = new ArrayList<Map<String,String>>();
//		if (jsonelement.isJsonArray()) {
//			JsonArray recordArr = jsonelement.getAsJsonArray();
//			for (JsonElement record : recordArr) {
//				JsonObject recordobj = record.getAsJsonObject();
//				String[] response = responseToParse.split("~~");
//				Map<String,String> jsonMap = new HashMap<String,String>();
//				for(String res : response) {
//					String val=recordobj.get(res).getAsString();
//					jsonMap.put(res, val);
//				}
//				jsonList.add(jsonMap);
//			}
//		}
//		return jsonList;
//	}
}
