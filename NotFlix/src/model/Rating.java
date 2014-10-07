package model;

public class Rating {
	
	private double score;
	private int movieID;
	
	public Rating() {}
	
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
