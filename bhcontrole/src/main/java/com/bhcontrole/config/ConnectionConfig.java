package com.bhcontrole.config;

import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.SQLException;

public interface ConnectionConfig {
	
	Connection getConnection() throws URISyntaxException, SQLException;

}
