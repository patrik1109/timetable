package timetable.thymeleaf_form;

import timetable.enums.UserRole;

public class UserForm {
    int id;
    String username;
    UserRole role;
    String email;
    String password;
    String confirmpassword;

    public UserForm(int id, String username, UserRole role, String email, String password, String confirmpassword) {
        this.id = id;
        this.username = username;
        this.role = role;
        this.email = email;
        this.password = password;
        this.confirmpassword = confirmpassword;
    }

    public UserForm() {
    }

    
    
    public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getConfirmpassword() {
        return confirmpassword;
    }

    public void setConfirmpassword(String Confirmpassword) {
        this.confirmpassword = confirmpassword;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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
