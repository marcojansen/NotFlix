package model;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * Movie.
 * Contains movie information.
 */
@XmlRootElement
public class Movie {
	
	private static int curID = 0;
	private int id, length;
	private double averageRating;
	private String imdb, title, date, director, shortDesc;
	private Map<User, Rating> ratings = new HashMap<User, Rating>();
	
	/**
	 * Empty constructor for Jaxb
	 */
	public Movie() {}
	
	/**
	 * Constructor to make a movie.
	 * @param imdb		IMDb ID
	 * @param title		Title of the movie
	 * @param date		Date the movie was released
	 * @param length	Length of the movie in minutes
	 * @param director	Director of the movie
	 * @param shortDesc	Short description of the movie
	 */
	public Movie(String imdb, String title, String date, int length, String director, String shortDesc) {
		this.id = ++curID;
		this.imdb = imdb;
		this.title = title;
		this.date = date;
		this.length = length;
		this.director = director;
		this.shortDesc = shortDesc;
	}
	
	/**
	 * Constructor used by ratedMovie
	 * @param movie	Movie to create
	 */
	public Movie(Movie movie) {
		this.id = movie.getId();
		this.imdb = movie.getImdb();
		this.title = movie.getTitle();
		this.date = movie.getDate();
		this.length = movie.getLength();
		this.director = movie.getDirector();
		this.shortDesc = movie.getShortDesc();
		this.averageRating = movie.getAverageRating();
	}
	
	@XmlAttribute
	public String getImdb() {
		return imdb;
	}
	
	@XmlTransient
	@JsonIgnore
	public int getId() {
		return id;
	}
	
	@XmlTransient
	@JsonIgnore
	public Map<User, Rating> getRatings() {
		return ratings;
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
	
	public double getAverageRating() {
		return averageRating;
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
	
	
	public void setAverageRating(double averageRating) {
		this.averageRating = averageRating;
	}
	
	/**
	 * Method to add a rating to a movie.
	 * @param rating	Rating you like to give
	 * @param user		The user which wants to give the rating.
	 * @return			Added(True) or not added(False)
	 */
	public boolean addRating(Rating rating, User user){
		if(ratings.get(user) == null){
			ratings.put(user, rating);
			updateAverageRating();
			return true;
		}
		
		return false;
	}
	
	/**
	 * Method to change a given rating to a movie.
	 * @param rating	New rating you would like to give.
	 * @param user		The user that wants to change the rating.
	 * @return			Modified(True) or not modified(False)
	 */
	public boolean changeRating(Rating rating, User user) {
		if (ratings.get(user) != null) {
			ratings.put(user, rating);
			updateAverageRating();
			return true;
		}
		return false;
	}

	/**
	 * Method to delete a given rating to a movie.
	 * @param user		User that gave the rating you want to delete.
	 * @return			Removed(True) or not removed(False)
	 */
	public boolean deleteRating(User user) {
		if(ratings.get(user) != null){
			ratings.remove(user);
			updateAverageRating();
			return true;
		}
		return false;
	}
	
	/**
	 * Method to update the average rating of the movie.
	 * Called when an rating is added,changed or deleted.
	 */
	private void updateAverageRating() {
		Set<User> set = ratings.keySet();
		double total = 0;
		for (User u : set) {
			total +=ratings.get(u).getScore();
		}
		if (set.size() == 0) {
			averageRating = 0;
		} else {
			averageRating = (total / set.size());
		}
	}
	
}
