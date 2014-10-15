package myResources;

import java.util.ArrayList;

import javax.servlet.ServletContext;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

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
	public ArrayList<Movie> getRatedMovies() {
		Model model = (Model) context.getAttribute("Model");
		return model.getRatedMovies();
	}

}
