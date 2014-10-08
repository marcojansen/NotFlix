package myResources;

import java.util.ArrayList;
import java.util.Set;

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
	@Produces({ MediaType.APPLICATION_XML })
	public ArrayList<Movie> getMovies() {
		Model model = (Model) context.getAttribute("Model");
		
		if(model != null){
			return model.getMovies();
		}
		return null;
	}

	@GET
	@Path("{id}")
	@Produces({MediaType.APPLICATION_XML})
	public Movie getMovie(@PathParam("id") String id) {
		Model model = (Model) context.getAttribute("Model");
		for (Movie movie : model.getMovies()) {
			if (movie.getImdb().equals(id)) {
				return movie;
			}
		}
		return null;
	}

}
