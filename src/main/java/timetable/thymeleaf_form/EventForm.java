package timetable.thymeleaf_form;

import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

public class EventForm {

    public String number;
    public Boolean holdnumber;
    public String description;
    public Boolean holddescription;
    public String composition;
    public Boolean holdcomposition;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    public Date date;
    public Boolean holddate;
    public int Hall_number;
    public int estatus;
    public Boolean holdestatus;



    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public Boolean getHoldnumber() {
        return holdnumber;
    }

    public void setHoldnumber(Boolean holdnumber) {
        this.holdnumber = holdnumber;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getHolddescription() {
        return holddescription;
    }

    public void setHolddescription(Boolean holddescription) {
        this.holddescription = holddescription;
    }

    public String getComposition() {
        return composition;
    }

    public void setComposition(String composition) {
        this.composition = composition;
    }

    public Boolean getHoldcomposition() {
        return holdcomposition;
    }

    public void setHoldcomposition(Boolean holdcomposition) {
        this.holdcomposition = holdcomposition;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Boolean getHolddate() {
        return holddate;
    }

    public void setHolddate(Boolean holddate) {
        this.holddate = holddate;
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

    public Boolean getHoldestatus() {
        return holdestatus;
    }

    public void setHoldestatus(Boolean holdestatus) {
        this.holdestatus = holdestatus;
    }
}
