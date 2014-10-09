package model;

import java.util.ArrayList;

public class Model {
	
	ArrayList<Movie> movies = new ArrayList<Movie>();
	ArrayList<User> users = new ArrayList<User>();
	
	public Model() {
		movies.add(new Movie("1234", "Breaking Bad", "2012", 138, "Steven Seagal", "Deze film is een serie"));
		movies.add(new Movie("1235","Breaking Good","2013",140,"Steven Seagul","Dit is een test"));
	}
	
	public ArrayList<Movie> getMovies() {
		return movies;
	}

}
