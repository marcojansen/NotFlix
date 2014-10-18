package model;

import java.util.ArrayList;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
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
	
	public ArrayList<User> getUsers(){
		return users;
	}
	
	public boolean addRating(Movie movie, int rating, User user){
		if(movies.contains(movie) && rating > 0 && rating <= 10){
			return movie.addRating(new Rating(rating, movie.getId()), user);
		}
		return false;
	}

	public ArrayList<Movie> getRatedMovies() {
		ArrayList<Movie> ratedMovies = new ArrayList<Movie>();
		for (Movie movie: movies) {
			if (movie.getAverageRating() != 0.0) {
				ratedMovies.add(movie);
			}
		}
		return ratedMovies;
	}
	
	public Token addUser(String firstName, String insert, String lastName, String nickName ,String password) {
		for (User user : users) {
			if (user.getNickName().equals(nickName)) {
				return null;
			}
		}
		User user = new User(firstName, insert, lastName, nickName, password);
		users.add(user);
		return user.getToken();
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
		return null;
	}
	
	public boolean isUser(String token){
		for(User user : users){
			if(user.getToken().getToken().equals(token)){
				return true;
			}
		}
		return false;
	}
	
	private User getUserByToken(String token){
		for(User user : users){
			if(user.getToken().getToken().equals(token)){
				return user;
			}
		}
		return null;
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
	
	private Movie getMovie(String imdbId){
		for(Movie movie : movies){
			if(movie.getImdb().equals(imdbId)){
				return movie;			
			}
		}
		return null;
	}
	
	public boolean addRating(String imdbId, double score, String token){
		Movie movie = getMovie(imdbId);
		User user = getUserByToken(token);
		if(movie != null && user != null){
			return movie.addRating(new Rating(score, movie.getId()), user);
		}
		return false;
	}
	
	public boolean deleteRating(String imdbId, String token) {
		Movie movie = getMovie(imdbId);
		User user = getUserByToken(token);
		if(movie != null && user != null){
			return movie.deleteRating(user);
		}
		return false;
	}

	public boolean changeRating(String token, String imdb, double rating) {
		Movie movie = getMovie(imdb);
		User user = getUserByToken(token);
		if (movie != null && user != null) {
			return movie.changeRating(new Rating(rating, movie.getId()), user);
		}
		return false;
		
	}

	public ArrayList<RatedMovie> getMyRatedMovies(String token) {
		User user = getUserByToken(token);
		ArrayList<RatedMovie> myRatedMovies = new ArrayList<RatedMovie>();
		for (Movie movie: movies) {
			if (movie.getRatings().containsKey(user)) {
				Rating rating = movie.getRatings().get(user);
				myRatedMovies.add(new RatedMovie(movie, rating.getScore()));
			}
		}
		return myRatedMovies;
	}
	
	public ArrayList<Movie> getUnratedMovies() {
		ArrayList<Movie> unratedMovies = new ArrayList<Movie>();
		for (Movie movie: movies) {
			if (movie.getAverageRating() == 0.0) {
				unratedMovies.add(movie);
			}
		}
		return unratedMovies;
	}

	public ArrayList<Movie> getMyUnratedMovies(String token) {
		User user = getUserByToken(token);
		ArrayList<Movie> myUnratedMovies = new ArrayList<Movie>();
		for (Movie movie: movies) {
			if (!movie.getRatings().containsKey(user)) {
				myUnratedMovies.add(movie);
			}
		}
		return myUnratedMovies;
	}

}
