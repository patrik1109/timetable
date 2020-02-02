package timetable.repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import timetable.entities.User;

public interface UserRepository extends JpaRepository<User,Integer> {
	User findByUsername(String username);
	
	User getById(Integer id);

    List<User> findByName(String name);

    @Query("UPDATE User u SET u.lastLogin=:lastLogin WHERE u.username = ?#{ principal?.username }")
    @Modifying
    @Transactional
    public void updateLastLogin(@Param("lastLogin") Date lastLogin);
	
}
