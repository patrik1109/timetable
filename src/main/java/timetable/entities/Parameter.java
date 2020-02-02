package timetable.entities;

import javax.persistence.*;

@Entity
@Table(name="parameter", schema = "test22", catalog = "" )
public class Parameter {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

    @Column(name = "parameter")
    String parameter;

    @Column(name = "textcolor")
    String textcolor;

    @Column(name = "textfont")
    String textfont;

    @Column(name = "textsize")
    Integer textsize;

    @Column(name = "textbackground")
    String textbackground;

    public Parameter(int id,String parameter, String textcolor, String textfont, Integer textsize, String textbackground) {
        this.id = id;
        this.textcolor = textcolor;
        this.textfont = textfont;
        this.textsize = textsize;
        this.textbackground = textbackground;
        this.parameter = parameter;
    }

    public Parameter() {
    }

    public String getParameter() {
        return parameter;
    }

    public void setParameter(String parameter) {
        this.parameter = parameter;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTextcolor() {
        return textcolor;
    }

    public void setTextcolor(String textcolor) {
        this.textcolor = textcolor;
    }

    public String getTextfont() {
        return textfont;
    }

    public void setTextfont(String textfont) {
        this.textfont = textfont;
    }

    public Integer getTextsize() {
        return textsize;
    }

    public void setTextsize(Integer textsize) {
        this.textsize = textsize;
    }

    public String getTextbackground() {
        return textbackground;
    }

    public void setTextbackground(String textbackground) {
        this.textbackground = textbackground;
    }
}
