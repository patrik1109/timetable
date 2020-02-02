package timetable.utils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import timetable.entities.User;


public class DummyContentUtil {

	 public static final List<User> generateDummyUsers() {
	        List<User> users = new ArrayList<>();
	        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
	        users.add(new User("admin", "admin@admin.com", passwordEncoder.encode("admin")));    
	        return users;
	    }
	 
	 
	    public static Collection<GrantedAuthority> getAuthorities() {
	        Collection<GrantedAuthority> grantedAuthorities = new ArrayList<GrantedAuthority>();
	        GrantedAuthority grantedAuthority = new GrantedAuthority() {
	            public String getAuthority() {
	                return "ROLE_USER";
	            }
	        };
	        grantedAuthorities.add(grantedAuthority);
	        return grantedAuthorities;
	    }
	
}
