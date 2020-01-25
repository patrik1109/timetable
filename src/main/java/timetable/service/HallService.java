package timetable.service;

import timetable.entities.Event;
import timetable.entities.Hall;

import java.util.List;

public interface HallService {
    Hall getHallById(Integer id);
    void saveHall(Hall hall);
    void deleteHallbyId(Integer id);
    List<Hall> findAll();
    List<Event> findHallEvents(Integer id);
}
