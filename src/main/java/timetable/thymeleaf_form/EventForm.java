package timetable.thymeleaf_form;

import org.springframework.format.annotation.DateTimeFormat;
import timetable.enums.EventStatus;

import java.util.Date;

public class EventForm {

    String Number;
    String Description;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    Date date;
    int Hall_number;
    EventStatus estatus;
    String color;

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public EventStatus getestatus() {
        return estatus;
    }

    public void setestatus(EventStatus status) {
        this.estatus = status;
    }

    public EventForm(String number, String description, Date date, int hall_number, EventStatus status, String color) {
        this.Number = number;
        this.Description = description;
        this.date = date;
        this.Hall_number = hall_number;
        this.estatus = status;
        this.color = color;
    }

    public EventForm() {
    }

    public String getNumber() {
        return Number;
    }

    public void setNumber(String number) {
        Number = number;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getHall_number() {
        return Hall_number;
    }

    public void setHall_number(int hall_number) {
        Hall_number = hall_number;
    }
}
