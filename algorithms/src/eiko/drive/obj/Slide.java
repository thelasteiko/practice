package eiko.drive.obj;

import java.io.File;

import javafx.scene.image.Image;

public class Slide {
	
	public static final String STRING_SUBJECT = "subject";
	public static final String STRING_TITLE = "title";
	public static final String INT_NUM = "num";
	public static final String STRING_IMAGE = "img_file";
	public static final String STRING_CONTENT = "content";

	private String subject;
	private String title;
	private int num;
	private String img_file;
	private String content;
	
	
	public Slide() {
		super();
	}
	public Slide(String subject, String title, int num, String img_file, String content) {
		super();
		this.subject = subject;
		this.title = title;
		this.num = num;
		this.img_file = img_file;
		this.content = content;
	}
	/**
	 * @return the subject
	 */
	public String getSubject() {
		return subject;
	}
	/**
	 * @param subject the subject to set
	 */
	public void setSubject(String subject) {
		this.subject = subject;
	}
	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}
	/**
	 * @param title the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}
	/**
	 * @return the num
	 */
	public int getNum() {
		return num;
	}
	/**
	 * @param num the num to set
	 */
	public void setNum(int num) {
		this.num = num;
	}
	/**
	 * @return the img_file
	 */
	public String getImg_file() {
		return img_file;
	}
	/**
	 * @param img_file the img_file to set
	 */
	public void setImg_file(String img_file) {
		this.img_file = img_file;
	}
	
	public Image getImage() {
		return new Image(new File(img_file).toURI().toString());
	}
	/**
	 * @return the content
	 */
	public String getContent() {
		return content;
	}
	/**
	 * @param content the content to set
	 */
	public void setContent(String content) {
		this.content = content;
	}
}
