package model;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class RatedMovie extends Movie {
	
	private double rating;
	
	public RatedMovie() {}
	
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
