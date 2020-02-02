package timetable.service;

import timetable.entities.StatusEvent;

import java.util.Date;
import java.util.List;
import java.util.Set;

public interface StatusEventService {
    StatusEvent getStatusEventById(Integer id);
    void saveStatusEvent(StatusEvent statusEvent);
    void deleteStatusEventbyId(Integer id);
    void updateStatusEvent(int id, String status, String color );
    List<StatusEvent> findAll();
}
