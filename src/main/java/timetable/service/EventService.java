package timetable.service;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import timetable.entities.Event;
import timetable.enums.EventStatus;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

public interface EventService {
    Event getEventById(Integer id);
    void saveEvent(Event event);
    void deleteEventbyId(Integer id);
    void updateEvent(int id, String number, String description, Date date, int idHall, EventStatus estatus, String color);
    List<Event> findAll();
    List<Event> findAllByIdHall(Integer id);
    List<Event> findAllByDate(Date date);
    List<Event> findAllByDateBetween(Date DateStart, Date DateEnd);
}
