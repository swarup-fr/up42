package com.fr.core.service;

import java.math.BigDecimal;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections.ListUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;

import com.fr.core.vo.AccountsVo;
import com.fr.core.vo.AssetsVo;
import com.fr.core.vo.ClassesVo;
import com.fr.core.vo.PeriodsVo;
import com.fr.core.vo.ResponseVo;
import com.fr.core.vo.SegmentVo;
import com.fr.util.APIGateway;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

@Service
public class ASPPMergeStrategy implements MergeStrategy{
	
	public static final Logger logger = Logger.getLogger(ASPPMergeStrategy.class);

	@Value("${webfocus.aspp.twrr.hidsum.url}")
	private String webfocusAsppTwrrHidsumUrl;
	
	@Value("${webfocus.aspp.twrr.retcalc.url}")
	private String webfocusAsppTwrrRetcalcUrl;
	
	@Value("${webfocus.aspp.mwrr.hidsum.url}")
	private String webfocusAsppMwrrHidsumUrl;
	
	@Value("${webfocus.aspp.mwrr.retcalc.url}")
	private String webfocusAsppMwrrRetcalcUrl;
	
	@Value("${HIDSUM.response}")
	private String hidSumResponse;
	
	@Value("${RETCALC.response}")
	private String retCalcResponse;
	
	@Value("${finaljson.accounts}")
	private String fjAccounts;
	
	@Value("${finaljson.period}")
	private String fjPeriod;
	
	@Value("${finaljson.classes}")
	private String fjClasses;
	
	@Value("${finaljson.segments}")
	private String fjSegments;
	
	@Autowired
	private APIGateway ag;
	
	@Override
	public ResponseVo getReportData(String filterParameter)  {
			Map<String,String> paramsMap =extractInputParams(filterParameter);
			
			String url = formatUrl(webfocusAsppTwrrHidsumUrl,paramsMap);
			if(paramsMap.get("segmentExclude")!=null) {
				url = url.concat("&segmentExclude="+paramsMap.get("segmentExclude"));
			}
			String hidSumResponeToParse = null;
			try {
				hidSumResponeToParse = ag.restTemplateGet(url);
				if(hidSumResponeToParse.contains("ERROR")) {
					return buildErrorResponseVo(hidSumResponeToParse);
				}
			}catch(RestClientException e) {
				return buildErrorResponseVo("E1001 : ASPP HIDSUM RestClientException.");
			}catch(URISyntaxException e) {
				return buildErrorResponseVo("E1002 : ASPP HIDSUM URISyntaxException.");
			}
			
			
			List<Map<String,String>> hidSumList =  convertToJsonMap(hidSumResponeToParse,hidSumResponse,false);
			Set<String> sector = new HashSet<String>();
			for(Map<String,String> m : hidSumList ) {
				sector.add(m.get("segments.id"));
			}
			String urlRet = formatUrl(webfocusAsppTwrrRetcalcUrl,paramsMap);
			
			urlRet = urlRet.replace("<SEGMENTS>", String.join(",", sector)+","+paramsMap.get("classes"));
			System.out.println(urlRet);
			if(paramsMap.get("segmentExclude")!=null) {
				urlRet = urlRet.concat("&segmentExclude="+paramsMap.get("segmentExclude"));
			}
			String retCalcResponeToParse = null;
			try {
				retCalcResponeToParse = ag.restTemplateGet(urlRet);
			}catch(RestClientException e) {
				return buildErrorResponseVo("E1003 : ASPP RETCALC RestClientException.");
			}catch(URISyntaxException e) {
				return buildErrorResponseVo("E1004 : ASPP RETCALC URISyntaxException.");
			}
			List<Map<String,String>> retCalcList =  convertToJsonMap(retCalcResponeToParse,retCalcResponse,false);
			
			AccountsVo avo = null;
			
			////////////////////////////////////////////////////////////////////////////////////////////////
			if(paramsMap.get("segmentExclude")!=null) {
				String segmentToInclude = paramsMap.get("segmentExclude");
				paramsMap.remove("segmentExclude");
				paramsMap.remove("segmentUse");
				
				String mhurl = formatUrl(webfocusAsppMwrrHidsumUrl,paramsMap);
				String mhidSumResponeToParse = null;
				try {
					mhidSumResponeToParse = ag.restTemplateGet(mhurl);
					if(mhidSumResponeToParse.contains("ERROR")) {
						return buildErrorResponseVo(mhidSumResponeToParse);
					}
				}catch(RestClientException e) {
					return buildErrorResponseVo("E1005 : ASPP HIDSUM RestClientException.");
				}catch(URISyntaxException e) {
					return buildErrorResponseVo("E1006 : ASPP HIDSUM URISyntaxException.");
				}
				
				
				List<Map<String,String>> mhidSumList =  convertToJsonMap(hidSumResponeToParse,hidSumResponse,true);
				hidSumList.addAll(mhidSumList);
				
				String murlRet = formatUrl(webfocusAsppMwrrRetcalcUrl,paramsMap);
				
				murlRet = murlRet.replace("<SEGMENTS>",segmentToInclude );
				System.out.println(murlRet);
				String mretCalcResponeToParse = null;
				try {
					mretCalcResponeToParse = ag.restTemplateGet(murlRet);
				}catch(RestClientException e) {
					return buildErrorResponseVo("E1007 : ASPP RETCALC RestClientException.");
				}catch(URISyntaxException e) {
					return buildErrorResponseVo("E1008 : ASPP RETCALC URISyntaxException.");
				}
				List<Map<String,String>> mretCalcList =  convertToJsonMap(mretCalcResponeToParse,retCalcResponse,true);
				retCalcList.addAll(mretCalcList);
				
			}
			
			try {
				avo = merge(hidSumList, retCalcList);
			}catch(Exception e) {
				e.printStackTrace();
				return buildErrorResponseVo("E1009 : ASPP Error processing response.");
			}
			
			ResponseVo rv = new ResponseVo();
			rv.setErrors(null);
			rv.setStatus(200);
			rv.setRecords(avo);
			return rv;
	}

	private ResponseVo buildErrorResponseVo(String responeToParse) {
		ResponseVo rv = new ResponseVo();
		rv.setErrors(responeToParse);
		rv.setStatus(500);
		rv.setRecords(null);
		return rv;
	}
	
	private AccountsVo merge(List<Map<String,String>> hidSumList,List<Map<String,String>> retCalcList) {
		
		AccountsVo avo = new AccountsVo();
		avo.setAccountNo(retCalcList.get(0).get("accounts.id"));
		avo.setAccountsToDate(retCalcList.get(0).get("accounts.toDate"));
		avo.setAccountsFromDate(retCalcList.get(0).get("periods.fromDate"));
		avo.setAccountsFromDateAdjusted(retCalcList.get(0).get("accounts.SD_ADJ"));
		
		List<PeriodsVo> pl = new ArrayList<PeriodsVo>();
		for(Map<String,String> rc : retCalcList) {
			PeriodsVo pvo = new PeriodsVo();
			pvo.setPeriod(rc.get("periods.period"));
			if(rc.get("periods.months").contains("."))
				pvo.setPeriodMonths(Integer.parseInt(rc.get("periods.months").substring(0,rc.get("periods.months").indexOf("."))));
			else
				pvo.setPeriodMonths(Integer.parseInt(rc.get("periods.months")));
			pvo.setPeriodsIndex(rc.get("periods.index"));
			if(!pl.contains(pvo)) 
				pl.add(pvo);
		}
		
		for(PeriodsVo pv :pl ) {
			List<ClassesVo> cl = new ArrayList<ClassesVo>();
			for(Map<String,String> rc : retCalcList) {
				PeriodsVo pvo = new PeriodsVo();
				pvo.setPeriod(rc.get("periods.period"));
				if(rc.get("periods.months").contains("."))
					pvo.setPeriodMonths(Integer.parseInt(rc.get("periods.months").substring(0,rc.get("periods.months").indexOf("."))));
				else
					pvo.setPeriodMonths(Integer.parseInt(rc.get("periods.months")));
				pvo.setPeriodsIndex(rc.get("periods.index"));
				if(pv.equals(pvo) && rc.get("segments.id").equalsIgnoreCase(rc.get("classes.id"))) {
					ClassesVo cvo = new ClassesVo();
					cvo.setId(rc.get("classes.id"));
					cvo.setName(rc.get("classes.name"));
					System.out.println(rc.get("segments.market"));
					cvo.setMarketPrice(new BigDecimal(rc.get("segments.market")));
					cvo.setAccrual(new BigDecimal(rc.get("segments.accrual")));
					cvo.setMarketValue(new BigDecimal(rc.get("segments.market")));
					cvo.setMarketValueAllocationPct(new BigDecimal(rc.get("segments.allocationPct")));
					cvo.setReturnPct(new BigDecimal(rc.get("segments.returnPct")));
					cl.add(cvo);
				}
			}
			pv.setClasses(cl);
		}
		avo.setPeriods(pl);
			
		for(PeriodsVo pv :pl ) {
			List<ClassesVo> cl1 = pv.getClasses();
			for(ClassesVo cv1 : cl1) {
				
				List<SegmentVo> sl = new ArrayList<SegmentVo>();

				for(Map<String,String> rc : retCalcList) {
					PeriodsVo pvo = new PeriodsVo();
					pvo.setPeriod(rc.get("periods.period"));
					if(rc.get("periods.months").contains("."))
						pvo.setPeriodMonths(Integer.parseInt(rc.get("periods.months").substring(0,rc.get("periods.months").indexOf("."))));
					else
						pvo.setPeriodMonths(Integer.parseInt(rc.get("periods.months")));
					pvo.setPeriodsIndex(rc.get("periods.index"));
					if(pv.equals(pvo) 
							&& rc.get("classes.id").equalsIgnoreCase(cv1.getId()) 
							&& !rc.get("segments.id").equalsIgnoreCase(rc.get("classes.id"))) {
						SegmentVo svo = new SegmentVo();
						svo.setId(rc.get("segments.id"));
						svo.setName(rc.get("segments.name"));
						svo.setMarketPrice(new BigDecimal(rc.get("periods.marketPrice")));
						svo.setAccrual(new BigDecimal(rc.get("periods.accrual")));
						svo.setMarketValue(new BigDecimal(rc.get("periods.marketValue")));
						svo.setMarketValueAllocationPct(new BigDecimal(rc.get("periods.marketValuePct")));
						svo.setReturnPct(new BigDecimal(rc.get("periods.returnPct")));
						sl.add(svo);
					}
				}
				cv1.setSegments(sl);
			}
		}
		Map<String,List<Map<String,String>>> hidGrp = getGroupList(hidSumList, "segments.id","classes.id","periods.period","periods.months");
		
		for(PeriodsVo pv :pl ) {
			List<ClassesVo> cl = pv.getClasses();
			for(ClassesVo cv : cl) {
				List<SegmentVo> sl = cv.getSegments();
				for(SegmentVo sv : sl) {
					List<Map<String,String>> assetList = hidGrp.get(sv.getId()+"~~"+cv.getId()+"~~"+pv.getPeriod()+"~~"+pv.getPeriodMonths());
					if(null!=assetList && !assetList.isEmpty()) {
						List<AssetsVo> al = new ArrayList<AssetsVo>();
						for(Map<String,String> am : assetList) {
							AssetsVo asvo = new AssetsVo();
							asvo.setId(am.get("assets.id"));
							asvo.setName(am.get("assets.name"));
							asvo.setMarketPrice(new BigDecimal(0));
							asvo.setAccrual(new BigDecimal(am.get("assets.accrual")));
							asvo.setMarketValue(new BigDecimal(am.get("assets.marketValue")));
							asvo.setMarketValueAllocationPct(new BigDecimal(am.get("assets.marketValueAllocationPct")));
							asvo.setReturnPct(new BigDecimal(am.get("assets.returnPct")));
							if(!al.contains(asvo))
							al.add(asvo);
						}
						sv.setAssets(al);
					}
				}
			}
		}
		
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
	
	private Map<String, List<Map<String, String>>>  getGroupList(List<Map<String, String>> retCalcList,
			String grpBy1,String grpBy2,String grpBy3,String grpBy4) {
		Map<String,List<Map<String,String>>> mapGrp = new LinkedHashMap<String, List<Map<String,String>>>();
		for(Map<String,String> rm :retCalcList) {
			String gp4 = rm.get(grpBy4).substring(0,rm.get(grpBy4).indexOf("."));
			if(mapGrp.containsKey(rm.get(grpBy1)+"~~"+rm.get(grpBy2)+"~~"+rm.get(grpBy3)+"~~"+gp4)) {
				List<Map<String,String>> rcl = mapGrp.get(rm.get(grpBy1)+"~~"+rm.get(grpBy2)+"~~"+rm.get(grpBy3)+"~~"+gp4);
				rcl.add(rm);
				mapGrp.put(rm.get(rm.get(grpBy1)+"~~"+rm.get(grpBy2)+"~~"+rm.get(grpBy3)+"~~"+gp4), rcl);
			}else {
				List<Map<String,String>> rcl = new ArrayList<Map<String,String>>();
				rcl.add(rm);
				mapGrp.put(rm.get(grpBy1)+"~~"+rm.get(grpBy2)+"~~"+rm.get(grpBy3)+"~~"+gp4, rcl);
			}
		}
		return mapGrp;
	}
	
	private List<Map<String,String>> convertToJsonMap(String respJsonString,String responseToParse,boolean ismwrr) {
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
					String val=null!=recordobj && null!=recordobj.get(res) ? recordobj.get(res).getAsString():"";
					if(ismwrr && res.contains("name"))
						jsonMap.put(res, val+" *");
					else
						jsonMap.put(res, val);
				}
				jsonList.add(jsonMap);
			}
		}
		return jsonList;
	}
}
