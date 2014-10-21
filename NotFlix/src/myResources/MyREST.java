package myResources;

import javax.ws.rs.ApplicationPath;

import org.glassfish.jersey.server.ResourceConfig;

/**
 * Resource configuration
 */
@ApplicationPath("resources")
public class MyREST extends ResourceConfig {
	
	/**
	 * Prints all the classes that are in the resources.
	 */
	public MyREST() {
		packages("myResources");
		for (Class c : getClasses()) {
			System.out.println(c.getName());
		}
	}

}
