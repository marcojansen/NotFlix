package myResources;

import javax.ws.rs.ApplicationPath;

import org.glassfish.jersey.server.ResourceConfig;

@ApplicationPath("resources")
public class MyREST extends ResourceConfig {
	
	public MyREST() {
		packages("myResources");
		for (Class c : getClasses()) {
			System.out.println(c.getName());
		}
	}

}
