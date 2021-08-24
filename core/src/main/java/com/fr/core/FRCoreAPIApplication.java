package com.fr.core;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
@EnableCaching
@ComponentScan(basePackages = { "com.*"})
public class FRCoreAPIApplication {

	public static void main(String[] args) {
		SpringApplication.run(FRCoreAPIApplication.class, args);
	}

}
