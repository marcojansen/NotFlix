package model;

import java.util.ArrayList;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * Model class that contains a list of movies and a list of users
 *
 */
@XmlRootElement
public class Model {
	
	ArrayList<Movie> movies = new ArrayList<Movie>();
	ArrayList<User> users = new ArrayList<User>();
	
	/**
	 * Constructor
	 * Adds two movies and two users to their respective lists
	 */
	public Model() {
		movies.add(new Movie("tt1924435", "Let's Be Cops", "2014", 104, "Luke Greenfield", "Two struggling pals dress as police officers for a costume party and become neighborhood sensations. But when these newly-minted \"heroes\" get tangled in a real life web of mobsters and dirty detectives, they must put their fake badges on the line."));
		movies.add(new Movie("tt2557490","A Million Ways to Die in the West","2014",116,"Seth MacFarlane","As a cowardly farmer begins to fall for the mysterious new woman in town, he must put his new-found courage to the test when her husband, a notorious gun-slinger, announces his arrival."));
		movies.add(new Movie("tt2294449","22 Jump Street","2014",80,"Phil Lord","After making their way through high school (twice), big changes are in store for officers Schmidt and Jenko when they go deep undercover at a local college."));
		movies.add(new Movie("tt2402157","The November Man","2014",108,"Roger Donaldson","An ex-CIA operative is brought back in on a very personal mission."));
		movies.add(new Movie("tt0455944","Equalizer","2014",132,"Antoine Fuqua","A man believes he has put his mysterious past behind him and has dedicated himself to beginning a new, quiet life. But when he meets a young girl under the control of ultra-violent Russian gangsters, he can't stand idly by - he has to help her."));
		movies.add(new Movie("tt2713180","Fury","2014",134,"David Ayer","April, 1945. As the Allies make their final push in the European Theatre, a battle-hardened army sergeant named Wardaddy commands a Sherman tank and his five-man crew on a deadly mission behind enemy lines. Out-numbered, out-gunned, and with a rookie soldier thrust into their platoon, Wardaddy and his men face overwhelming odds in their heroic attempts to strike at the heart of Nazi Germany."));
		
		users.add(new User("marco", null, "jansen", "marco", "jansen"));
		users.add(new User("daan", null, "veldhof", "daan", "veldhof"));
	}
	
//	Methods for movies -------------------------------------------------------------------------------------
	
	/**
	 * Getter for movies
	 * @return list with all movies
	 */
	public ArrayList<Movie> getMovies() {
		return movies;
	}
	
	/**
	 * Getter for a Movie with the given imdbId
	 * @param imdbId	Imdb id of the movie
	 * @return Movie if movie with the given imdbid exists, else null
	 */
	private Movie getMovie(String imdbId){
		for(Movie movie : movies){
			if(movie.getImdb().equals(imdbId)){
				return movie;			
			}
		}
		return null;
	}
	
	/**
	 * Getter for all rated movies
	 * @return a list with all the movies that have a rating
	 */
	public ArrayList<Movie> getRatedMovies() {
		ArrayList<Movie> ratedMovies = new ArrayList<Movie>();
		for (Movie movie: movies) {
			if (movie.getAverageRating() != 0.0) {
				ratedMovies.add(movie);
			}
		}
		return ratedMovies;
	}
	
	/**
	 * Getter for a list of movies that has not been rated
	 * @return list with movies without a rating
	 */
	public ArrayList<Movie> getUnratedMovies() {
		ArrayList<Movie> unratedMovies = new ArrayList<Movie>();
		for (Movie movie: movies) {
			if (movie.getAverageRating() == 0.0) {
				unratedMovies.add(movie);
			}
		}
		return unratedMovies;
	}
	
	/**
	 * Getter for a list of movies that has been rated by a user
	 * @param token		Token of the user
	 * @return list with movies with a rating from the user
	 */
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
	
	/**
	 * Getter for a list of movies that has not been rated by the user
	 * @param token		Token of the user
	 * @return list of movies that have not been rated by the user
	 */
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
	
//	Method for users ------------------------------------------------------------------------------------------
	
	/**
	 * Getter for users
	 * @return list with all users
	 */
	public ArrayList<User> getUsers(){
		return users;
	}
	
	/**
	 * Getter for a User with the given token
	 * @param token		Token that is being checked
	 * @return User if a user exists with the given token, else null
	 */
	private User getUserByToken(String token){
		for(User user : users){
			if(user.getToken().getToken().equals(token)){
				return user;
			}
		}
		return null;
	}
	
	/**
	 * Getter for a User with the given nickname
	 * @param nickname	Nickname that is being checked
	 * @return User if a user exists with the given nickname, else null
	 */
	public User getUserByNickname(String nickname) {
		for (User user: users) {
			if (user.getNickName().equals(nickname)) {
				return user;
			}
		}
		return null;
	}
	
	/**
	 * Method for logging in
	 * @param nickname		Nickname of the user that tries to log in
	 * @param password		Password of the user that tries to log in
	 * @return Token if nickname and password combo exists, else null
	 */
	public Token login(String nickname, String password) {
		for (User user: users) {
			if (user.getNickName().equals(nickname) && user.getPassword().equals(password)) {
				return user.getToken();
			}
		}
		return null;
	}
	
	/**
	 * Method for adding a user
	 * @param firstName		First name of the user
	 * @param insert		Insert of the user
	 * @param lastName		Last name of the user
	 * @param nickName		Nickname of the user
	 * @param password		Password of the user
	 * @return Token if user is created successfuly, else null
	 */
	public Token addUser(String firstName, String insert, String lastName, String nickName ,String password) {
		firstName = firstName.replaceAll("\\s+","");lastName = lastName.replaceAll("\\s+","");nickName=nickName.replaceAll("\\s+","");password=password.replaceAll("\\s+","");
		if (firstName.isEmpty() || lastName.isEmpty() || nickName.isEmpty() || password.isEmpty()) {
			return null;
		}
		for (User user : users) {
			if (user.getNickName().equals(nickName)) {
				return null;
			}
		}
		User user = new User(firstName, insert, lastName, nickName, password);
		users.add(user);
		return user.getToken();
	}
	
	/**
	 * Method for checking if the token is a correct token
	 * @param token		token that is being checked
	 * @return true if token is correct, else false
	 */
	public boolean isUser(String token){
		for(User user : users){
			if(user.getToken().getToken().equals(token)){
				return true;
			}
		}
		return false;
	}
	
//	Methods for ratings ----------------------------------------------------------------------------------------------------------
	
	/**
	 * Method for adding a rating to the movie with the given imdbid
	 * @param imdbId	Imdb ID of the movie were the rating has to be added
	 * @param score		Score of the rating
	 * @param token		Token of the user that adds the rating
	 * @return true if the rating has been added successfuly, else false
	 */
	public boolean addRating(String imdbId, double score, String token){
		Movie movie = getMovie(imdbId);
		User user = getUserByToken(token);
		if(movie != null && user != null){
			return movie.addRating(new Rating(score, movie.getId()), user);
		}
		return false;
	}
	
	/**
	 * Method for deleting a rating of a movie
	 * @param imdbId	Imdb ID of the movie were the rating has to be deleted
	 * @param token		Token of the user that deletes the rating
	 * @return true if the rating has been deleted, else false
	 */
	public boolean deleteRating(String imdbId, String token) {
		Movie movie = getMovie(imdbId);
		User user = getUserByToken(token);
		if(movie != null && user != null){
			return movie.deleteRating(user);
		}
		return false;
	}

	/**
	 * Method for changing a rating of a movie
	 * @param token		Token of the user that changes the rating
	 * @param imdb		Imdb id of the movie were the rating has to be changed
	 * @param rating	The value of the new rating
	 * @return true if rating has been changed, else false
	 */
	public boolean changeRating(String token, String imdb, double rating) {
		Movie movie = getMovie(imdb);
		User user = getUserByToken(token);
		if (movie != null && user != null) {
			return movie.changeRating(new Rating(rating, movie.getId()), user);
		}
		return false;
	}

}
