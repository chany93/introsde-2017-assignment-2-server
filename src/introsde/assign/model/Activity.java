package introsde.assign.model;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

import introsde.assign.dao.PersonActivityDao;


@Entity
@Table(name="Activity")
@NamedQuery(name="Activity.findAll", query="SELECT a FROM Activity a")
@XmlType(propOrder = {"idActivity", "name", "description", "place", "type", "startdate"})
@XmlRootElement(name="activity")
public class Activity implements Serializable {


	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO) 
	@Column(name="idActivity")
	private int idActivity;
	
	@Column(name="name")
	private String name;
	
	@Column(name="description")
	private String description;
	
	@Column(name="place")
	private String place;
	
	@ManyToOne
	@JoinColumn(name="idPerson",referencedColumnName="idPerson")
	private Person person;
	
	@Column(name="type")
	private ActivityType type;
	
	@Column(name="startdate")
	private String startdate;
	
	public Activity() {
	
	}
	
	public Activity(String name, String description, String place, ActivityType type, String startdate) {
		super();
		this.name = name;
		this.description = description;
		this.place = place;
		this.type = type;
		this.startdate = startdate;
	}
	 
	 
	public int getIdActivity() {
		return idActivity;
	}

	public void setIdActivity(int idActivity) {
		this.idActivity = idActivity;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getPlace() {
		return place;
	}

	public void setPlace(String place) {
		this.place = place;
	}
	
	@XmlTransient
	public Person getPerson() {
	    return person;
	}

	public void setPerson(Person person) {
		this.person = person;
	}
	
	public ActivityType getType() {
		return type;
	}
	
	public void setType(ActivityType type) {
		this.type = type;
	}
	
	public String getStartdate() {
		return startdate;
	}

	public void setStartdate(String startdate) {
		this.startdate = startdate;
	}
	
	public static Activity getActivityById(int activityId) {
        EntityManager em = PersonActivityDao.instance.createEntityManager();
        Activity a = em.find(Activity.class, activityId);
        PersonActivityDao.instance.closeConnections(em);
        return a;
    }

    public static List<Activity> getAll() {
        EntityManager em = PersonActivityDao.instance.createEntityManager();
        List<Activity> list = em.createNamedQuery("Activity.findAll", Activity.class)
            .getResultList();
        PersonActivityDao.instance.closeConnections(em);
        return list;
    }

    public static Activity saveActivity(Activity a) {
        EntityManager em = PersonActivityDao.instance.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        em.persist(a);
        tx.commit();
        PersonActivityDao.instance.closeConnections(em);
        return a;
    } 

    public static Activity updateActivity(Activity a) {
        EntityManager em = PersonActivityDao.instance.createEntityManager(); 
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        a=em.merge(a);
        tx.commit();
        PersonActivityDao.instance.closeConnections(em);
        return a;
    }

    public static void removeActivity(Activity a) {
        EntityManager em = PersonActivityDao.instance.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        a=em.merge(a);
        em.remove(a);
        tx.commit();
        PersonActivityDao.instance.closeConnections(em);
    }
    
    public static List<Activity> getRangedActivityByPersonId(int idPerson,String type, String before, String after) throws ParseException{
        SimpleDateFormat sDF = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.S");
        Date beforeDate = sDF.parse(before);
        Date afterDate = sDF.parse(after);
        
        List<Activity> personActivities = new ArrayList<>();
        for(Activity activity : (List<Activity>) Person.getPersonById(idPerson).getActivity()) {
			System.out.println("loop----");
			if(activity.getType().toString().equals(type)) {
				System.out.println("if");
				personActivities.add(activity);
			}
        }
        
        List<Activity> ret = new ArrayList<>();
        
        for(Activity a : personActivities) {
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