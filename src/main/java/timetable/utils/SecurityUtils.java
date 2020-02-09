package timetable.utils;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import timetable.service.UserPrincipalImpl;

public class SecurityUtils {
    private SecurityUtils() {
    }


    public static String getUserName() {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        Authentication authentication = securityContext.getAuthentication();
        String userName = null;
        if (authentication != null) {
            /*userName = (String) authentication.getPrincipal();*/
            UserPrincipalImpl userDetails = (UserPrincipalImpl) authentication.getPrincipal();
            userName = userDetails. getUsername();

        }
        return userName;
    }
}