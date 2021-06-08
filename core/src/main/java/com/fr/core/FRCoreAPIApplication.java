package com.fr.core;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = { "com.*"})
public class FRCoreAPIApplication {

	public static void main(String[] args) {
		SpringApplication.run(FRCoreAPIApplication.class, args);
	}

}
