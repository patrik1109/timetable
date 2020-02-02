package timetable.entities;

import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Indexed;
import org.hibernate.search.annotations.TermVector;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import timetable.enums.UserRole;

import java.util.Date;

import javax.persistence.*;


@Entity
@Indexed
@Table(name="user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

    @Column(nullable = false, unique = true)
    String username;

    @Column(name = "role")
    UserRole role;

    @Column(name = "email")
    String email;
    
    @Column(name = "password")
    String password;
    
    @Column(name = "last_login")
    Date lastLogin;

    @Column(name = "name")
    String name;
    
    
    
    
    public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getLastLogin() {
		return lastLogin;
	}

	public void setLastLogin(Date lastLogin) {
		this.lastLogin = lastLogin;
	}

	public User() {
    }

    public User(String username, String email, String password) {
    	this.name = username;
        this.username = username;
        this.email = email;
        this.password = password;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }





}
