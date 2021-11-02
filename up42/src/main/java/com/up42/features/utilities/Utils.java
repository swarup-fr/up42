package com.up42.features.utilities;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

import org.springframework.core.io.ClassPathResource;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.up42.features.vo.FeatureCollection;

public class Utils {

	public static List<FeatureCollection> loadData() throws IOException, JsonMappingException, Exception{
		try {
			File resource = new ClassPathResource("source-data.json"). getFile();
			String text = new String(Files. readAllBytes(resource. toPath()));
			ObjectMapper mapper = new ObjectMapper();
			List<FeatureCollection> fc = mapper.readValue(text, new TypeReference<List<FeatureCollection>>(){});
			return fc;
		}catch(JsonMappingException ie) {
			ie.printStackTrace();
			throw ie;
		}catch(IOException  ie) {
			ie.printStackTrace();
			throw ie;
		}
		catch(Exception ie) {
			ie.printStackTrace();
			throw ie;
		}
	}
}
