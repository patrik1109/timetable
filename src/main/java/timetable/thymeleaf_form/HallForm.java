package timetable.thymeleaf_form;

import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

public class HallForm {
    int id;
    String name;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    Date date;

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

    public HallForm() {
    }

    public HallForm(int id, String name, Date date) {
        this.id = id;
        this.name = name;
        this.date = date;
    }
}
