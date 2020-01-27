package timetable.entities;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Entity
@Table(name="hall", schema = "test22", catalog = "" )
public class Hall {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

    @Column(name = "name")
    String name;

    @Column(name = "date")
    Date date;

    @OneToMany(fetch= FetchType.EAGER, mappedBy = "idHall")
    Set<Event> eventSet;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Set<Event> getEventSet() {
        return eventSet;
    }

    public void setEventSet(Set<Event> eventSet) {
        this.eventSet = eventSet;
    }

    public Hall() {
    }

    public Hall(int id, String name, Date date, Set<Event> eventSet) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.eventSet = eventSet;
    }
}
