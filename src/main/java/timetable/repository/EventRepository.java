package timetable.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import timetable.entities.Event;

public interface EventRepository extends JpaRepository<Event,Integer> {
}
