package timetable.thymeleaf_form;

public class SettingForm {

    String parameter;
    String textcolor;
    String textfont;
    String textsize;
    String textbackground;

    public SettingForm(String parameter, String textcolor, String textfont, String textsize, String textbackground) {
        this.parameter = parameter;
        this.textcolor = textcolor;
        this.textfont = textfont;
        this.textsize = textsize;
        this.textbackground = textbackground;
    }

    public SettingForm() {
    }

    public String getParameter() {
        return parameter;
    }

    public void setParameter(String parameter) {
        this.parameter = parameter;
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

    public String getTextsize() {
        return textsize;
    }

    public void setTextsize(String textsize) {
        this.textsize = textsize;
    }

    public String getTextbackground() {
        return textbackground;
    }

    public void setTextbackground(String textbackground) {
        this.textbackground = textbackground;
    }
}
