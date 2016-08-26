package eiko.drive;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Connector {
	
	public static final String CLASS_NAME = "org.sqlite.JDBC";
	public static final String DB_NAME = "jdbc:sqlite:study.db";
	
	public static final String FN_DBSTART = "./resource/db_starter.txt";
	
	Connection c;
	
	public Connector() throws ClassNotFoundException {
		Class.forName(CLASS_NAME);
	}
	
	public void open() {
		if(c != null) return;
		try {
			c = DriverManager.getConnection(DB_NAME);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void close() {
		if (c == null) return;
		try {
			c.close();
			c = null;
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
