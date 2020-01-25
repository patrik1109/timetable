package timetable.responses;

import timetable.enums.EventStatus;

import javax.persistence.Column;
import java.util.Date;

public class EventResponse {

    public int id;
    public String number;
    public String description;
    public Date date;
    public int idHall;
    public EventStatus status;

    public EventStatus getStatus() {
        return status;
    }

    public void setStatus(EventStatus status) {
        this.status = status;
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



    public EventResponse() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public EventResponse(String number, String description, Date date, int idHall, int id ,EventStatus status) {
        this.number = number;
        this.description = description;
        this.date = date;
        this.idHall = idHall;
        this.id = id;
        this.status = status;

    }
}
