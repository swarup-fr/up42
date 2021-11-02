package com.up42.features.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.up42.features.utilities.Utils;
import com.up42.features.vo.FeatureCollection;
import com.up42.features.vo.ResponseVo;

@Service
public class FeatureImpl implements IFeature{

	public List<ResponseVo> getAllFeatures() throws IOException, Exception{
		try {
			List<FeatureCollection> fc = Utils.loadData();
			if(null==fc || fc.size()==0)
				return null;
			
			List<ResponseVo> rl = new ArrayList<ResponseVo>();
			fc.stream().forEach(f -> rl.add(prepareResponse(f)));
			return rl;
		}catch(Exception e) {
			throw e;
		}
	}
	
	public ResponseVo getFeature(String id) throws IOException, Exception{
		try {
			List<FeatureCollection> r = Utils.loadData();
			if(null==r || r.size()==0)
				return null;
			
			FeatureCollection f = r.parallelStream().filter(fc -> id.equals(fc.getFeatures().get(0).getProperties().getId())).collect(Collectors.toList()).get(0);
			
			if(null==f)
				return null;
			
			ResponseVo rv = prepareResponse(f);
			
			return rv;
		}catch(Exception e) {
			throw e;
		}
	}
	
	public byte[] getFeatureQuicklook(String id) throws IOException, Exception{
		try {
			List<FeatureCollection> r = Utils.loadData();
			if(null==r || r.size()==0)
				return null;
			
			FeatureCollection f = r.parallelStream().filter(fc -> id.equals(fc.getFeatures().get(0).getProperties().getId())).collect(Collectors.toList()).get(0);
			
			if(null==f)
				return null;
			
			return org.apache.tomcat.util.codec.binary.Base64.decodeBase64(f.getFeatures().get(0).getProperties().getQuicklook());
		}catch(Exception e) {
			throw e;
		}
	}
	


	private ResponseVo prepareResponse(FeatureCollection f) {
		ResponseVo rv = new ResponseVo();
		rv.setId(f.getFeatures().get(0).getProperties().getId());
		rv.setBeginViewingDate(f.getFeatures().get(0).getProperties().getAcquisition().getBeginViewingDate());
		rv.setEndViewingDate(f.getFeatures().get(0).getProperties().getAcquisition().getEndViewingDate());
		rv.setTimeStamp(f.getFeatures().get(0).getProperties().getTimestamp());
		rv.setMissionName(f.getFeatures().get(0).getProperties().getAcquisition().getMissionName());
		return rv;
	}
	
}
