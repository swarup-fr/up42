package com.fr.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public class APIUtils {
	
	public static final Logger logger = Logger.getLogger(APIUtils.class);

	public static String formatUrl(String url,Map<String,String> paramsMap,String apiNum) {
		StringBuilder urlParams = new StringBuilder();
		for(String p :paramsMap.keySet()) {
			urlParams.append("&").append(p).append("=").append(paramsMap.get(p));
		}
		url = url.replace("<APINUM>", apiNum);
		url = url+urlParams.toString();
		logger.info(url);
		return url;
	}
	
	public static Map<String,String> extractInputParams(String filterParameter){
		Gson gson = new Gson();
		JsonObject jsonObject = gson.fromJson(filterParameter, JsonObject.class);

		Map<String,String> pamasMap = new LinkedHashMap<String, String>();
		for(String p :jsonObject.keySet()) {
			pamasMap.put(p,jsonObject.get(p).getAsString());
		}
		return pamasMap;
	}
	
	public static List<Map<String,String>> convertToJsonMap(String respJsonString,String responseToParse) {
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
