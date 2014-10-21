package model;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * Rated movie. Used for the method getMyRatedMovies in model.
 * This contains an movie + an rating.
 *
 */
@XmlRootElement
public class RatedMovie extends Movie {
	
	private double rating;
	
	/**
	 * Empty contructor for JAXB
	 */
	public RatedMovie() {}
	
	/**
	 * Constructor to make an rated movie object.
	 * @param movie		Movie that's rated
	 * @param rating	Rating you've given
	 */
	public RatedMovie(Movie movie, double rating) {
		super(movie);
		this.rating = rating;
	}
	public double getRating() {
		return rating;
	}
	public void setRating(double rating) {
		this.rating = rating;
	}
	
	
	
	

}
