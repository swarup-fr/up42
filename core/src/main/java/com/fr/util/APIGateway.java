package com.fr.util;

import java.net.URI;
import java.net.URISyntaxException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.fr.core.config.Scheduler;



@Component
public class APIGateway {
	
	@Autowired
	private RestTemplate restTemplate;

	 public String restTemplateGet(String url) throws RestClientException, URISyntaxException {
		 try {
			 
			HttpHeaders hh = new HttpHeaders();
			hh.setContentType(MediaType.APPLICATION_JSON);
			hh.add("Cookie",Scheduler.getSessionList().get("0") );

			org.springframework.http.HttpEntity<?> httpEntity = new org.springframework.http.HttpEntity(null, hh);

			ResponseEntity<String> resp = restTemplate.exchange(new URI(url), HttpMethod.GET, httpEntity, String.class);
			if (null != resp) {

				if (StringUtils.hasText(resp.getBody())) {
					String response = resp.getBody().substring(0, resp.getBody().indexOf("]}") + 2);
					return response;
				}
			}
			return "500~Error";
		 }catch(Exception e) {
			 e.printStackTrace();
			 return "500~Error";
		}
	 }
	 
	 public String getWFSessionId(String url, String user, String pwd) {
		 try {
			 
			HttpHeaders hh = new HttpHeaders();
			hh.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

			MultiValueMap<String, String> map= new LinkedMultiValueMap<String, String>();
			map.add("IBFS_comp_user", user);
			map.add("IBFS_comp_pass", pwd);
			map.add("IBIWF_credAutoPrompt", "true");
			map.add("IBFS_comp_name", "EDASERVE");
			
			HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<MultiValueMap<String, String>>(map, hh);

			ResponseEntity<String> response = restTemplate.postForEntity( url, request , String.class );
			String cookie = response.getHeaders().getFirst(HttpHeaders.SET_COOKIE);
			cookie = cookie.substring(0,cookie.indexOf(";"));
			return cookie;
		 }catch(Exception e) {
			 e.printStackTrace();
			 return "ERROR";
		}
	 }
	 
//	 public APIGateway() {
//		 restTemplate = new RestTemplate();
//	 }
//	 
//	 public static void main(String[] ss) throws Exception{
//		 APIGateway ag = new APIGateway();
//		 String c = ag.post("http://fr-dev-cr-001/ibi_apps/WFServlet.ibfs?IBIF_ex=FRAPI&api=101&IBIAPP_app=API&CLIENT=CR001&accounts=1022310057&asofDate=20121130&periods=1&segments=EQ,FX,ST,8A,TF&segmentUse=5&outputFormat=JSON");
//		 ag.get("http://fr-dev-cr-001/ibi_apps/WFServlet.ibfs?IBIF_ex=FRAPI&api=101&IBIAPP_app=API&CLIENT=CR001&accounts=1022310057&asofDate=20121130&periods=1&segments=EQ,FX,ST,8A,TF&segmentUse=5&outputFormat=JSON", c);
//	 }

}
