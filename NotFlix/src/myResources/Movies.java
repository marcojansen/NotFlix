package myResources;

import java.util.Set;

import javax.servlet.ServletContext;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import model.Model;
import model.Movie;

@Path("/movies")
public class Movies {
	
	@Context ServletContext context;
	
	@GET
	@Produces({MediaType.APPLICATION_XML})
	public Set<Movie> getMovies() {
		Model model = (Model) context.getAttribute("Model");
		if(model != null){
			return model.getMovies();
		}
		return null;
	}

}