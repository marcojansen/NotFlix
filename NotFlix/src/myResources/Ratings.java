package myResources;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;
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
import model.RatedMovie;

/**
 * Path /ratings, Documented in Documentatie.pdf
 */
@Path("/ratings")
public class Ratings {
	@Context ServletContext context;
	
	@POST
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public void addRating(@FormParam("imdb") String imdb, @FormParam("rating") double rating, @HeaderParam("Token") String token, @Context final HttpServletResponse response) {
		Model model = (Model) context.getAttribute("Model");
		System.out.println(imdb + rating + token);
		if(!model.isUser(token) || rating < 1 || rating > 10){
			response.setStatus(HttpServletResponse.SC_FORBIDDEN);
			try {
				response.flushBuffer();
				return;
			} catch (IOException e) {}
		}
		boolean added = model.addRating(imdb, rating, token);
		if(added){
			response.setStatus(HttpServletResponse.SC_CREATED);
			try {
				response.flushBuffer();
				return;
			} catch (IOException e) {}
		}else{
			response.setStatus(HttpServletResponse.SC_NOT_ACCEPTABLE);
			try {
				response.flushBuffer();
				return;
			} catch (IOException e) {}
		}
	}
	
	@PUT
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public void changeRating(@FormParam("imdb") String imdb, @FormParam("rating") double rating, @HeaderParam("Token") String token, @Context final HttpServletResponse response) {
		Model model = (Model) context.getAttribute("Model");
		if(!model.isUser(token) || rating < 1 || rating > 10){
			response.setStatus(HttpServletResponse.SC_FORBIDDEN);
			try {
				response.flushBuffer();
				return;
			} catch (IOException e) {}
		}
		boolean changed = model.changeRating(token, imdb, rating);
		if(changed){
			response.setStatus(HttpServletResponse.SC_ACCEPTED);
			try {
				response.flushBuffer();
				return;
			} catch (IOException e) {}
		}
		response.setStatus(HttpServletResponse.SC_NOT_MODIFIED);
		try {
			response.flushBuffer();
			return;
		} catch (IOException e) {}
	}
	
	@DELETE
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public void deleteRating(@FormParam("imdb") String imdb, @HeaderParam("Token") String token, @Context final HttpServletResponse response) {
		Model model = (Model) context.getAttribute("Model");
		if(!model.isUser(token)){
			response.setStatus(HttpServletResponse.SC_FORBIDDEN);
			try {
				response.flushBuffer();
				return;
			} catch (IOException e) {}
		}
		boolean deleted = model.deleteRating(imdb, token);
		if(deleted){
			response.setStatus(HttpServletResponse.SC_ACCEPTED);
			try {
				response.flushBuffer();
				return;
			} catch (IOException e) {}
		}else{
			response.setStatus(HttpServletResponse.SC_NOT_ACCEPTABLE);
			try {
				response.flushBuffer();
				return;
			} catch (IOException e) {}
		}
	}
	
	@GET
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	public ArrayList<RatedMovie> getMyRatedMovies(@HeaderParam("Token") String token, @Context final HttpServletResponse response) {
		
		Model model = (Model) context.getAttribute("Model");
		if (!model.isUser(token)) {
			response.setStatus(HttpServletResponse.SC_FORBIDDEN);
			try {
				response.flushBuffer();
				return null;
			} catch (IOException e) {}
		}
		return model.getMyRatedMovies(token);
	}
	
	@GET
	@Path("/unrated")
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	public ArrayList<Movie> getMyUnratedMovies(@HeaderParam("Token") String token, @Context final HttpServletResponse response) {
		Model model = (Model) context.getAttribute("Model");
		if (!model.isUser(token)) {
			response.setStatus(HttpServletResponse.SC_FORBIDDEN);
			try {
				response.flushBuffer();
			} catch (IOException e) {}
			return null;
		}
		return model.getMyUnratedMovies(token);
	}
	
	
	

}
