package model;


import java.util.ArrayList;
import java.util.Random;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class User {

	private String firstName, insert, lastName, nickName, password,token;
	private ArrayList<Rating> ratings = new ArrayList<Rating>();

	public User() {
	}

	public User(String firstName, String insert, String lastName,
			String nickName, String password) {
		this.firstName = firstName;
		this.insert = insert;
		this.lastName = lastName;
		this.nickName = nickName;
		this.password = password;

		token = generateToken();

	}

	public String generateToken() {
		Random rng = new Random();
		String characters = "1234567890abcdefhijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
		int length = 24;
		char[] text = new char[length];
		for (int i = 0; i < length; i++) {
			text[i] = characters.charAt(rng.nextInt(characters.length()));
		}
		return new String(text);
	}

	public String getToken() {
		return token;
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

	public void setToken(String token) {
		this.token = token;
	}

	public String getNickName() {
		return nickName;
	}

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
