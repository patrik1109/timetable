package timetable.responses;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.sql.Time;
import java.util.Date;

public class EventResponse {

    public int id;
    public String number;           // номер справи
    public String description;      // сторони у справи та предмет спору
    //@DateTimeFormat(pattern = "yyyy-MM-dd")
    //public Date date;
    public String date;
    public int idHall;
    public String status;
    public String color;
    public String composition;      // склад суду

    public String time;               // час слухання
    public String defendant;        // позивач
    public String plaintiff;        // видповидач
    public String contestation;      // предмет позиву
    public String additionalstatus;   // доповнення до статусу
    public boolean hide;

    public boolean isHide() {
        return hide;
    }

    public void setHide(boolean hide) {
        this.hide = hide;
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

    public String getAdditionalstatus() {
        return additionalstatus;
    }

    public void setAdditionalstatus(String additionalstatus) {
        this.additionalstatus = additionalstatus;
    }

    public String getComposition() {
        return composition;
    }

    public void setComposition(String composition) {
        this.composition = composition;
    }



    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
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

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getIdHall() {
        return idHall;
    }

    public void setIdHall(int idHall) {
        this.idHall = idHall;
    }

	public EventResponse() { }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

}
