package timetable.service;

import timetable.entities.Event;
import timetable.enums.EventStatus;

import java.util.Date;
import java.util.List;

public interface EventService {
    Event getEventById(Integer id);
    void saveEvent(Event event);
    void deleteEventbyId(Integer id);
    void updateEvent(int id, String number, String description, Date date, int idHall, EventStatus estatus);
    List<Event> findAll();
    List<Event> findAllByIdHall(Integer id);
}
