package model;

import java.util.Random;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Token {
	String token;
	
	public Token() {
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
	
	public void setToken(String token) {
		this.token = token;
	}

}
