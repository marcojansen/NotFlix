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
	
	public ArrayList<Movie> getRatedMovies() {
		//TODO
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
	
	public User login(String nickname, String password) {
		for (User user: users) {
			if (user.getNickName().equals(nickname) && user.getPassword().equals(password)) {
				return user;
			}
		}
		return null;
	}
	
	public ArrayList<User> getUsers(String token) {
		for (User user : users) {
			if (token.equals(user.getToken())) {
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
			if (token.equals(user.getToken())) {
				return getUserByNickname(nickname);
			}
		}
		return null;
	}

}
