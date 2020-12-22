package team;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

public class Hikariconfig {
	HikariConfig config = new HikariConfig();
	public Hikariconfig() {
		
		config.setJdbcUrl("jdbc:oracle:thin:@localhost:1521/XEPDB1");
		config.setUsername("team4");
		config.setPassword("1234");
		config.addDataSourceProperty("cachePrepStmts", "true");
		config.addDataSourceProperty("prepStmtCacheSize", "250");
		config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
		
	}
	
	public HikariDataSource config() {
		return new HikariDataSource(config);
	}
	
}
