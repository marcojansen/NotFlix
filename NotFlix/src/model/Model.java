package model;

import java.util.ArrayList;

public class Model {
	
	private ArrayList<Movie> movies = new ArrayList<Movie>();
	private ArrayList<User> users = new ArrayList<User>();
	
	public Model() {
		movies.add(new Movie("1234", "Breaking Bad", "2012", 138, "Steven Seagal", "Deze film is een serie"));
		movies.add(new Movie("1234", "Breaking Bad", "2012", 138, "Steven Seagal", "Deze film is een serie"));
	}
	
	public ArrayList<Movie> getMovies() {
		return movies;
	}

}
