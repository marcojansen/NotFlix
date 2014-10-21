package model;

import java.util.Random;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * Class that generates a token
 * An user has a token.
 */
@XmlRootElement
public class Token {
	String token;
	
	/**
	 * Constructor, creates a token.
	 */
	public Token() {
		token = generateToken();
	}
	
	/**
	 * Method to generate a token.
	 * @return	Random string of 24 characters with Capitals,letters and numbers.
	 */
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
	
	public void setToken(String token) {
		this.token = token;
	}

}
