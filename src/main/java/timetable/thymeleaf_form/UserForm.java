package timetable.thymeleaf_form;

import timetable.enums.UserRole;

public class UserForm {
    int id;
    String name;
    UserRole role;
    String password;
    String konformpassword;

    public UserForm(int id, String name, UserRole role, String password, String konformpassword) {
        this.id = id;
        this.name = name;
        this.role = role;
        this.password = password;
        this.konformpassword = konformpassword;
    }

    public UserForm() {
    }

    public String getKonformpassword() {
        return konformpassword;
    }

    public void setKonformpassword(String konformpassword) {
        this.konformpassword = konformpassword;
    }

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

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }

    public String getPassword() { return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
}
