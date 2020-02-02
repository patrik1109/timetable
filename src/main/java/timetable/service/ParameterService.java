package timetable.service;

import timetable.entities.Parameter;

import java.util.Date;
import java.util.List;
import java.util.Set;

public interface ParameterService {
    Parameter getParameterById(Integer id);
    void saveParameter(Parameter parameter);
    void deleteParameterbyId(Integer id);
    void updateParameter(int id,String parameter,String textColor,String textFont,Integer textSize,String textBackGround);
    List<Parameter> findAll();
}
