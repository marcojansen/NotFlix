package model;


import java.util.ArrayList;
import java.util.Random;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import com.fasterxml.jackson.annotation.JsonIgnore;

@XmlRootElement
public class User {

	private String firstName, insert, lastName, nickName, password;
	private ArrayList<Rating> ratings = new ArrayList<Rating>();
	private Token token;
	

	public User() {}

	public User(String firstName, String insert, String lastName,
			String nickName, String password) {
		this.firstName = firstName;
		if (insert == null) {
			insert = "";
		} else {
			this.insert = insert;			
		}
		this.lastName = lastName;
		this.nickName = nickName;
		this.password = password;
		token = new Token();
	}
	@XmlTransient
	@JsonIgnore
	public Token getToken() {
		return token;
	}
	
	public void setToken(Token token) {
		this.token = token;
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

	public ArrayList<Rating> getRatings() {
		return ratings;
	}

	public void setRatings(ArrayList<Rating> ratings) {
		this.ratings = ratings;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public void setInsert(String insert) {
		this.insert = insert;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public void setPassword(String password) {
		this.password = password;
	}


	public String getNickName() {
		return nickName;
	}

	@XmlTransient
	@JsonIgnore
	public String getPassword() {
		return password;
	}

	public double getRating(int movieId) {
		for (Rating rating : ratings) {
			if (rating.getMovieID() == movieId) {
				return rating.getScore();
			}
		}
		return 0;
	}

}
