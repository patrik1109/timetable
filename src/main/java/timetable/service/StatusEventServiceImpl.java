package timetable.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import timetable.entities.StatusEvent;
import timetable.repository.StatusEventRepository;

import java.util.List;

@Service
public class StatusEventServiceImpl implements StatusEventService {

    StatusEventRepository repository;

    @Autowired
    public void setStatusEventRepository(StatusEventRepository repository){
        this.repository = repository;
    }

    @Override
    public StatusEvent getStatusEventById(Integer id) {
        return repository.getOne(id);
    }

    @Override
    public void saveStatusEvent(StatusEvent statusEvent) {
        repository.save(statusEvent);
    }

    @Override
    public void deleteStatusEventbyId(Integer id) {
         repository.deleteById(id);
    }

    @Override
    public void updateStatusEvent(int id, String status, String color) {
        StatusEvent statusEvent = new StatusEvent(id,status,color);
        repository.save(statusEvent);
    }

    @Override
    public List<StatusEvent> findAll() {
        return repository.findAll();
    }
}
