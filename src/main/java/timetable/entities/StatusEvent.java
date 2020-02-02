package timetable.entities;


import org.hibernate.search.annotations.Indexed;

import javax.persistence.*;
import java.util.Set;

@Entity
@Indexed
@Table(name="status", schema = "test22", catalog = "" )
public class StatusEvent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

    @Column(name = "status")
    String status;

    @Column(name = "color")
    String color;

    /*@OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "event_id", nullable = false)*/
   /* @OneToOne(mappedBy = "statusEvent")*/



    @OneToMany(fetch= FetchType.EAGER, mappedBy = "idStatus")
    Set<Event> eventSet;

    public StatusEvent() {
    }

    public StatusEvent(int id, String status, String color) {
        this.id = id;
        this.status = status;
        this.color = color;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }
}
