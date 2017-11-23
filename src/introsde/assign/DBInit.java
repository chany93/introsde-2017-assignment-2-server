package introsde.assign;

import java.util.ArrayList;
import java.util.List;

import introsde.assign.model.Activity;
import introsde.assign.model.ActivityType;
import introsde.assign.model.Person;

public class DBInit {

	public static List<Activity> createActivities(){
		List<Activity> activities = new ArrayList<Activity>();
		Activity a1 = new Activity("Swimming", "Swimming in the lake", "Lake Tovel", ActivityType.Sport, "2013-10-30T00:00:00.0");
		activities.add(a1);
		
		Activity a2 = new Activity("Running", "Running in the forest", "Trento", ActivityType.Sport, "2000-11-02T00:00:00.0");
		activities.add(a2);
		
		Activity a3 = new Activity("Watch movies", "Whatch Harry Potter", "Milan", ActivityType.Entertainment, "1999-09-26T00:00:00.0");
		activities.add(a3);
		
		Activity a4 = new Activity("Party", "Organizing parties", "Home", ActivityType.Social, "2005-02-30T00:00:00.0");
		activities.add(a4);
		
		Activity a5 = new Activity("Visiting", "Visiting a museum", "London", ActivityType.Culture, "2007-12-30T00:00:00.0");
		activities.add(a5);
		
		Activity a6 = new Activity("Football", "Goalkeeper in local team", "Napoli", ActivityType.Sport, "2001-05-12T00:00:00.0");
		activities.add(a6);
		
		Activity a7 = new Activity("Videogames", "Playing videogames", "Home", ActivityType.Entertainment, "2003-06-04T00:00:00.0");
		activities.add(a7);
		
		Activity a8 = new Activity("Learning a language", "Studying French", "School", ActivityType.Culture, "2015-04-17T00:00:00.0");
		activities.add(a8);
		
		Activity a9 = new Activity("Chat", "Chatting online", "Web", ActivityType.Social, "2000-07-29T00:00:00.0");
		activities.add(a9);
		
		Activity a10 = new Activity("Cycling", "Cycling at home", "Home", ActivityType.Sport, "2017-11-02T00:00:00.0");
		activities.add(a10);
		
		return activities;
	}
	
	public static List<Person> createPeople(){
		List<Person> people = new ArrayList<>();
		List<Activity> activityList = createActivities();
		Person p1 = new Person("Mario", "Rossi", "1990-12-29", activityList.subList(0, 1));
		people.add(p1);
		activityList.get(0).setPerson(p1);
		
		Person p2 = new Person("Paolo", "Bianchi", "1994-02-10", activityList.subList(1, 4));
		people.add(p2);
		activityList.get(1).setPerson(p2);
		activityList.get(2).setPerson(p2);
		activityList.get(3).setPerson(p2);
		
		Person p3 = new Person("Roberto", "Negri", "1989-11-02", activityList.subList(4, 6));
		people.add(p3);
		activityList.get(4).setPerson(p3);
		activityList.get(5).setPerson(p3);

		Person p4 = new Person("Giovanni", "Verdi", "1979-07-14", activityList.subList(6, 7));
		people.add(p4);
		activityList.get(6).setPerson(p4);

		Person p5 = new Person("Damiano", "Guerra", "1992-05-19", activityList.subList(7, 10));
		people.add(p5);
		activityList.get(7).setPerson(p5);
		activityList.get(8).setPerson(p5);
		activityList.get(9).setPerson(p5);
		
		return people;
	}
	
	
	
	public static List<Person> initDB() {
		List<Person> people = new ArrayList<>();
		Person person;
		for (int i=0; i<5; i++) {
			person = Person.savePerson(createPeople().get(i));
			people.add(person);
		}
		return people;

		
	}
	
	public static void main(String[] args) {
		initDB();
	}
}
	