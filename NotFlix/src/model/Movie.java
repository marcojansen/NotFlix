package model;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

@XmlRootElement
public class Movie {
	
	private static int curID = 0;
	private int id, length;
	private String imdb, title, date, director, shortDesc;
	
	public Movie() {
		// TODO Auto-generated constructor stub
	}
	
	public Movie(String imdb, String title, String date, int length, String director, String shortDesc) {
		this.id = ++curID;
		this.imdb = imdb;
		this.title = title;
		this.date = date;
		this.length = length;
		this.director = director;
		this.shortDesc = shortDesc;
	}
	
	@XmlTransient
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setLength(int length) {
		this.length = length;
	}

	public void setImdb(String imdb) {
		this.imdb = imdb;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public void setDirector(String director) {
		this.director = director;
	}

	public void setShortDesc(String shortDesc) {
		this.shortDesc = shortDesc;
	}
	@XmlAttribute
	public String getImdb() {
		return imdb;
	}
	
	public String getTitle() {
		return title;
	}
	
	public String getDate() {
		return date;
	}

	public int getLength() {
		return length;
	}
	
	public String getDirector() {
		return director;
	}
	
	public String getShortDesc() {
		return shortDesc;
	}
}
