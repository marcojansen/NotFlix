package model;

/**
 * Class for a Rating.
 * Rating can be added to an movie. 
 * Contains a movieId and a score.
 *
 */
public class Rating {
	
	private double score;
	private int movieID;
	
	/**
	 * Empty constructor for Jaxb
	 */
	public Rating() {}
	
	/**
	 * Constructor to make a rating.
	 * @param score			The score you give.
	 * @param movieID		The movie id of the movie you're willing to rate.
	 */
	public Rating(double score, int movieID) {
		this.score = score;
		this.movieID = movieID;
	}

	public double getScore() {
		return score;
	}
	
	public int getMovieID() {
		return movieID;
	}
	
}
