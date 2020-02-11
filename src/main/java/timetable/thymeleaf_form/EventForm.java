package timetable.thymeleaf_form;

import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

public class EventForm {

    String number;
    String Description;
    String composition;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    Date date;
    int Hall_number;
    int estatus;

    public void setEstatus(int estatus) {
        this.estatus = estatus;
    }


	public int getEstatus() {
        return estatus;
    }

    public void setestatus(int status) {
        this.estatus = status;
    }

    public EventForm(String number, String description, Date date, int hall_number, int status,String composition) {
        this.number = number;
        this.Description = description;
        this.date = date;
        this.Hall_number = hall_number;
        this.estatus = status;
        this.composition = composition;
    }

    public EventForm() {
    }

    public String getComposition() {
        return composition;
    }

    public void setComposition(String composition) {
        this.composition = composition;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
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
