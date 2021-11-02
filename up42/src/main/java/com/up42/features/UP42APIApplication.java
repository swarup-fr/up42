package com.up42.features;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableCaching
@ComponentScan(basePackages = { "com.*"})
public class UP42APIApplication {

	public static void main(String[] args) {
		SpringApplication.run(UP42APIApplication.class, args);
	}

}
