package introsde.assign.resources;

import java.net.URI;
import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.UriInfo;

import introsde.assign.DBInit;
import introsde.assign.model.Person;

@Path("/database_init")
public class DBResource {
	
		@Context
		UriInfo uriInfo;
		@Context
		Request request;

		//Request#0
		@GET
		@Produces({MediaType.TEXT_XML, MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
		public List<Person> getPersonsList() {
			
			DBInit db = new DBInit();
			List<Person> people = db.initDB();
			System.out.println(" --> Initialize DB with 5 people");
			return people;
		}
		
		

	}
