package timetable.entities;

import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Indexed;
import org.hibernate.search.annotations.TermVector;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Indexed
@Table(name="event", schema = "test", catalog = "" )
public class Event implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

    @Column(name = "number")
    @Field(termVector = TermVector.YES)
    String number;

    @Column(name = "description")
    String description;

    @Column(name = "date")
    @Temporal(TemporalType.DATE)
    Date date;

    private int  idStatus;

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


    public Event() {
    }

    public Event(int id, String number, String description, Date date, int idHall,int estatus)   {
        this.id = id;
        this.number = number;
        this.description = description;
        this.date = date;
        this.idHall = idHall;
        this.idStatus = estatus;

    }

    public int getIdStatus() {
        return idStatus;
    }

    public void setIdStatus(int idStatus) {
        this.idStatus = idStatus;
    }
}
