package timetable.thymeleaf_form;

import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

public class HallEventsForm {

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    Date dateStart;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    Date dateEnd;

    public Date getDateStart() {
        return dateStart;
    }

    public void setDateStart(Date dateStart) {
        this.dateStart = dateStart;
    }

    public Date getDateEnd() {
        return dateEnd;
    }

    public void setDateEnd(Date dateEnd) {
        this.dateEnd = dateEnd;
    }

    public HallEventsForm() {
    }

    public HallEventsForm(Date dateStart, Date dateEnd) {
        this.dateStart = dateStart;
        this.dateEnd = dateEnd;
    }
}
