package com.up42.features;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.up42.features.service.IFeature;
import com.up42.features.utilities.Utils;
import com.up42.features.vo.FeatureCollection;
import com.up42.features.vo.ResponseVo;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UP42APIApplicationTests {
	
	
	@Autowired
	private IFeature iFeature;
	
	@Test
	public void loadFeatures() {
		try {
			List<FeatureCollection> lfc =  Utils.loadData();
			assertEquals(14, lfc.size(), 0);
		}catch(Exception e) {
			
		}
	}

	@Test
	public void getFeatures() {
		try {
			List<ResponseVo> lr =  iFeature.getAllFeatures();
			assertEquals(14, lr.size(), 0);
		}catch(Exception e) {
			
		}
	}
	
	@Test
	public void getFeature() {
		try {
			ResponseVo lr =  iFeature.getFeature("2ed68fe5-f719-48c3-aa27-b0cc155f06cb");
			assertEquals("matches", lr.getId(), "2ed68fe5-f719-48c3-aa27-b0cc155f06cb");
		}catch(Exception e) {
			
		}
	}
}
