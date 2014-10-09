package model;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "pietje precies")
public class Movie {
	
	private static int curID = 0;
	private int id, length;
	private String imdb, title, date, director, shortDesc;
	
	public Movie() {}
	
	public Movie(String imdb, String title, String date, int length, String director, String shortDesc) {
		this.id = ++curID;
		this.imdb = imdb;
		this.title = title;
		this.date = date;
		this.length = length;
		this.director = director;
		this.shortDesc = shortDesc;
	}
	
	public int getId() {
		return id;
	}
	
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
