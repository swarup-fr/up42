package com.fr.core.service;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.fr.core.model.FieldMappingMaster;
import com.fr.core.repository.FieldMappingsMasterRepository;
import com.fr.core.repository.FieldMappingsRepository;

@Service
public class CacheDataServiceImpl {

	@Autowired
	private FieldMappingsMasterRepository fmmr;
	
	@Autowired
	private FieldMappingsRepository fmr;
	
	 @Cacheable(value = "master")
	 public Map<Long,FieldMappingMaster> getFieldMappingsMaster(Long apiId){
		 List<FieldMappingMaster> fmml= fmmr.findByWfApiId(apiId);
		 Map<Long, FieldMappingMaster> map = fmml.stream()
                 .collect(Collectors.toMap(FieldMappingMaster::getFmId, Function.identity()));
		 return map;
	 }
	 
	 @Cacheable(value = "fieldMapping")
	 public String getFieldMappings(Long id){
		 return fmr.findById(id).get().getFieldString();
	 }
	 
	 public String getFieldsList(Long apiId, Long reportId) {
		 Map<Long, FieldMappingMaster> map = getFieldMappingsMaster(apiId);
		 String[] fieldArray = getFieldMappings(reportId).split(",");;
		 StringBuilder fieldsList = new StringBuilder();
		 for(String s : fieldArray) {
			 fieldsList.append(map.get((Long.parseLong(s))).getField()).append("::").append(map.get((Long.parseLong(s))).getJsonField()).append("~~");
		 }
		 return fieldsList.toString();
	 }
}
