package com.fr.core.config;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import com.fr.util.APIGateway;

@Configuration
@EnableScheduling
public class Scheduler {


	@Value("${webfocus.credentials.user}")
	private String wfUser;
	
	@Value("${webfocus.credentials.pwd}")
	private String wfPwd;
	
	@Value("${webfocus.session.acquire.url}")
	private String wfsUrl;
	
	@Autowired
	private APIGateway ag;
	
	private static final Map<String,String> wfSessionList = Collections.synchronizedMap(new HashMap<String, String>());
	
	public static final Logger logger = Logger.getLogger(Scheduler.class);
	
	@Scheduled(cron = "0 0/15 * * * *")
	public void refreshWebFocusSession() {
		logger.info("session Id Map refresh start.");
		String sid = ag.getWFSessionId(wfsUrl, wfUser, wfPwd);
		wfSessionList.clear();
		wfSessionList.put("0",sid);
		logger.info("session Id Map refresh end.="+sid);
	}
	
	public static Map<String,String> getSessionList(){
		return wfSessionList;
	}
	
	 @PostConstruct
     public void initIt() throws Exception {
		 refreshWebFocusSession();
     }
	
}
