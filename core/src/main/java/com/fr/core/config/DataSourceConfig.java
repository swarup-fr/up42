package com.fr.core.config;

import javax.sql.DataSource;

import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.zaxxer.hikari.HikariDataSource;

@Configuration
public class DataSourceConfig { 
    
    
    @Bean
    @ConfigurationProperties("spring.datasource")
    public DataSourceProperties dataSourceProperties() {
        return new DataSourceProperties();
    }
    
    @Bean
    public DataSource dataSource() {
    	HikariDataSource hds = dataSourceProperties().initializeDataSourceBuilder()
                .type(HikariDataSource.class).build();
    	hds.setMaxLifetime(30000);
    	hds.setIdleTimeout(0);
    	return hds;
    }
   
}

