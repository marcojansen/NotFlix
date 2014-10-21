package myResources;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import model.Model;
import model.Token;
import model.User;

/**
 * Path /users, Documented in Documentatie.pdf
 */
@Path("/users")
public class Users {
	@Context ServletContext context;
	
	@POST
	@Produces({ MediaType.APPLICATION_XML , MediaType.APPLICATION_JSON})
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public Token createUser(@FormParam("firstname")String firstname, @FormParam("insert") String insert
			, @FormParam("lastname") String lastname, @FormParam("nickname") String nickname
			,@FormParam("password") String password, @Context final HttpServletResponse response) {
		Model model = (Model) context.getAttribute("Model");
		Token token = model.addUser(firstname, insert, lastname, nickname, password);
		if(token == null){
			response.setStatus(HttpServletResponse.SC_NOT_ACCEPTABLE);
		}else{
			response.setStatus(HttpServletResponse.SC_CREATED);
		}
		try {
			response.flushBuffer();
		} catch (IOException e) {}
		return token;
	}
	
	@POST
	@Path("/login")
	@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public Token Login(@FormParam("nickname") String nickname, @FormParam("password") String password, @Context final HttpServletResponse response) {
		Model model = (Model) context.getAttribute("Model");
		Token token = model.login(nickname, password);
		if(token == null){
			response.setStatus(HttpServletResponse.SC_FORBIDDEN);
			try {
				response.flushBuffer();
			} catch (IOException e) {}
		}
		return token;
	}
	
	@GET
	@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	public ArrayList<User> getUsers(@HeaderParam("token") String token, @Context final HttpServletResponse response) {
		Model model = (Model) context.getAttribute("Model");
		if(!model.isUser(token)){
			response.setStatus(HttpServletResponse.SC_FORBIDDEN);
			try {
				response.flushBuffer();
			} catch (IOException e) {}
		}
		return model.getUsers();
	}
	
	@GET
	@Path("{nickname}")
	@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	public User getUser(@HeaderParam("Token") String token, @PathParam("nickname") String nickname, @Context final HttpServletResponse response) {
		Model model = (Model) context.getAttribute("Model");
		User user = model.getUserByNickname(nickname);
		if(!model.isUser(token)){
			response.setStatus(HttpServletResponse.SC_FORBIDDEN);
			try {
				response.flushBuffer();
			} catch (IOException e) {}
			return null;
		}
		if(user == null){
			response.setStatus(HttpServletResponse.SC_NOT_ACCEPTABLE);
			try {
				response.flushBuffer();
			} catch (IOException e) {}
		}
		return user;
	}

}
