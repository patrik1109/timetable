package timetable.entities;

import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Indexed;
import org.hibernate.search.annotations.TermVector;
import org.springframework.format.annotation.DateTimeFormat;

import timetable.enums.EventStatus;

import javax.persistence.*;
import java.util.Date;

@Entity
@Indexed
@Table(name="event", schema = "test", catalog = "" )
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

    @Column(name = "number")
    @Field(termVector = TermVector.YES)
    String number;

    @Column(name = "description")
    String description;

    //@DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "date")
    @Temporal(TemporalType.DATE)
    Date date;

    @Column(name = "status")
    EventStatus status;
    
    @Column(name = "color")
    String color;
    
	private int idHall;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getIdHall() {
        return idHall;
    }

    public void setIdHall(int idHall) {
        this.idHall = idHall;
    }
    public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}
	
    public Event() {
    }

    public Event(int id, String number, String description, Date date, int idHall, EventStatus estatus, String color) {
        this.id = id;
        this.number = number;
        this.description = description;
        this.date = date;
        this.idHall = idHall;
        this.status = estatus;
        this.color = color;
    }

	public void setStatus(EventStatus getestatus) {
		this.status = getestatus;
		
	}

	public EventStatus getStatus() {
		return status;
	}
	
	
}
