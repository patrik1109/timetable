package timetable.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import timetable.entities.Hall;

public interface HallRepository extends JpaRepository<Hall,Integer> {
}
