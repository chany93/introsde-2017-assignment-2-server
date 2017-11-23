package introsde.assign.model;


import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.fasterxml.jackson.annotation.JsonValue;

@XmlRootElement(name="activity_type")
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum ActivityType implements Serializable{
	Social("Social"),
	Sport("Sport"),
	Culture("Culture"),
	Entertainment("Entertainment"),
	Work("Work");
	
	private String type;
	
	ActivityType(String type){
		this.type = type;
	}
	
	@JsonValue
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	 @Override
	 public String toString() {
	    return type;
	 }
}


/*
import java.io.Serializable;
import java.util.List;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;

import introsde.assign.dao.PersonActivityDao;

@Entity
@Table(name="ActivityType")
@NamedQuery(name="ActivityType.findAll", query="SELECT t FROM ActivityType t")
@XmlRootElement(name="activity_type")
public class ActivityType implements Serializable {


	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO) 
	@Column(name="idType")
	private int idType;
	
	@Column(name="name")
	private String name;

	public int getIdType() {
		return idType;
	}

	public void setIdType(int idType) {
		this.idType = idType;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	  public static List<ActivityType> getAll() {
	        EntityManager em = PersonActivityDao.instance.createEntityManager();
	        List<ActivityType> list = em.createNamedQuery("ActivityType.findAll", ActivityType.class)
	            .getResultList();
	        PersonActivityDao.instance.closeConnections(em);
	        return list;
	    }
	  
	  public static ActivityType findByName(String name) {
			
			EntityManager em = PersonActivityDao.instance.createEntityManager();
	        Query query = em.createQuery("SELECT t FROM ActivityType t WHERE name=?1");
	        query.setParameter(1, name);
	        ActivityType type = (ActivityType)query.getSingleResult();
			return type;
		}

	@Override
	public String toString() {
		return name;
	}
	  
	  
	   
}


*/