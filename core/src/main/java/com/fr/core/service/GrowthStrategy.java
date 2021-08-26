package com.fr.core.service;

import java.net.URISyntaxException;
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
import com.fr.util.APIUtils;

@Service
public class GrowthStrategy implements MergeStrategy{
	
	public static final Logger logger = Logger.getLogger(GrowthStrategy.class);

	@Value("${webfocus.url}")
	private String webfocusUrl;
	

	@Autowired
	private APIGateway ag;
	
	@Autowired
	private CacheDataServiceImpl cds;
	
	@Override
	public ResponseVo getReportData(String filterParameter)  {
			Map<String,String> paramsMap =APIUtils.extractInputParams(filterParameter);
			
			String url = APIUtils.formatUrl(webfocusUrl,paramsMap,String.valueOf(106));

			String responeToParse = null;
			try {
				responeToParse = ag.restTemplateGet(url);
				if(responeToParse.contains("ERROR")) {
					return buildErrorResponseVo(responeToParse);
				}
			}catch(RestClientException e) {
				return buildErrorResponseVo("E1020 : Growth RestClientException.");
			}catch(URISyntaxException e) {
				return buildErrorResponseVo("E1021 : Growth URISyntaxException.");
			}
			
			List<Map<String,String>> dataList =  APIUtils.convertToJsonMap(responeToParse,cds.getFieldsList(106L, 506L));
			
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
	

}
