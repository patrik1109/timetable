package timetable.thymeleaf_form;

import java.util.HashMap;

public class EventfieldsForm {
    Boolean number;       // номер справи
    Boolean composition;  // склад суду
    Boolean time;         // час слухання
    Boolean defendant;    // позивач
    Boolean plaintiff;    // видповидач
    Boolean contestation; // предмет позиву
    Boolean description;  // сторони у справи та предмет спору
    Boolean idStatus;     // статус справи
    Boolean additionalStatus;  // доповнення до статусу
    Integer idHall; //

    public EventfieldsForm(Boolean number, Boolean composition, Boolean time, Boolean defendant, Boolean plaintiff, Boolean contestation, Boolean description, Boolean idStatus, Boolean additionalStatus, Integer idHall) {
        this.number = number;
        this.composition = composition;
        this.time = time;
        this.defendant = defendant;
        this.plaintiff = plaintiff;
        this.contestation = contestation;
        this.description = description;
        this.idStatus = idStatus;
        this.additionalStatus = additionalStatus;
        this.idHall = idHall;
    }

    public EventfieldsForm() {
    }

    public Integer getIdHall() {
        return idHall;
    }

    public void setIdHall(int idHall) {
        this.idHall = idHall;
    }


    public Boolean getNumber() {
        return number;
    }

    public void setNumber(Boolean number) {
        this.number = number;
    }

    public Boolean getComposition() {
        return composition;
    }

    public void setComposition(Boolean composition) {
        this.composition = composition;
    }

    public Boolean getTime() {
        return time;
    }

    public void setTime(Boolean time) {
        this.time = time;
    }

    public Boolean getDefendant() {
        return defendant;
    }

    public void setDefendant(Boolean defendant) {
        this.defendant = defendant;
    }

    public Boolean getPlaintiff() {
        return plaintiff;
    }

    public void setPlaintiff(Boolean plaintiff) {
        this.plaintiff = plaintiff;
    }

    public Boolean getContestation() {
        return contestation;
    }

    public void setContestation(Boolean contestation) {
        this.contestation = contestation;
    }

    public Boolean getDescription() {
        return description;
    }

    public void setDescription(Boolean description) {
        this.description = description;
    }

    public Boolean getIdStatus() {
        return idStatus;
    }

    public void setIdStatus(Boolean idStatus) {
        this.idStatus = idStatus;
    }

    public Boolean getAdditionalStatus() {
        return additionalStatus;
    }

    public void setAdditionalStatus(Boolean additionalStatus) {
        this.additionalStatus = additionalStatus;
    }
}
