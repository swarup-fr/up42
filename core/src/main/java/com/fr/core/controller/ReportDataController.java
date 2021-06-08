package com.fr.core.controller;

import java.io.IOException;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.fr.core.service.ASPPMergeStrategy;
import com.fr.core.service.CoverPageStrategy;
import com.fr.core.service.FixedIncomeAnalysisStrategy;
import com.fr.core.service.MergeContext;
import com.fr.core.vo.ReportFilter;
import com.fr.core.vo.ResponseVo;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Controller
public class ReportDataController {
	
	public static final Logger logger = Logger.getLogger(ReportDataController.class);
	
	@Autowired
	private MergeContext mergeContext;
	
	@Autowired
	private ASPPMergeStrategy asppMS;
	
	@Autowired
	private FixedIncomeAnalysisStrategy fiaMS;
	
	@Autowired
	private CoverPageStrategy coverpageMS;
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(method = RequestMethod.POST, value = "/asset/performance", consumes = "application/json",produces = "application/json")
	@ApiOperation(value = "Provides Asset Performance Data.")
	@ApiResponses(value = {
	        @ApiResponse(code = 200, message = "The request has succeeded."),
	        @ApiResponse(code = 401, message = "The request requires user authentication."),
	        @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden."),
	        @ApiResponse(code = 404, message = "The server has not found anything matching the Request-URI."),
	        @ApiResponse(code = 500, message = "Internal Server Error.")
	})
	public ResponseEntity<ResponseVo> assetPerformanceData(
			@RequestBody(required=true) ReportFilter filterParameter) throws IOException {
		logger.info("Controller invoked asset/performance:: ");
		ResponseVo response= new ResponseVo(); 
		mergeContext.setMergeContext(asppMS);
	    try {
	    	response = mergeContext.executeMergeStrategy(filterParameter);
		} catch (Exception e) {
			logger.error("Error in assetPerformanceData", e);
			return new ResponseEntity("Exception in asset/performance", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	    return new ResponseEntity<ResponseVo>(response,HttpStatus.OK);
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(method = RequestMethod.POST, value = "/fixedIncome/fixedIncomeAnalysis",consumes = "application/json", produces = "application/json")
	@ApiOperation(value = "Provides Fixed Income Analysis Data.")
	@ApiResponses(value = {
	        @ApiResponse(code = 200, message = "The request has succeeded."),
	        @ApiResponse(code = 401, message = "The request requires user authentication."),
	        @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden."),
	        @ApiResponse(code = 404, message = "The server has not found anything matching the Request-URI."),
	        @ApiResponse(code = 500, message = "Internal Server Error.")
	})
	public ResponseEntity<ResponseVo> fixedIncomeAnalysisData(
			@RequestBody(required=true) ReportFilter filterParameter) throws IOException {
		logger.info("Controller invoked fixedIncome/fixedIncomeAnalysis:: ");
		ResponseVo response= new ResponseVo(); 
		mergeContext.setMergeContext(fiaMS);
	    try {
	    	response = mergeContext.executeMergeStrategy(filterParameter);
		} catch (Exception e) {
			logger.error("Error in assetPerformanceData", e);
			return new ResponseEntity("Exception in fixedIncome/fixedIncomeAnalysis", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	    return new ResponseEntity<ResponseVo>(response,HttpStatus.OK);
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(method = RequestMethod.POST, value = "/coverpage/demographics",consumes = "application/json", produces = "application/json")
	@ApiOperation(value = "Provides Cover Page Demograhics Data.")
	@ApiResponses(value = {
	        @ApiResponse(code = 200, message = "The request has succeeded."),
	        @ApiResponse(code = 401, message = "The request requires user authentication."),
	        @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden."),
	        @ApiResponse(code = 404, message = "The server has not found anything matching the Request-URI."),
	        @ApiResponse(code = 500, message = "Internal Server Error.")
	})
	public ResponseEntity<ResponseVo> coverpageDemographicsData(
			@RequestBody(required=true) ReportFilter filterParameter) throws IOException {
		logger.info("Controller invoked coverpage/demographics:: ");
		ResponseVo response= new ResponseVo(); 
		mergeContext.setMergeContext(coverpageMS);
	    try {
	    	response = mergeContext.executeMergeStrategy(filterParameter);
		} catch (Exception e) {
			logger.error("Error in coverPageData", e);
			return new ResponseEntity("Exception in coverpage/demographics", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	    return new ResponseEntity<ResponseVo>(response,HttpStatus.OK);
	}
	
}
