package timetable.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import timetable.entities.User;

import java.util.List;

public interface UserRepo extends JpaRepository<User,Long> {
    User findByUsername(String username);
    User findByid(Long id);

    //List<User> fundAll();
    //void deleteUserbyId(Long iduser);
}
