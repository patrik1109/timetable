package timetable.thymeleaf_form;

import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

public class EventForm {

    public String number;

    public String description;

    public String composition;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    public Date date;

    public int Hall_number;
    public int estatus;




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

    public String getComposition() {
        return composition;
    }

    public void setComposition(String composition) {
        this.composition = composition;
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

    public int getEstatus() {
        return estatus;
    }

    public void setEstatus(int estatus) {
        this.estatus = estatus;
    }

}
