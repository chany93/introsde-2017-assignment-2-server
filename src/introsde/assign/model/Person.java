package introsde.assign.model;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

import introsde.assign.dao.PersonActivityDao;

@Entity
@Table(name="Person")
@NamedQuery(name="Person.findAll", query="SELECT p FROM Person p")
@XmlType(propOrder = {"idPerson", "firstname", "lastname", "birthdate", "activity"})
@XmlRootElement(name="person")
@XmlAccessorType(XmlAccessType.FIELD)
public class Person implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

    @Id // defines this attributed as the one that identifies the entity
    @GeneratedValue(strategy=GenerationType.AUTO) 
    @Column(name="idPerson") // maps the following attribute to a column
    private int idPerson;
    
    @Column(name="firstname")
    private String firstname;
    
    @Column(name="lastname")
    private String lastname;
     
    @Column(name="birthdate")
    private String birthdate;
    
    @OneToMany(mappedBy="person",cascade=CascadeType.ALL,fetch=FetchType.EAGER)
    @XmlElementWrapper(name="preferences")
    private List<Activity> activity;
    
    public Person() {
	
    }
	

	public Person(String firstname, String lastname, String birthdate, List<Activity> activity) {
		super();
		this.firstname = firstname;
		this.lastname = lastname;
		this.birthdate = birthdate;
		this.activity = activity;
	}

	public int getIdPerson() {
		return idPerson;
	}

	public void setIdPerson(int idPerson) {
		this.idPerson = idPerson;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getBirthdate() {
		return birthdate;
	}

	public void setBirthdate(String birthdate) {
		this.birthdate = birthdate;
	}
	
	@XmlTransient
	public List<Activity> getActivity() {
	    return activity;
	}
	
	public void setActivity(List<Activity> activity) {
		this.activity = activity;
	}
	
    
	public static Person getPersonById(int personId) {
        EntityManager em = PersonActivityDao.instance.createEntityManager();
        Person p = em.find(Person.class, personId);
        PersonActivityDao.instance.closeConnections(em);
        return p;
    }

    public static List<Person> getAll() {
        EntityManager em = PersonActivityDao.instance.createEntityManager();
        List<Person> list = em.createNamedQuery("Person.findAll", Person.class)
            .getResultList();
        PersonActivityDao.instance.closeConnections(em);
        return list;
    }

    public static Person savePerson(Person p) {
        EntityManager em = PersonActivityDao.instance.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        em.persist(p);
        tx.commit();
        PersonActivityDao.instance.closeConnections(em);
        return p;
    } 

    public static Person updatePerson(Person p) {
        EntityManager em = PersonActivityDao.instance.createEntityManager(); 
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        p=em.merge(p);
        tx.commit();
        PersonActivityDao.instance.closeConnections(em);
        return p;
    }

    public static void removePerson(Person p) {
        EntityManager em = PersonActivityDao.instance.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        p=em.merge(p);
        em.remove(p);
        tx.commit();
        PersonActivityDao.instance.closeConnections(em);
    }
    
    public List<Activity> getActivitiesByTypeAndId(ActivityType type, int activityId) {
		List<Activity> activities = getActivity();
	    List<Activity> activitiesByTypeAndId = new ArrayList<>();
	    
	    for (Activity activity : activities) {
	    	if (activity.getType().equals(type) && activity.getIdActivity()==activityId) {
	    		activitiesByTypeAndId.add(activity);
	    	}
	    }
	    return activitiesByTypeAndId;
	}
    
    public List<Activity> getActivitiesByType(String type) {
	    List<Activity> activities = getActivity();
	    List<Activity> activitiesWithType = new ArrayList<>();
	    
	    for (Activity activity : activities) {
		    	if (activity.getType().toString().equals(type)) {
		    		activitiesWithType.add(activity);
		    	}
	    }
	    return activitiesWithType;
	}
    	
    public List<Activity> getActivitiesWithinRange(String type, String before, String after) throws ParseException {
    	
    		SimpleDateFormat sDF = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.S");
        Date beforeDate = sDF.parse(before);
        Date afterDate = sDF.parse(after);
        
		List<Activity> activities = this.getActivitiesByType(type);
		System.out.println(activities);
		
		 List<Activity> ret = new ArrayList<>();
	        
	        for(Activity a : activities) {
	          Date tmpDate = sDF.parse(a.getStartdate());
	          System.out.println("here");
	          if(tmpDate.before(afterDate) && tmpDate.after(beforeDate)) {
	        	  	System.out.println("here if");
	            ret.add(a);
	          }
	        }
		return ret; 	

    }
}