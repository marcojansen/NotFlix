package model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import com.fasterxml.jackson.annotation.JsonIgnore;

@XmlRootElement
public class Movie {
	
	private static int curID = 0;
	private int id, length;
	private double averageRating;
	private String imdb, title, date, director, shortDesc;
	private Map<User, Rating> ratings = new HashMap<User, Rating>();
	
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
	
	@XmlTransient
	@JsonIgnore
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
	
	public double getAverageRating() {
		return averageRating;
	}
	
	public void setAverageRating(double averageRating) {
		this.averageRating = averageRating;
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
	
	public boolean addRating(Rating rating, User user){
		if(ratings.get(user) == null){
			ratings.put(user, rating);
			updateAverageRating();
			return true;
		}
		
		return false;
	}
	
	private void updateAverageRating() {
		Set<User> set = ratings.keySet();
		double total = 0;
		for (User u : set) {
			total +=ratings.get(u).getScore();
		}
		averageRating = (total / set.size());
	}
	
	public Map<User, Rating> getRatings() {
		return ratings;
	}

	public boolean deleteRating(User user) {
		if(ratings.get(user) != null){
			ratings.remove(user);
		}
		return true;
	}
	
	
}
