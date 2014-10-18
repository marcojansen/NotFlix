package myResources;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import org.apache.catalina.connector.Response;

import model.Model;
import model.Movie;

@Path("/movies")
public class Movies {
	
	@Context ServletContext context;
	
	@GET
	@Produces({ MediaType.APPLICATION_XML , MediaType.APPLICATION_JSON})
	public ArrayList<Movie> getMovies() {
		Model model = (Model) context.getAttribute("Model");
		return model.getMovies();
	}
	
	@GET
	@Path("/rated")
	@Produces({MediaType.APPLICATION_XML , MediaType.APPLICATION_JSON})
	public ArrayList<Movie> getRatedMovies(@HeaderParam("Token") String token, @Context final HttpServletResponse response) {
		Model model = (Model) context.getAttribute("Model");
		if(!model.isUser(token)){
			response.setStatus(HttpServletResponse.SC_FORBIDDEN);
			try {
				response.flushBuffer();
			} catch (IOException e) {}
			return null;
		}
		return model.getRatedMovies();
	}
	
	@GET
	@Path("/unrated")
	@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	public ArrayList<Movie> getUnratedMovies(@HeaderParam("Token")String token, @Context final HttpServletResponse response) {
		Model model = (Model) context.getAttribute("Model");
		if (!model.isUser(token)) {
			response.setStatus(HttpServletResponse.SC_FORBIDDEN);
			try {
				response.flushBuffer();
			} catch (IOException e) {}
			return null;
		}
		
		return model.getUnratedMovies();
	}

}
