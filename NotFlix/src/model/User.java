package model;


import java.util.ArrayList;
import java.util.Random;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * Class for an user.
 */
@XmlRootElement
public class User {

	private String firstName, insert, lastName, nickName, password;
	private Token token;
	

	/**
	 * Empty constructor for Jaxb
	 */
	public User() {}

	/**
	 * Constructor to create an user.
	 * @param firstName		Firstname of the user
	 * @param insert		Insert of the user ( can be null if not present )
	 * @param lastName		Lastname of the user
	 * @param nickName		Nickname for the user	( used to login )
	 * @param password		Password for the user	( used to login )
	 */
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
}
