package timetable.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import timetable.entities.Parameter;

public interface ParameterRepository extends JpaRepository<Parameter,Integer> {
}
