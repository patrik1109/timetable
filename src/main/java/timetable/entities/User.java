package timetable.entities;


import org.hibernate.search.annotations.Indexed;
import timetable.enums.Role;
import timetable.enums.Role;
import java.util.Date;
import java.util.Set;
import javax.persistence.*;


@Entity
@Indexed
@Table(name="user")
public class User {
    @Id
    //@GeneratedValue(strategy = GenerationType.IDENTITY)
    @GeneratedValue(strategy = GenerationType.AUTO )
    int id;

    @Column(nullable = false, unique = true)
    String username;

    @Column(name = "email")
    String email;
    
    @Column(name = "password")
    String password;
    
    @Column(name = "last_login")
    Date lastLogin;

    @Column(name = "name")
    String name;

   /* @Column(name = "role")
    UserRole role;*/


    //@CollectionTable(name="user_role",joinColumns = @JoinColumn(name="id"))
    @Enumerated(EnumType.ORDINAL)
    private Role role;
    
    
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

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Role getRoles() {
        return role;
    }

    public void setRoles (Role role) {
        this.role = role;
    }



}
