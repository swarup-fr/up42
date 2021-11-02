package com.up42.features.controller;

import java.io.IOException;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.up42.features.service.IFeature;
import com.up42.features.vo.ResponseVo;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Controller
public class FeaturesController {
	
	public static final Logger logger = Logger.getLogger(FeaturesController.class);
	
	@Autowired
	private IFeature iFeature;
	
		
	@RequestMapping(method = RequestMethod.GET, value = "/features",  produces = "application/json")
	@ApiOperation(value = "Gets all features.")
	@ApiResponses(value = {
	        @ApiResponse(code = 200, message = "The request has succeeded."),
	        @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden."),
	        @ApiResponse(code = 404, message = "The server has not found anything matching the Request-URI."),
	        @ApiResponse(code = 500, message = "Internal Server Error.")
	})
	public ResponseEntity<List<ResponseVo>> getFeatures() throws IOException {
		logger.info("Controller invoked /features:: ");
		try {
			List<ResponseVo> rl = iFeature.getAllFeatures();
			
		    return new ResponseEntity<List<ResponseVo>>(rl,HttpStatus.OK);
		}catch(Exception e) {
			return new ResponseEntity<>(null,HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/features/{id}",  produces = "application/json")
	@ApiOperation(value = "Gets all features.")
	@ApiResponses(value = {
	        @ApiResponse(code = 200, message = "The request has succeeded."),
	        @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden."),
	        @ApiResponse(code = 404, message = "The server has not found anything matching the Request-URI."),
	        @ApiResponse(code = 500, message = "Internal Server Error.")
	})
	public ResponseEntity<ResponseVo> getFeature(@PathVariable String id) throws IOException {
		logger.info("Controller invoked /features/{id}:: ");
		try {
			
			ResponseVo rv = iFeature.getFeature(id);
		    return new ResponseEntity<ResponseVo>(rv,HttpStatus.OK);
		}catch(Exception e) {
			return new ResponseEntity<>(null,HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/features/{id}/quicklook",  produces = "image/png")
	@ApiOperation(value = "Gets quicl look for a feature.")
	@ApiResponses(value = {
	        @ApiResponse(code = 200, message = "The request has succeeded."),
	        @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden."),
	        @ApiResponse(code = 404, message = "The server has not found anything matching the Request-URI."),
	        @ApiResponse(code = 500, message = "Internal Server Error.")
	})
	public ResponseEntity<byte[]> getQuickLook(@PathVariable String id) throws IOException {
		logger.info("Controller invoked /features/{id}/quicklook:: ");
		try {
			byte[] qli = iFeature.getFeatureQuicklook(id);
			
		    return new ResponseEntity<byte[]>(qli,HttpStatus.OK);
		}catch(Exception e) {
			return new ResponseEntity<>(null,HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
