package timetable.thymeleaf_form;

public class OrderForm {

    Integer ordernumber;
    boolean up;

    public OrderForm() {
    }

    public OrderForm(Integer ordernumber, boolean up) {
        this.ordernumber = ordernumber;
        this.up = up;
    }

    public Integer getOrdernumber() {
        return ordernumber;
    }

    public void setOrdernumber(Integer ordernumber) {
        this.ordernumber = ordernumber;
    }

    public boolean isUp() {
        return up;
    }

    public void setUp(boolean up) {
        this.up = up;
    }
}
