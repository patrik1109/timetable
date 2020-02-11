package timetable.service;

import timetable.entities.Event;

import java.util.Date;
import java.util.List;

public interface EventService {
    Event getEventById(Integer id);
    void saveEvent(Event event);
    void deleteEventbyId(Integer id);
    void updateEvent(int id, String number, String description, Date date, int idHall, int estatus,String composition );
    List<Event> findAll();
    List<Event> findAllByIdHall(Integer id);
    List<Event> findAllByDate(Date date);
    List<Event> findAllByDateBetween(Date DateStart, Date DateEnd);
    List<Event> findAllByDateAndIdHall(Date date, int id_hall );

}
