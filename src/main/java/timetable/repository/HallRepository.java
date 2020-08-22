package timetable.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import timetable.entities.Event;
import timetable.entities.Hall;

public interface HallRepository extends JpaRepository<Hall,Integer> {

    @Query(value= "select h from Hall h where  h.id =:id")
    Hall findHallById(@Param("id") int id);
}
