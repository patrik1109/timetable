package timetable.service;

import org.apache.lucene.search.Query;

import org.hibernate.search.jpa.FullTextEntityManager;
import org.hibernate.search.jpa.FullTextQuery;
import org.hibernate.search.jpa.Search;
import org.hibernate.search.query.dsl.QueryBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import timetable.entities.Event;
import timetable.repository.EventRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import java.sql.Time;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

@Service
public class EventServiceImpl implements EventService{

    EventRepository repository;

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    public void setProductRepository(EventRepository repository) {

        this.repository = repository;
    }

    @Override
    public Event getEventById(Integer id) {
        return repository.getOne(id);
    }

    @Override
    public void saveEvent(Event event) {
        repository.save(event);
    }

    @Override
    public void deleteEventbyId(Integer id) {
        repository.deleteById(id);
    }

    @Override
    public void updateEvent(int id, String number, Date time, String defendant, String plaintiff, String contestation, String description, Date date, String composition, String additionalstatus, int idStatus, int idHall) {
        Event newEvent = new Event(id,number,time,defendant,plaintiff,contestation,description,date,composition,additionalstatus,idStatus,idHall);
        repository.save(newEvent);
    }


    @Override
    public List<Event> findAll() {
        return repository.findAll();
    }

    @Override
    public List<Event> findAllByIdHall(Integer id_hall) {
        List<Event> results = new LinkedList<>();
        try {
            FullTextEntityManager fullTextEntityManager = Search.getFullTextEntityManager(entityManager);
            fullTextEntityManager.createIndexer().startAndWait();
            QueryBuilder queryBuilder = fullTextEntityManager.getSearchFactory()
                    .buildQueryBuilder()
                    .forEntity(Event.class)
                    .get();
            Query query = queryBuilder. keyword().onField("id_hall").matching(id_hall).createQuery(); //непнятно как ищет


            FullTextQuery jpaQuery = fullTextEntityManager.createFullTextQuery(query, Event.class);
            results = jpaQuery.getResultList();
        }
        catch (Exception e){
            System.out.println(e.getMessage());
            System.out.println(e.fillInStackTrace());
        }
        return results;
    }



    @Override
    public List<Event> findAllByDate(Date date) {
        return repository.findAllByDate(date);
    }

    @Override
    public List<Event> findAllByDateBetween(Date DateStart, Date DateEnd) {
        return repository.findAllByDateBetween(DateStart,DateEnd);
    }

    @Override
    public List<Event> findAllByDateAndIdHall(Date date, int id_hall) {
        return repository.findAllWithCreationDateTimeandIdHall(date,id_hall);
    }


}
