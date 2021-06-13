package com.fr.core.service;

import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.fr.core.vo.AccountsVo;
import com.fr.core.vo.AssetsVo;
import com.fr.core.vo.ClassesVo;
import com.fr.core.vo.PeriodsVo;
import com.fr.core.vo.ReportFilter;
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

	@Value("${webfocus.aspp.hidsum.url}")
	private String webfocusAsppHidsumUrl;
	
	@Value("${webfocus.aspp.retcalc.url}")
	private String webfocusAsppRetcalcUrl;
	
	@Value("${HIDSUM.response}")
	private String hidSumResponse;
	
	@Value("${RETCALC.response}")
	private String retCalcResponse;
	
	@Value("${inputParam.mapping}")
	private String inputParamMapping;

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
	public ResponseVo getReportData(ReportFilter filterParameter) throws Exception {
		try {
			String url = formatUrl(webfocusAsppHidsumUrl,filterParameter);
			List<Map<String,String>> hidSumList =  convertToJsonMap(ag.restTemplateGet(url),hidSumResponse);
			Set<String> sector = new HashSet<String>();
			for(Map<String,String> m : hidSumList ) {
//				if(!"02".equalsIgnoreCase(m.get("segments.id")))
				sector.add(m.get("segments.id"));
			}
			String urlRet = formatUrl(webfocusAsppRetcalcUrl,filterParameter);
			urlRet = urlRet.replace("<SEGMENTS>", String.join(",", sector)+","+filterParameter.getClasses());
			List<Map<String,String>> retCalcList =  convertToJsonMap(ag.restTemplateGet(urlRet),retCalcResponse);
			
			AccountsVo avo = merge(hidSumList, retCalcList);
			ResponseVo rv = new ResponseVo();
			rv.setErrors(null);
			rv.setStatus(200);
			rv.setRecords(avo);
			return rv;
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	private AccountsVo merge(List<Map<String,String>> hidSumList,List<Map<String,String>> retCalcList) {
		
		AccountsVo avo = new AccountsVo();
		avo.setAccountNo(retCalcList.get(0).get("accounts.id"));
		avo.setAccountsToDate(retCalcList.get(0).get("accounts.toDate"));
		avo.setAccountsFromDate(retCalcList.get(0).get("periods.fromDate"));
		avo.setAccountsFromDateAdjusted(retCalcList.get(0).get("accounts.fromDateAdjusted"));
		
		List<PeriodsVo> pl = new ArrayList<PeriodsVo>();
		for(Map<String,String> rc : retCalcList) {
			PeriodsVo pvo = new PeriodsVo();
			pvo.setPeriod(rc.get("periods.period"));
			pvo.setPeriodMonths(Integer.parseInt(rc.get("periods.months").substring(0,rc.get("periods.months").indexOf("."))));
			pvo.setPeriodsIndex(rc.get("periods.index"));
			if(!pl.contains(pvo))
				pl.add(pvo);
		}
		
		for(PeriodsVo pv :pl ) {
			List<ClassesVo> cl = new ArrayList<ClassesVo>();
			for(Map<String,String> rc : retCalcList) {
				PeriodsVo pvo = new PeriodsVo();
				pvo.setPeriod(rc.get("periods.period"));
				pvo.setPeriodMonths(Integer.parseInt(rc.get("periods.months").substring(0,rc.get("periods.months").indexOf("."))));
				pvo.setPeriodsIndex(rc.get("periods.index"));
				if(pv.equals(pvo) && rc.get("segments.id").equalsIgnoreCase(rc.get("classes.id"))) {
					ClassesVo cvo = new ClassesVo();
					cvo.setId(rc.get("classes.id"));
					cvo.setName(rc.get("segments.name"));
//					cvo.setMarketPrice(new BigDecimal(rc.get("periods.marketPrice")));
					cvo.setAccrual(new BigDecimal(rc.get("periods.accrual")));
					cvo.setMarketValue(new BigDecimal(rc.get("periods.market")));
					cvo.setMarketValueAllocationPct(new BigDecimal(rc.get("periods.allocationPct")));
					cvo.setReturnPct(new BigDecimal(rc.get("periods.returnPct")));
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
					pvo.setPeriodMonths(Integer.parseInt(rc.get("periods.months").substring(0,rc.get("periods.months").indexOf("."))));
					pvo.setPeriodsIndex(rc.get("periods.index"));
					if(pv.equals(pvo) 
							&& rc.get("classes.id").equalsIgnoreCase(cv1.getId()) 
							&& !rc.get("segments.id").equalsIgnoreCase(rc.get("classes.id"))) {
						SegmentVo svo = new SegmentVo();
						svo.setId(rc.get("segments.id"));
						svo.setName(rc.get("segments.name"));
//						svo.setMarketPrice(new BigDecimal(rc.get("periods.marketPrice")));
						svo.setAccrual(new BigDecimal(rc.get("periods.accrual")));
						svo.setMarketValue(new BigDecimal(rc.get("periods.market")));
						svo.setMarketValueAllocationPct(new BigDecimal(rc.get("periods.returnPct")));
						svo.setReturnPct(new BigDecimal(rc.get("periods.returnPct")));
						sl.add(svo);
					}
				}
				cv1.setSegments(sl);
			}
		}
		Map<String,List<Map<String,String>>> hidGrp = getGroupList(hidSumList, "segments.id","classes.id","periods.stp","periods.months");
		
		for(PeriodsVo pv :pl ) {
			List<ClassesVo> cl = pv.getClasses();
			for(ClassesVo cv : cl) {
				List<SegmentVo> sl = cv.getSegments();
				for(SegmentVo sv : sl) {
					List<AssetsVo> al = new ArrayList<AssetsVo>();
					List<Map<String,String>> assetList = hidGrp.get(sv.getId()+"~~"+cv.getId()+"~~"+pv.getPeriod()+"~~"+pv.getPeriodMonths());
					for(Map<String,String> am : assetList) {
						AssetsVo asvo = new AssetsVo();
						asvo.setId(am.get("assets.hid_print"));
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
	
	private String formatUrl(String url,ReportFilter filterParameter) {
		String[] params = inputParamMapping.split("~~");
		for(String p :params) {
			String[] pv = p.split(":");
			Class<?> classObj = filterParameter.getClass();
			  
			try {
				Method pvm = classObj.getDeclaredMethod(pv[1]);
	            url = url.replace(pv[0],String.valueOf(pvm.invoke(filterParameter)));
	        }catch(Exception e) {
	        	e.printStackTrace();
	        }
		}
//		System.out.println(url);
		return url;
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
	
	private Map<String, List<Map<String, String>>>  getGroupList(List<Map<String, String>> retCalcList,String grpBy) {
		Map<String,List<Map<String,String>>> mapGrp = new LinkedHashMap<String, List<Map<String,String>>>();
		for(Map<String,String> rm :retCalcList) {
			if(mapGrp.containsKey(rm.get(grpBy))) {
				List<Map<String,String>> rcl = mapGrp.get(rm.get(grpBy));
				rcl.add(rm);
				mapGrp.put(rm.get(grpBy), rcl);
			}else {
				List<Map<String,String>> rcl = new ArrayList<Map<String,String>>();
				rcl.add(rm);
				mapGrp.put(rm.get(grpBy), rcl);
			}
		}
		return mapGrp;
	}
	
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
					String val=null!=recordobj.get(res)?recordobj.get(res).getAsString():"";
					jsonMap.put(res, val);
				}
				jsonList.add(jsonMap);
			}
		}
		return jsonList;
	}
}
