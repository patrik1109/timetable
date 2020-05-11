package timetable.utils;

import timetable.entities.Hall;
import timetable.entities.User;
import timetable.responses.HallResponse;
import timetable.responses.UserResponse;

import java.util.LinkedList;
import java.util.List;

public  class FillForms {

    public static String errorMessage;

    public static List<HallResponse> fillHallResponse (List<Hall> hallList){
        List<HallResponse> halls = new LinkedList<>();
        for (Hall hall: hallList) {
            HallResponse hallResponse = new HallResponse();
            hallResponse.setName(hall.getName());
            hallResponse.setDate(hall.getDate());
            hallResponse.setId(hall.getId());
            hallResponse.setEventSet(hall.getEventSet());
            halls.add(hallResponse);
        }
        return  halls;
    }

    public static List<UserResponse> fillUserResponse(List<User> userList) {
        List<UserResponse> users = new LinkedList<>();
        for(User user : userList){
            UserResponse userResponse = new UserResponse();
            userResponse.setId(user.getId());
            userResponse.setUsername(user.getUsername());
            userResponse.setPassword(user.getPassword());
            userResponse.setRole(user.getRole());
            users.add(userResponse);
        }
        return users;
    }
}
