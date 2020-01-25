package timetable.responses;

import timetable.entities.Event;
import timetable.enums.EventStatus;

import java.util.Date;
import java.util.Set;

public class HallResponse{

    public int id;
    public String name;
    public Date date;
    public Set<Event> eventSet;
    public EventStatus status;


    public HallResponse() {
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

    public HallResponse(int id, String name, Date date, Set<Event> booksAuthor, EventStatus status) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.eventSet = booksAuthor;
        this.status = status;
    }
}
