package timetable.entities;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Entity
@Table(name="hall", schema = "public", catalog = "" )
public class Hall {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

    @Column(name = "name")
    String name;

    @Column(name = "date")
    Date date;

    @OneToMany(fetch= FetchType.EAGER)
    @JoinColumn(name = "idhall")
    Set<Event> eventSet;

    @Column(name="hiddencolloms")
    String hiddencolloms;

    public Hall(int id, String name, Date date, Set<Event> eventSet, String hiddencolloms) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.eventSet = eventSet;
        this.hiddencolloms = hiddencolloms;
    }

    public String getHiddencolloms() {
        return hiddencolloms;
    }

    public void setHiddencolloms(String hiddencolloms) {
        this.hiddencolloms = hiddencolloms;
    }

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


}
