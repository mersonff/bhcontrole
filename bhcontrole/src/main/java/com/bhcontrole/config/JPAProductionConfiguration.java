package com.bhcontrole.config;

import java.net.URI;
import java.net.URISyntaxException;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

@Profile("prod")
@Configuration
public class JPAProductionConfiguration {
	
	   @Bean	   
	   public DataSource dataSource() throws URISyntaxException{
	      DriverManagerDataSource dataSource = new DriverManagerDataSource();
	      dataSource.setDriverClassName("org.postgresql.Driver");
	      URI dbUrl = new URI(System.getenv("DATABASE_URL"));
		  dataSource.setUrl("jdbc:postgresql://" + dbUrl.getHost() + ":" + dbUrl.getPort() + dbUrl.getPath());
		  dataSource.setUsername(dbUrl.getUserInfo().split(":")[0]);
		  dataSource.setPassword(dbUrl.getUserInfo().split(":")[1]);
	      return dataSource;
	   }	
}
