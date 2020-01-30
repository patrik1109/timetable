package timetable.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import timetable.entities.User;

public interface UserRepository extends JpaRepository<User,Integer> {
}
