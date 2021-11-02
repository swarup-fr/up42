package com.up42.features.service;

import java.io.IOException;
import java.util.List;

import com.up42.features.vo.ResponseVo;

public interface IFeature {

	public List<ResponseVo> getAllFeatures() throws IOException, Exception;
	
	public ResponseVo getFeature(String id) throws IOException, Exception;
	
	public byte[] getFeatureQuicklook(String id) throws IOException, Exception;
}
