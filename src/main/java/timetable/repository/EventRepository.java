package timetable.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.format.annotation.DateTimeFormat;
import timetable.entities.Event;

import java.util.Date;
import java.util.List;

public interface EventRepository extends JpaRepository<Event,Integer> {
    List<Event> findAllByOrderByDateAsc();

    List<Event> findAllByDate(Date date);
    List<Event> findAllByDateBetween(Date DateStart, Date DateEnd);


    @Query(value= "select e from event e where e.date = :date AND e.idHall =: idHall")
    List<Event> findAllWithCreationDateTimeandIdHall(
            @Param("date") Date date, @Param("idHall") int idHall );


    /*List<Event> findAllByDateAndId_Hall(Date date,int idHall );*/


}
