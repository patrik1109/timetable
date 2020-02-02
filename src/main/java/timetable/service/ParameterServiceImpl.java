package timetable.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import timetable.entities.Parameter;
import timetable.repository.EventRepository;
import timetable.repository.ParameterRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Service
public class ParameterServiceImpl implements ParameterService{

    ParameterRepository repository;


    @Autowired
    public void setParameterRepository(ParameterRepository repository) {

        this.repository = repository;
    }

    @Override
    public Parameter getParameterById(Integer id) {
        return repository.getOne(id);
    }

    @Override
    public void saveParameter(Parameter parameter) {
        repository.save(parameter);
    }

    @Override
    public void deleteParameterbyId(Integer id) {
          repository.deleteById(id);
    }

    @Override
    public void updateParameter(int id,String parameter, String textColor, String textFont, Integer textSize, String textBackGround) {
           Parameter Newparameter = new Parameter(id,parameter,textColor,textFont,textSize,textBackGround);
    }

    @Override
    public List<Parameter> findAll() {
        return repository.findAll();
    }
}
