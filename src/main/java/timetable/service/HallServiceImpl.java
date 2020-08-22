package timetable.service;

import org.apache.lucene.search.Query;
import org.hibernate.search.jpa.FullTextEntityManager;
import org.hibernate.search.jpa.FullTextQuery;
import org.hibernate.search.jpa.Search;
import org.hibernate.search.query.dsl.QueryBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import timetable.entities.Event;
import timetable.entities.Hall;

import timetable.repository.EventRepository;
import timetable.repository.HallRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

@Service
public class HallServiceImpl implements HallService {

    HallRepository repository;

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    public void setProductRepository(HallRepository repository) {

        this.repository = repository;
    }


    @Override
    public Hall getHallById(Integer id) {
        return repository.getOne(id);
    }

    @Override
    public void saveHall(Hall hall) {
        repository.save(hall);
    }

    @Override
    public void deleteHallbyId(Integer id) {
        repository.deleteById(id);
    }

    @Override
    public void updateHall(int id, String name, Date date, Set<Event> events,String hiddencolloms) {
        Hall hall = new Hall(id,name,date,events,hiddencolloms);
        repository.save(hall);
    }


    @Override
    public List<Hall> findAll() {
        return repository.findAll();
    }

    @Override
    public List<Event> findHallEvents(Integer id) {
        List<Event> results = new LinkedList<>();
        try {
            FullTextEntityManager fullTextEntityManager = Search.getFullTextEntityManager(entityManager);
            fullTextEntityManager.createIndexer().startAndWait();
            QueryBuilder queryBuilder = fullTextEntityManager.getSearchFactory()
                    .buildQueryBuilder()
                    .forEntity(Event.class)
                    .get();
            Query query = queryBuilder. keyword().onField("id_hall").matching(id).createQuery(); //непнятно как ищет
            // Query query = queryBuilder .phrase().withSlop(1).onField("Title").sentence("FFFF").createQuery(); //совпадет одно слово в фразе

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
    public Hall findHallById(int id) {
        return repository.findHallById(id);
    }
}
