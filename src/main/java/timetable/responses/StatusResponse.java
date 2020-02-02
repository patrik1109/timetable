package timetable.responses;

public class StatusResponse {
    int id;
    String status;
    String color;

    public StatusResponse(int id, String status, String color) {
        this.id = id;
        this.status = status;
        this.color = color;
    }

    public StatusResponse() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }
}
