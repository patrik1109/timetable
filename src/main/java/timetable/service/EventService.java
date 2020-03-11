package timetable.service;


import timetable.entities.Event;


import java.sql.Time;
import java.util.Date;
import java.util.List;

public interface EventService {
    Event getEventById(Integer id);
    void saveEvent(Event event);
    void deleteEventbyId(Integer id);
    void updateEvent(int id, String number, Date time, String defendant, String plaintiff, String contestation, String description, Date date, String composition, String additionalstatus, int idStatus, int idHall);
    List<Event> findAll();
    List<Event> findAllByIdHall(Integer id);
    List<Event> findAllByDate(Date date);
    List<Event> findAllByDateBetween(Date DateStart, Date DateEnd);
    List<Event> findAllByDateAndIdHall(Date datestart, int id_hall );

}
