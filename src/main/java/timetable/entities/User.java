package timetable.entities;

import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Indexed;
import org.hibernate.search.annotations.TermVector;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import timetable.enums.UserRole;

import javax.persistence.*;


@Entity
@Indexed
@Table(name="users", schema = "test22", catalog = "" )
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

    @Column(name = "name")
    String name;

    @Column(name = "role")
    UserRole role;

    @Column(name = "password")
    String password;

    public User() {
    }

    public User(int id, String name, UserRole role, String password) {
        this.id = id;
        this.name = name;
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

    public void setName(String user) {
        this.name = user;
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
