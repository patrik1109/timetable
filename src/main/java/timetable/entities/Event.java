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
@Table(name="event", schema = "test22", catalog = "" )
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

    @Column(name = "number")
    @Field(termVector = TermVector.YES)
    String number;

    @Column(name = "description")
    String description;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "date")
    Date date;

    @Column(name = "status")
    EventStatus status;

    private int idHall;

    public EventStatus getStatus() {
        return status;
    }

    public void setStatus(EventStatus status) {
        this.status = status;
    }

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

    public Event(int id, String number, String description, Date date, int idHall, EventStatus estatus) {
        this.id = id;
        this.number = number;
        this.description = description;
        this.date = date;
        this.idHall = idHall;
        this.status = estatus;
    }
}
