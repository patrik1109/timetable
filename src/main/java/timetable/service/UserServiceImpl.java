package timetable.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import timetable.entities.Event;
import timetable.entities.User;
import timetable.enums.UserRole;
import timetable.repository.UserRepository;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    UserRepository repository;


    @Autowired
    public void setProductRepository(UserRepository repository) {

        this.repository = repository;
    }

    @Override
    public User getUserById(Integer id) {
        return repository.getOne(id);
    }

    @Override
    public void saveUser(User user) {
        repository.save(user);
    }

    @Override
    public void deleteUserbyId(Integer id) {
        repository.deleteById(id);
    }

    @Override
    public void updateUser(int id, String name, UserRole role, String password) {
        User newUser = new User(id,name,role,password);
        repository.save(newUser);
    }

    @Override
    public List<User> findAll() {
        return repository.findAll();
    }
}
