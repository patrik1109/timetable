package timetable.responses;

import timetable.enums.UserRole;

public class UserResponse {
    int id;
    String username;
    String name;
    UserRole role;
    String password;

    public UserResponse() {
    }

    public UserResponse(int id, String username, UserRole role, String password) {
        this.id = id;
        this.username = username;
        this.role = role;
        this.password = password;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
