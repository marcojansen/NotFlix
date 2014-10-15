package myResources;

import java.util.ArrayList;

import javax.servlet.ServletContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import model.Model;
import model.Movie;

@Path("/ratings")
public class Ratings {
	@Context ServletContext context;
	
	@POST
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public void addRating(@FormParam("imdb") String imdb, @FormParam("rating") double rating, @HeaderParam("Token") String token) {
		Model model = (Model) context.getAttribute("Model");
		//TODO model.addRating(token,imdb,rating);
	}
	
	@PUT
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public void putRating(@FormParam("imdb") String imdb, @FormParam("rating") double rating, @HeaderParam("Token") String token) {
		Model model = (Model) context.getAttribute("Model");
		//TODO model.putRating(token,imdb,rating);
	}
	
	@DELETE
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public void deleteRating(@FormParam("imdb") String imdb, @HeaderParam("Token") String token) {
		Model model = (Model) context.getAttribute("Model");
		//TODO model.deleteRating(token,imdb);
	}
	
	@GET
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	public ArrayList<Movie> getMovies(@HeaderParam("Token") String token) {
		Model model = (Model) context.getAttribute("Model");
		return null;
		//TODO return model.getMyRatedMovies(token);
	}
	
	
	
	

}
