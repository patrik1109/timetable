package timetable.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import timetable.entities.StatusEvent;

public interface StatusEventRepository extends JpaRepository<StatusEvent,Integer> {
}
