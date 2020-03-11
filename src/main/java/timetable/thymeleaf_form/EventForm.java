package timetable.thymeleaf_form;


import org.springframework.format.annotation.DateTimeFormat;


import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.sql.Time;
import java.util.Date;

public class EventForm {

    public String number; // номер справи

    public String description; // сторони у справи та предмет спору

    public String composition;   // склад суду

    @DateTimeFormat(pattern = "HH:SS")
    public String time;  // час слухання

    public String defendant;  // позивач

    public String plaintiff;  // видповидач

    public String contestation;  // предмет позиву

    public String additionalstatus;   // доповнення до статусу

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    public Date date;

    public int Hall_number;
    public int estatus;

    public String getAdditionalstatus() {
        return additionalstatus;
    }

    public void setAdditionalstatus(String additionalstatus) {
        this.additionalstatus = additionalstatus;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDefendant() {
        return defendant;
    }

    public void setDefendant(String defendant) {
        this.defendant = defendant;
    }

    public String getPlaintiff() {
        return plaintiff;
    }

    public void setPlaintiff(String plaintiff) {
        this.plaintiff = plaintiff;
    }

    public String getContestation() {
        return contestation;
    }

    public void setContestation(String contestation) {
        this.contestation = contestation;
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
