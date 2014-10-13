package model;

public class Rating {
	
	private int score;
	private int movieID;
	
	public Rating() {}
	
	public Rating(int score, int movieID) {
		this.score = score;
		this.movieID = movieID;
	}

	public int getScore() {
		return score;
	}
	
	public int getMovieID() {
		return movieID;
	}
	
}
