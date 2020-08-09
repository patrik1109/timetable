package timetable.entities;


import com.fasterxml.jackson.annotation.JsonFormat;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Indexed;
import org.hibernate.search.annotations.TermVector;

import timetable.utils.TimeUtils;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Time;
import java.util.Calendar;
import java.util.Date;

@Entity
@Indexed
@Table(name="event", schema = "test", catalog = "" )
public class Event implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

    @Column(name = "number")
    @Field(termVector = TermVector.YES)
    String number; // номер справи

    @Column(name="defendant")
    String defendant;    // позивач

    @Column(name="plaintiff")
    String plaintiff;    // видповидач

    @Column(name="contestation")
    String contestation; // предмет позиву

    @Column(name = "description")// сторони у справи та предмет спору
    String description;

    @Column(name = "date" )
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd")
    @Temporal(TemporalType.DATE)
    Date date;

    @Column(name="time")
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="HH:mm:ss")
    @Temporal(TemporalType.TIME)
    Date time;

    @Column(name = "composition" )
    String composition;  // склад суду

    @Column(name="additionalstatus")
    String additionalstatus;  // доповнення до статусу

    private int  idStatus;

	private int idHall;

	@Column(name="hide")
	private boolean hide;

	@Column(name="ordernumber")
    int ordernumber;


    public Event() {
    }

    public Event(int id, String number, String defendant, String plaintiff, String contestation, String description, Date date, Date time, String composition, String additionalstatus, int idStatus, int idHall, boolean hide, int ordernumber) {
        this.id = id;
        this.number = number;
        this.defendant = defendant;
        this.plaintiff = plaintiff;
        this.contestation = contestation;
        this.description = description;
        this.date = date;
        this.time = time;
        this.composition = composition;
        this.additionalstatus = additionalstatus;
        this.idStatus = idStatus;
        this.idHall = idHall;
        this.hide = hide;
        this.ordernumber = ordernumber;
    }

    public int getOrdernumber() {
        return ordernumber;
    }

    public void setOrdernumber(int ordernumber) {
        this.ordernumber = ordernumber;
    }

    public boolean isHide() {
        return hide;
    }

    public void setHide(boolean hide) {
        this.hide = hide;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
       	this.date = TimeUtils.getTimeZoneDate(date);
    }

    public int getIdHall() {
        return idHall;
    }

    public void setIdHall(int idHall) {
        this.idHall = idHall;
    }

    public int getIdStatus() {
        return idStatus;
    }

    public void setIdStatus(int idStatus) {
        this.idStatus = idStatus;
    }
    
}
