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


    @Query(value= "select e from Event e where e.date = :date AND e.idHall =:idHall")
    List<Event> findAllWithCreationDateTimeandIdHall(@Param("date") Date date, @Param("idHall") int idHall );

    @Query(value= "select e from Event e where e.date = :date AND e.idHall =:idHall AND e.hide =:flag")
    List<Event> findAllWithDateandIdHallandNohidden(@Param("date") Date date, @Param("idHall") int idHall, @Param("flag") boolean flag);

    @Query(value= "select e from Event e where e.date = :date AND e.idHall =:idHall AND e.hide =:flag order by e.ordernumber")
    List<Event> findAllWithDateandIdHallandNohiddenOrdered(@Param("date") Date date, @Param("idHall") int idHall, @Param("flag") boolean flag);

    @Query(value= "select max(e.ordernumber) from Event e where e.date = :date AND e.idHall =:idHall ")
    Integer findMaxOrderNumberByDate(@Param("date") Date date, @Param("idHall") int idHall);

    @Query(value= "select e from Event e where e.date = :date AND e.idHall =:idHall  order by e.ordernumber")
    List<Event> findAllWithDateandIdHallOrdered(@Param("date") Date date, @Param("idHall") int idHall);



}
