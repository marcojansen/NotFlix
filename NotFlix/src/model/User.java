package model;

import java.util.ArrayList;

public class User {
	
	private String firstName, insert, lastName, nickName, password;
	private ArrayList<Rating> ratings = new ArrayList<Rating>();

	public User() {}
	
	public User(String firstName, String insert, String lastName, String nickName, String password) {
		this.firstName = firstName;
		this.insert = insert;
		this.lastName = lastName;
		this.nickName = nickName;
		this.password = password;
	}
	
	public String getFirstName() {
		return firstName;
	}
	
	public String getInsert() {
		return insert;
	}
	
	public String getLastName() {
		return lastName;
	}
	
	public String getNickName() {
		return nickName;
	}
	
	public String getPassword() {
		return password;
	}
	
	public double getRating(int movieId){
		for(Rating rating : ratings){
			if(rating.getMovieID() == movieId){
				return rating.getScore();
			}
		}
		return 0;
	}
	
}
