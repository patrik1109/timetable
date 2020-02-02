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
	        /*users.add(new User("Cristiano Ronaldo", "cristiano@ronaldo.com", passwordEncoder.encode("c1234")));
	        users.add(new User("Neymar Dos Santos", "neymar@neymar.com", passwordEncoder.encode("n1234")));
	      
	         
	        users.add(new User("Luiz Suarez", "luiz@suarez.com", passwordEncoder.encode("lu1234")));
	        users.add(new User("Andres Iniesta", "andres@iniesta.com", passwordEncoder.encode("a1234")));
	        users.add(new User("Ivan Rakitic", "ivan@rakitic.com", passwordEncoder.encode("i1234")));
	        users.add(new User("Ousman Dembele", "ousman@dembele.com", passwordEncoder.encode("o1234")));
	        users.add(new User("Sergio Busquet", "sergio@busquet.com", passwordEncoder.encode("s1234")));
	        users.add(new User("Gerard Pique", "gerard@pique.com", passwordEncoder.encode("g1234")));
	        users.add(new User("Ter Stergen", "ter@stergen.com", passwordEncoder.encode("t1234")));
	   */     
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
