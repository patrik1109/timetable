package timetable.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import timetable.entities.*;
import timetable.enums.UserRole;
import timetable.repository.EventRepository;
import timetable.repository.UserRepository;
import timetable.responses.EventResponse;
import timetable.responses.*;
import timetable.service.*;
import timetable.thymeleaf_form.*;
import timetable.responses.HallResponse;
import timetable.responses.UserResponse;
import timetable.service.EventService;
import timetable.service.HallService;
import timetable.service.UserService;
import timetable.thymeleaf_form.EventForm;
import timetable.thymeleaf_form.HallEventsForm;
import timetable.thymeleaf_form.HallForm;
import timetable.thymeleaf_form.UserForm;
import timetable.utils.DummyContentUtil;

import java.text.SimpleDateFormat;
import java.util.*;

@RestController
public class timeTableController {
    @Autowired
    EventService eventRepository;

    @Autowired
    HallService hallRepository;

    @Autowired
    UserService userRepository;

    @Autowired
    StatusEventService statusEventRepository;

    @Autowired
    ParameterService parameterRepository;

    private volatile Event event;
    private volatile Hall hall;
    private volatile User user;
    private volatile Parameter parameter;
    @Value("${error.message}")

    private String errorMessage;

    //
    //EVENTS PART
    //


    @Transactional
    @RequestMapping(value = { "/AddEvent" }, method = RequestMethod.GET )
    public ModelAndView addEvent(ModelAndView model){
        ModelAndView newModel = new ModelAndView("AddEvent");
        List<HallResponse> hallResponses = fillHallResponse(hallRepository.findAll());
        List<StatusResponse> statuses = fillStatusResponse(statusEventRepository.findAll());

        EventForm eventForm = new EventForm();
        Date date = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DATE, 1); 
        eventForm.setDate(cal.getTime());
        
        newModel.addObject("eventForm", eventForm);
        newModel.addObject("halls",hallResponses);
        newModel.addObject("statuses",statuses);
        return  newModel;
    }




    @RequestMapping(value = { "/AddEvent" }, method = RequestMethod.POST)
    public ModelAndView addEvent(ModelAndView model, @ModelAttribute("eventForm") EventForm eventForm) {
        Event newEvent = new Event();
        Date date = eventForm.getDate();
        newEvent.setDate(date);
        newEvent.setDescription(eventForm.getDescription());
        newEvent.setIdHall(eventForm.getHall_number());
        newEvent.setNumber(eventForm.getNumber());
        newEvent.setIdStatus(eventForm.getEstatus());
        
        eventRepository.saveEvent(newEvent);
        int hall_Id = eventForm.getHall_number();
        return new ModelAndView("redirect:/hallEvents/"+hall_Id);
    }


    @Transactional
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @RequestMapping(value = { "/showHall/{id}" }, method = RequestMethod.GET)
    public ModelAndView showHall(@PathVariable Integer id) {
        ModelAndView NewModel = new ModelAndView("showHall");

        String  hallName = hallRepository.getHallById(id).getName();

        Date date =  new Date();
        List<Event> eventList = eventRepository.findAllByDate(date);
        List<EventResponse> eventsresponse = new LinkedList<>();

        for (Event event: eventList) {
            if(event.getIdHall()==id) {
                EventResponse response = new EventResponse();
                StatusEvent statusEvent = statusEventRepository.getStatusEventById(event.getIdStatus());
                response.setDate(event.getDate());
                response.setDescription(event.getDescription());
                response.setNumber(event.getNumber());
                response.setColor(statusEvent.getColor());
                response.setStatus(statusEvent.getStatus());
                eventsresponse.add(response);
            }
        }
        NewModel.addObject("events",eventsresponse);
        NewModel.addObject("hallName",hallName);
        NewModel.addObject("dateTime",date);
        return NewModel;
    }
    @Transactional
    @RequestMapping(value = { "/hallEvents/{id}" }, method = RequestMethod.GET)
    public ModelAndView editHallEvents(@PathVariable Integer id) {
        ModelAndView NewModel = new ModelAndView("hallEvents");
        Integer hallid = id;
        String  hallName = hallRepository.getHallById(id).getName();
        List<Event> events = eventRepository.findAll();
        List<HallResponse> halls = fillHallResponse(hallRepository.findAll());
        List<EventResponse> eventsresponse = new LinkedList<>();
        HallEventsForm hallEventsForm = new HallEventsForm();


        for (Event event: events) {
            if(event.getIdHall()==id) {
                EventResponse response = new EventResponse();
                StatusEvent statusEvent = statusEventRepository.getStatusEventById(event.getIdStatus());
                response.setDate(event.getDate());
                response.setDescription(event.getDescription());
                response.setNumber(event.getNumber());
                response.setId(event.getId());
                response.setStatus(statusEvent.getStatus());
                eventsresponse.add(response);
            }
        }
        NewModel.addObject("events",eventsresponse);
        NewModel.addObject("hallName",hallName);
        NewModel.addObject("hallEventsForm",hallEventsForm);
        NewModel.addObject("hallid",hallid);
        NewModel.addObject("halls",halls);
        return NewModel;
    }

    @RequestMapping(value = { "/hallEvents/{id}" }, method = RequestMethod.POST)
    public ModelAndView editHallEvents(@PathVariable Integer id, @ModelAttribute("hallEventsForm") HallEventsForm halleventsForm) {
        ModelAndView NewModel = new ModelAndView("hallEvents");
        HallEventsForm hallEventsForm = new HallEventsForm();
        Date dateStart = halleventsForm.getDateStart();
        List<Event> events = eventRepository.findAllByDate(dateStart) ;
        List<EventResponse> eventsresponse = new LinkedList<>();
        String  hallName = hallRepository.getHallById(id).getName();
        Integer hallid = id;
        for (Event event: events) {
            if(event.getIdHall()==id) {
                EventResponse response = new EventResponse();
                StatusEvent statusEvent = statusEventRepository.getStatusEventById(event.getIdStatus());
                response.setDate(event.getDate());
                response.setDescription(event.getDescription());
                response.setNumber(event.getNumber());
                response.setId(event.getId());
                response.setStatus(statusEvent.getStatus());
                eventsresponse.add(response);
            }
        }

        NewModel.addObject("events",eventsresponse);
        NewModel.addObject("hallName",hallName);
        NewModel.addObject("hallid",hallid);
        NewModel.addObject("hallEventsForm",hallEventsForm);

        return NewModel;
    }



    @GetMapping("/delete/{id}")
     public ModelAndView delete(@PathVariable Integer id) {
        Event tmpevent = eventRepository.getEventById(id);
        int hall_Id = tmpevent.getIdHall();
        eventRepository.deleteEventbyId(id);

        return new ModelAndView("redirect:/hallEvents/"+hall_Id);
    }

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @RequestMapping(value = { "/editEvent/{id}" }, method = RequestMethod.GET)
    public ModelAndView editEvent(@PathVariable Integer id) {
        ModelAndView newModel = new ModelAndView("editEvent");
        EventForm eventForm = new EventForm();

        List<HallResponse> hallResponses = fillHallResponse(hallRepository.findAll());
        List<StatusResponse> statuses = fillStatusResponse(statusEventRepository.findAll());

        event = eventRepository.getEventById(id);
            eventForm.setDate(event.getDate());
            eventForm.setDescription(event.getDescription());
            eventForm.setHall_number(event.getIdHall());
            eventForm.setNumber(event.getNumber());
            eventForm.setestatus(event.getIdStatus());

        newModel.addObject("eventForm", eventForm);
        newModel.addObject("halls",hallResponses);
        newModel.addObject("statuses",statuses);
        return  newModel;
    }

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @RequestMapping(value = { "/editEvent" }, method = RequestMethod.POST)
    public ModelAndView editEvent(ModelAndView model,    @ModelAttribute("eventForm") EventForm eventForm) {
        Integer idEvent = event.getId();
        Integer idHall = eventForm.getHall_number();
        String numberEvent = eventForm.getNumber();
        String description = (eventForm.getDescription());
        int estatus = eventForm.getEstatus();
        Date date= eventForm.getDate();
        if (idEvent !=0   ) {

            eventRepository.updateEvent(idEvent,numberEvent,description,date,idHall,estatus);
            return new ModelAndView("redirect:/hallEvents/"+idHall);
        }
        model.addObject("errorMessage", errorMessage);

        return model;
    }

    //
    //   PART OF HALL CONTROLLERS
    //

    @Transactional
    @GetMapping(value = { "/", "/index" } )
    public ModelAndView addEvent(Map<String, Object> model){

        List<HallResponse> halls = fillHallResponse(hallRepository.findAll());
        List<UserResponse> users = fillUserResponse(userRepository.findAll());
        ModelAndView NewModel = new ModelAndView("index");
        NewModel.addObject("halls",halls);
        NewModel.addObject("users",users);
        return  NewModel;
    }

    @Transactional
    @RequestMapping(value = { "/settings" }, method = RequestMethod.GET)
    public ModelAndView settings(Map<String, Object> model){
        List<ParameterResponse> parameters = fillParameterResponce(parameterRepository.findAll());
        ModelAndView NewModel = new ModelAndView("settings");
        SettingForm settingForm = new SettingForm();
        NewModel.addObject("parameters",parameters);
        NewModel.addObject("settingForm",settingForm);
    @Transactional
    @RequestMapping(value = { "/createDummies" }, method = RequestMethod.GET )
    public String addHall(){
        
<<<<<<< .mine
    	System.out.println("Here we go!");

=======
        return  NewModel;
    }
>>>>>>> .theirs
    	
    	DummyContentUtil dcu = new DummyContentUtil();
    	
    	List<User> users = dcu.generateDummyUsers();
    	
    	users.stream().forEach((u) -> {userRepository.saveUser(u);});
    	
        return  "OK";
    }

@Transactional
    @RequestMapping(value = { "/createDummies" }, method = RequestMethod.GET )
    public String createDummies(){

    	System.out.println("Here we go!");

    	
    	DummyContentUtil dcu = new DummyContentUtil();
    	
    	List<User> users = dcu.generateDummyUsers();
    	
    	users.stream().forEach((u) -> {userRepository.saveUser(u);});
    	
        return  "OK";
    }

    @Transactional
    @RequestMapping(value = { "/addHall" }, method = RequestMethod.GET )
    public ModelAndView addHall(ModelAndView model){
        ModelAndView newModel = new ModelAndView("addHall");
        HallForm hallForm = new HallForm();
        newModel.addObject("hallForm", hallForm);

        return  newModel;
    }

    @RequestMapping(value = { "/addHall" }, method = RequestMethod.POST)
    public ModelAndView addHall(ModelAndView model, @ModelAttribute("hallForm") HallForm hallForm) {

        Hall newHall = new Hall();
        newHall.setName(hallForm.getName());
        newHall.setDate(hallForm.getDate());
        hallRepository.saveHall(newHall);
        return new ModelAndView("redirect:/index");

    }


    @GetMapping("/deleteHall/{id}")
    public ModelAndView deleteHall(@PathVariable Integer id) {
        Hall tmphall =  hallRepository.getHallById(id);
        int hall_Id = tmphall.getId();
        hallRepository.deleteHallbyId(hall_Id);
        return new ModelAndView("redirect:/index");
    }

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @RequestMapping(value = { "/editHall/{id}" }, method = RequestMethod.GET)
    public ModelAndView editHall(@PathVariable Integer id) {
        ModelAndView newModel = new ModelAndView("editHall");
        HallForm hallForm = new HallForm();
        hall = hallRepository.getHallById(id);
        hallForm.setDate(hall.getDate());
        hallForm.setName(hall.getName());
        hallForm.setId(hall.getId());
        newModel.addObject("hallForm", hallForm);
        newModel.addObject("hall",hall);
        return  newModel;
    }


    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @RequestMapping(value = { "/editHall" }, method = RequestMethod.POST)
    public ModelAndView editHall(ModelAndView model,    @ModelAttribute("eventHall") HallForm hallForm) {
        Integer idHall = hall.getId();
        String nameHall = (hallForm.getName());
        Date date = hallForm.getDate();
        Set<Event> events = hall.getEventSet();
        if (idHall !=0   ) {
            hallRepository.updateHall(idHall,nameHall,date,events);
            return new ModelAndView("redirect:/index");
        }
        model.addObject("errorMessage", errorMessage);

        return model;
    }
//=========================================================================================================================
    //
    // USERS PART METHODS
    //
//=========================================================================================================================
@Transactional
@GetMapping(value = {  "/users" } )
public ModelAndView users(Map<String, Object> model){
    List<User> userList = userRepository.findAll();
    List<UserResponse> responses = new LinkedList<>();
        for (User user : userList )
            {
                UserResponse response = new UserResponse();
                response.setId(user.getId());
                response.setUsername(user.getUsername());
                response.setRole(user.getRole());
                response.setPassword(user.getPassword());

                responses.add(response);
            }

    ModelAndView NewModel = new ModelAndView("userList");
    NewModel.addObject("users",responses);
    return  NewModel;
}

    @GetMapping("/deleteUser/{id}")
    public ModelAndView deleteUser(@PathVariable Integer id) {
        userRepository.deleteUserbyId(id);
        return new ModelAndView("redirect:/index");
    }

    @Transactional
    @RequestMapping(value = { "/addUser" }, method = RequestMethod.GET )
    public ModelAndView addUser(ModelAndView model){
        ModelAndView newModel = new ModelAndView("addUser");
        UserForm userForm = new UserForm();
        newModel.addObject("userForm", userForm);
        return  newModel;
    }

    @RequestMapping(value = { "/addUser" }, method = RequestMethod.POST)
    public ModelAndView addUser(ModelAndView model, @ModelAttribute("userForm") UserForm userForm) {
        User newUser = new User();
        newUser.setName(userForm.getUsername());
        newUser.setUsername(userForm.getUsername());
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String password = passwordEncoder.encode(userForm.getPassword());
        newUser.setPassword(password);
        newUser.setRole(userForm.getRole());
        userRepository.saveUser(newUser);
        return new ModelAndView("redirect:/index");
    }

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @RequestMapping(value = { "/editUser/{id}" }, method = RequestMethod.GET)
    public ModelAndView editUser(@PathVariable Integer id) {
        ModelAndView newModel = new ModelAndView("editUser");
        UserForm userForm = new UserForm();
        user = userRepository.getUserById(id);
            userForm.setUsername(user.getUsername());
            userForm.setRole(user.getRole());
            userForm.setPassword(user.getPassword());
             newModel.addObject("userForm", userForm);
             newModel.addObject("user",user);
        return  newModel;
    }


    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @RequestMapping(value = { "/editUser" }, method = RequestMethod.POST)
    public ModelAndView editUser(ModelAndView model,    @ModelAttribute("userForm") UserForm userForm) {
        int id = user.getId();
        String name =userForm.getUsername();
        String email = userForm.getEmail();      
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String password = passwordEncoder.encode(userForm.getPassword());
        UserRole role =userForm.getRole();

        if (id !=0   ) {
            userRepository.updateUser(id,name,role, email, password);
            return new ModelAndView("redirect:/index");
        }
        model.addObject("errorMessage", errorMessage);

        return model;
    }
//===========================================================================================================================
  //
  // SUPPORT PART OF METHODS
  //
//===========================================================================================================================

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @RequestMapping(value = { "/getTime" }, method = RequestMethod.GET)
    public String getTime() {
        Date dateNow = new Date();
        SimpleDateFormat formatForDateNow = new SimpleDateFormat("yyyy.MM.dd");
        return  formatForDateNow.format(dateNow);
    }

    private List<StatusResponse> fillStatusResponse(List<StatusEvent> statusEventList) {
        List<StatusResponse> responses = new LinkedList<>();
            for(StatusEvent statusEvent : statusEventList){
                StatusResponse statusResponse = new StatusResponse();
                statusResponse.setColor(statusEvent.getColor());
                statusResponse.setStatus(statusEvent.getStatus());
                statusResponse.setId(statusEvent.getId());
                responses.add(statusResponse);
             }
            return responses;
    }
    private  List<HallResponse> fillHallResponse (List<Hall> hallList){

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
    private List<UserResponse> fillUserResponse(List<User> userList) {
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

    private List<ParameterResponse> fillParameterResponce(List<Parameter> parameterList) {
        List<ParameterResponse> parameters = new LinkedList<>();
        for(Parameter parameter:parameterList){
            ParameterResponse response = new ParameterResponse();
            response.setId(parameter.getId());
            response.setTextbackground(parameter.getTextbackground());
            response.setTextcolor(parameter.getTextcolor());
            response.setTextfont(parameter.getTextfont());
            response.setTextsize(parameter.getTextsize());
            response.setParameter(parameter.getParameter());
            parameters.add(response);
}
        return parameters;
    }
}

