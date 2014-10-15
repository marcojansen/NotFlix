package model;

import java.util.ArrayList;

public class Model {
	
	ArrayList<Movie> movies = new ArrayList<Movie>();
	ArrayList<User> users = new ArrayList<User>();
	
	public Model() {
		movies.add(new Movie("1234", "Breaking Bad", "2012", 138, "Steven Seagal", "Deze film is een serie"));
		movies.add(new Movie("1235","Breaking Good","2013",140,"Steven Seagul","Dit is een test"));
		users.add(new User("marco", null, "jansen", "marco", "jansen"));
		users.add(new User("daan", null, "veldhof", "daan", "veldhof"));
	}
	
	public ArrayList<Movie> getMovies() {
		return movies;
	}
	
	public void setMovies(ArrayList<Movie> movies) {
		this.movies = movies;
	}
	
	public ArrayList<User> getUsers(){
		return users;
	}
	
	public void setUsers(ArrayList<User> users) {
		this.users = users;
	}
	
	
	public boolean addRating(Movie movie, int rating, User user){
		if(movies.contains(movie) && rating > 0 && rating <= 10){
			return movie.addRating(new Rating(rating, movie.getId()), user);
		}
		return false;
	}

	public ArrayList<Movie> getRatedMovies() {
		return movies;
	}
	
	public User addUser(String firstName, String insert, String lastName, String nickName
			,String password) {
		for (User user : users) {
			if (user.getNickName().equals(nickName)) {
				return null;
			}
		}
		User user = new User(firstName, insert, lastName, nickName, password);
		users.add(user);
		return user;
	}
	
	public Token login(String nickname, String password) {
		for (User user: users) {
			if (user.getNickName().equals(nickname) && user.getPassword().equals(password)) {
				return user.getToken();
			}
		}
		return null;
	}
	
	public ArrayList<User> getUsers(String token) {
		for (User user : users) {
			if (token.equals(user.getToken().getToken())) {
				return users;
			}
		}
		return new ArrayList<User>();
	}
	
	private User getUserByNickname(String nickname) {
		for (User user: users) {
			if (user.getNickName().equals(nickname)) {
				return user;
			}
		}
		return null;
	}
	
	public User getUser(String nickname, String token) {
		for (User user: users) {
			if (user.getToken().getToken().equals(token)) {
				return getUserByNickname(nickname);
			}
		}
		return null;
	}

}
