package timetable.service;

import timetable.entities.User;
import timetable.enums.Role;
import timetable.enums.Role;

import java.util.List;

public interface UserService {
    User getUserById(Integer id);
    void saveUser(User user);
    void deleteUserbyId(Integer id);
    void updateUser(int id, String name, Role role, String email, String password);
    List<User> findAll();
}
