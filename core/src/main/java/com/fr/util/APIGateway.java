package com.fr.util;

import java.net.URI;
import java.net.URISyntaxException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@Component
public class APIGateway {

	
	@Autowired
	private RestTemplate restTemplate;

	 public String restTemplateGet(String url) throws RestClientException, URISyntaxException {
		 try {
			 
			HttpHeaders hh = new HttpHeaders();
			hh.setContentType(MediaType.APPLICATION_JSON);

			org.springframework.http.HttpEntity<?> httpEntity = new org.springframework.http.HttpEntity(null, hh);

			ResponseEntity<String> resp = restTemplate.exchange(new URI(url), HttpMethod.GET, httpEntity, String.class);
//			System.out.println(resp);
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

}
