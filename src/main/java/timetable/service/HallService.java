package timetable.service;

import timetable.entities.Event;
import timetable.entities.Hall;

import java.util.Date;
import java.util.List;
import java.util.Set;

public interface HallService {
    Hall getHallById(Integer id);
    void saveHall(Hall hall);
    void deleteHallbyId(Integer id);
    void updateHall(int id, String name, Date date, Set<Event> events,String hiddencolloms);
    List<Hall> findAll();
    List<Event> findHallEvents(Integer id);
}
