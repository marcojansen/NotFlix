package model;

import java.util.HashSet;
import java.util.Set;

public class Model {
	
	Set<Movie> movies = new HashSet<Movie>();
	Set<User> users = new HashSet<User>();
	
	public Model() {
		movies.add(new Movie("1234", "Breaking Bad", "2012", 138, "Steven Seagal", "Deze film is een serie"));
	}
	
	public Set<Movie> getMovies() {
		return movies;
	}

}
