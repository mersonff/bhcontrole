package com.bhcontrole.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Component
@Profile("dev")
@PropertySource(value = { "classpath:application.properties" })
public class JasperConnectionDev implements ConnectionConfig{
	
	@Autowired
	private Environment env;
	
	public Connection getConnection() {
		try {
			Class.forName(env.getProperty("mysql.driver"));
			return DriverManager.getConnection(env.getProperty("mysql.url"), env.getProperty("mysql.usuario"), env.getProperty("mysql.senha"));
		} catch (SQLException e) {
			throw new RuntimeException(e);
		} catch (ClassNotFoundException e) {
			throw new RuntimeException(e);
		}
	}
	
}
