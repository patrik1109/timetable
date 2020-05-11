package timetable.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import timetable.entities.Event;
import timetable.entities.User;
import timetable.enums.UserRole;
import timetable.repository.UserRepository;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService,UserDetailsService {
   
    @Autowired
    private UserRepository repository;

/*
    @Autowired
    public void setProductRepository(UserRepository repository) {

        this.repository = repository;
    }*/

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
    public void updateUser(int id, String username, UserRole role, String email, String password) {
    	User updUser = repository.getById(id);
    	if(updUser != null) 
    	{
    		updUser.setUsername(username);
    		//updUser.setRole(role);
    		updUser.setEmail(email);
    		updUser.setPassword(password);
    		repository.save(updUser);
        }
    }

    @Override
    public List<User> findAll() {
        return repository.findAll();
    }

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = repository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException(username);
        }
        return new UserPrincipalImpl(user);
	}
}
