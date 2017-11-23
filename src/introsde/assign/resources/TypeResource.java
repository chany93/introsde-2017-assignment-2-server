package introsde.assign.resources;



import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.persistence.PersistenceUnit;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.UriInfo;


import introsde.assign.model.ActivityType;

@Stateless // will work only inside a Java EE application
@LocalBean // will work only inside a Java EE application
@Path("/activity_types")
public class TypeResource {
	 
		@Context
	    UriInfo uriInfo;
	    @Context
	    Request request;
	    String type;

	    //Request#6
	    @GET
		@Produces({MediaType.TEXT_XML, MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
		public ActivityType[] getActivityTypeList() {
			System.out.println("--> ActivityResource request...");
			System.out.println("--> URI = "+uriInfo);
			System.out.println("--> request = "+request);
			ActivityType[] types = ActivityType.values();
			
			return types;
		}
	    
}