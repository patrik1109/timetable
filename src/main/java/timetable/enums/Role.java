package timetable.enums;

import org.springframework.security.core.GrantedAuthority;

public enum Role implements GrantedAuthority {
    USER,
    ADMIN,
    SUPERADMIN;

    @Override
    public String getAuthority(){
        return name();
    }

}
