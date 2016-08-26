package eiko.drive;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import eiko.drive.obj.Slide;

public class StudyDataConnector extends Connector {

	public StudyDataConnector() throws ClassNotFoundException {
		super();
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
	
	public ArrayList<Slide> getSlides(String subject) {
		ArrayList<Slide> slides = new ArrayList<Slide>();
		if (c == null) return slides;
		try {
			Statement st = c.createStatement();
			String sql = "SELECT * FROM slide WHERE subject = " + subject 
					+ "ORDER BY num;";
			ResultSet rs = st.executeQuery(sql);
			while(rs.next()) {
				int i = rs.getInt(Slide.INT_NUM);
				slides.add(i,new Slide(rs.getString(Slide.STRING_SUBJECT),
						rs.getString(Slide.STRING_TITLE),
						i,
						rs.getString(Slide.STRING_IMAGE),
						rs.getString(Slide.STRING_CONTENT)));
				
			}
			st.close();
		} catch (SQLException e) {
			return slides;
		}
		return slides;
	}
	
	public ArrayList<String> getSlideSubjects() {
		ArrayList<String> subj = new ArrayList<String>();
		if (c == null) return subj;
		try {
			Statement st = c.createStatement();
			String sql = "SELECT DISTINCT subject FROM slide;";
			ResultSet rs = st.executeQuery(sql);
			while(rs.next()) {
				subj.add(rs.getString(Slide.STRING_SUBJECT));
				
			}
			st.close();
		} catch (SQLException e) {
			return subj;
		}
		return subj;
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

}
