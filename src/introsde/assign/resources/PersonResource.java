package introsde.assign.resources;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import introsde.assign.model.Activity;
import introsde.assign.model.ActivityType;
import introsde.assign.model.Person;

@Stateless // only used if the the application is deployed in a Java EE container
@LocalBean // only used if the the application is deployed in a Java EE container
public class PersonResource {
    @Context
    UriInfo uriInfo;
    @Context
    Request request;
    int id;

    EntityManager entityManager; // only used if the application is deployed in a Java EE container

    public PersonResource(UriInfo uriInfo, Request request,int id, EntityManager em) {
        this.uriInfo = uriInfo;
        this.request = request;
        this.id = id;
        this.entityManager = em;
    }

    public PersonResource(UriInfo uriInfo, Request request,int id) {
        this.uriInfo = uriInfo;
        this.request = request;
        this.id = id;
    }

    //Request#2
    @GET
    @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    public Person getPerson() {
        Person person = this.getPersonById(id);
        if (person == null)
            throw new RuntimeException("Get: Person with " + id + " not found");
        return person;
    }
	
    //Request#3
    @PUT
    @Consumes({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    public Response putPerson(Person person) {
        System.out.println("--> Updating Person... " +this.id);
        System.out.println("--> "+person.getFirstname());
        
        person.setActivity(getPersonById(this.id).getActivity());
        Response res;
        Person existing = getPersonById(this.id);

        if (existing == null) {
            res = Response.noContent().build();
        } else {
            res = Response.created(uriInfo.getAbsolutePath()).build();
            person.setIdPerson(this.id);
            Person.updatePerson(person);
        }

        return res;
    }
    
    //Request#4
    @POST
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Person newPerson(Person person) throws IOException {
    		Person newPerson = this.getPersonById(id);
        System.out.println("Creating new person...");            
        return Person.savePerson(person);
    }

    //Request#5: delete the person identified by {id} from the system
    @DELETE
    public void deletePerson() {
        Person c = getPersonById(id);
        if (c == null)
            throw new RuntimeException("Delete: Person with " + id
                    + " not found");
        Person.removePerson(c);
    }

    // for the browser
    @GET
    @Produces(MediaType.TEXT_XML)
    public Person getPersonHTML() {
        Person person = this.getPersonById(id);
        if (person == null)
            throw new RuntimeException("Get: Person with " + id + " not found");
        System.out.println("Returning person... " + person.getIdPerson());
        return person;
    }
    
    
    //Request#7 and Request#11
    @GET
    @Path("{activity_type}")
    @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    public List<Activity> getActivityTypesByPersonAndTypes(
        @PathParam("activity_type") String type,
        @QueryParam("before") String before, 
        @QueryParam("after") String after) {
    		Person person = Person.getPersonById(id);
    		
    		if(before != null && after != null) {
	        try {
	            System.out.println("Getting " + type + " activities from person id: " + id +""
	                + " from:" + before + " to:" + after);
	            return person.getActivitiesWithinRange(type, before, after);
	        } catch (ParseException e) {
		        System.out.println("something wrong with dates....");
		        e.printStackTrace();
		        return null;
	        }
    		}else {
    			
	        System.out.println("Getting " + type + " activities from person id: " + id);
	        List<Activity> personActivities = new ArrayList<>();
	        personActivities = person.getActivitiesByType(type);
	        System.out.println("heeey");
	        return personActivities;
    		}
        
    }
	
    //Request#8: return the activities of {activity_type} identified by {activity_id} for person identified by {id}
    @GET
    @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    @Path("{activity_type}/{activity_id}")
    public Activity getActivity(@PathParam("activity_type") String type, @PathParam("activity_id") int activityId ) {
    		Person person = this.getPersonById(id); 
    		System.out.println("getActivities()");
    	  
    	    		for(Activity activity : (List<Activity>) person.getActivity()) {
    	    			System.out.println("loop");
    	    			if(activity.getType().toString().equals(type) && activity.getIdActivity()==activityId) {
    	    				System.out.println("if");
    	    				return activity;
    	    			}
    	    		}
					return null;    	    
       
    }
    
    //Request#10
    @PUT
    @Consumes({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    @Path("{activity_type}/{activity_id}")
    public Response putType(ActivityType aType, @PathParam("activity_type") String type, @PathParam("activity_id") int activityId ) {
       
        Person person = Person.getPersonById(id);
        for(Activity activity : (List<Activity>) person.getActivity()) {
			System.out.println("loop");
			if(activity.getIdActivity()==activityId ) {
				activity.setType(aType);
				Activity.updateActivity(activity);
				System.out.println("if");
				
			}
        }
        
		Response res;
			Activity existing = Activity.getActivityById(activityId);
        if (existing == null) {
            res = Response.noContent().build();
        } else {
            res = Response.created(uriInfo.getAbsolutePath()).build();
            person.setIdPerson(this.id);
            Person.updatePerson(person);
        }

        return res;
        
        }	

    public Person getPersonById(int personId) {
        System.out.println("Reading person from DB with id: "+personId);

        // this will work within a Java EE container, where not DAO will be needed
        //Person person = entityManager.find(Person.class, personId); 

        Person person = Person.getPersonById(personId);
        System.out.println("Person: "+person.toString());
        return person;
    }
}