package myResources;

import java.util.ArrayList;

import javax.servlet.ServletContext;
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

@Path("/users")
public class Users {
	@Context ServletContext context;
	
	@POST
	@Produces({ MediaType.APPLICATION_XML , MediaType.APPLICATION_JSON})
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public Token createUser(@FormParam("firstname")String firstname, @FormParam("insert") String insert
			, @FormParam("lastname") String lastname, @FormParam("nickname") String nickname
			,@FormParam("password") String password) {
		Model model = (Model) context.getAttribute("Model");
		return model.addUser(firstname, insert, lastname, nickname, password);
	}
	
	@POST
	@Path("/login")
	@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public Token Login(@FormParam("nickname") String nickname, @FormParam("password") String password) {
		Model model = (Model) context.getAttribute("Model");
		return model.login(nickname, password);
	}
	
	@GET
	@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	public ArrayList<User> getUsers(@HeaderParam("Token") String token) {
		Model model = (Model) context.getAttribute("Model");
		return model.getUsers(token);
	}
	
	@GET
	@Path("{nickname}")
	@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	public User getUser(@HeaderParam("Token") String token, @PathParam("nickname") String nickname) {
		Model model = (Model) context.getAttribute("Model");
		return model.getUser(nickname, token);
	}

}
