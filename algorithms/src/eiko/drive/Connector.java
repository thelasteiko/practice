package eiko.drive;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

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
	
	public boolean insert_slide(String subject, String title, 
			int num, String img_file, String content) {
		if (c == null) return false;
		try {
			Statement st = c.createStatement();
			String sql = "INSERT INTO slide VALUES ("
					+ subject + "," + title + "," + num
					+ "," + img_file + "," + content + ");";
			st.executeUpdate(sql);
			st.close();
			c.commit();
		} catch (SQLException e) {
			return false;
		}
		return true;
	}
	
	public void create()  {
		try (BufferedReader br = new BufferedReader(new FileReader(FN_DBSTART))) {
			String line = null;
			StringBuilder sb = new StringBuilder();
			Statement st = c.createStatement();
			while ((line = br.readLine()) != null) {
				sb.append(line);
				if (line.contains(";")) {
					st.executeUpdate(sb.toString());
					sb.delete(0, sb.length()-1);
				}
			}
			st.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
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
