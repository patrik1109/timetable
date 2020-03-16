package timetable.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import timetable.entities.*;
import timetable.enums.UserRole;
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

import java.sql.Time;
import java.util.*;

import java.lang.reflect.Field;
import java.text.SimpleDateFormat;

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

    private String formHall = "formHall";
    private String formTabletitle = "formTabletitle";
    private String formText ="formText";

    private volatile Event event;
    private volatile Hall hall;
    private volatile User user;
    private volatile List<Parameter> parameterList;
    private Integer idHallbydefault;
    @Value("${error.message}")

    final private String hold = "hold";
    final private String pivot ="#";
    final private String timePivot=":";


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
        newEvent.setDate(eventForm.getDate());
        newEvent.setDescription(eventForm.getDescription());
        newEvent.setIdHall(eventForm.getHall_number());
        newEvent.setNumber(eventForm.getNumber());
        newEvent.setIdStatus(eventForm.getEstatus());
        newEvent.setComposition(eventForm.getComposition());
        eventRepository.saveEvent(newEvent);
        int hall_Id = eventForm.getHall_number();
        return new ModelAndView("redirect:/hallEvents");
    }


    @Transactional
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @RequestMapping(value = { "/showHall/{id}" }, method = RequestMethod.GET)
    public ModelAndView showHall(@PathVariable Integer id) {
        ModelAndView NewModel = new ModelAndView("showHall");
        Hall hall =hallRepository.getHallById(id);
        String  hallName = hall.getName();
        String[] hiddenColomns =  hall.getHiddencolloms().split(pivot);
        List<ParameterResponse> listparameter =fillParameterResponce(parameterRepository.findAll());
        Date date =  new Date();
        List<EventResponse> eventsresponse = fillEventRenspose(eventRepository.findAllByDateAndIdHall(date,id) );
        NewModel.addObject("events",eventsresponse);
        NewModel.addObject("hallName",hallName);
        NewModel.addObject("dateTime",date);
        NewModel.addObject("hiddenColomns",hiddenColomns);
        if(listparameter != null && !listparameter.isEmpty() && listparameter.size()<4 ) {
            
        	ParameterResponse parameterHall = listparameter.get(0);
        	ParameterResponse parameterTableTitle =  listparameter.get(1);
        	ParameterResponse parameterText = listparameter.get(2);

        	NewModel.addObject("parameterHall",parameterHall);
        	NewModel.addObject("parameterTableTitle",parameterTableTitle );
        	NewModel.addObject("parameterText",parameterText);
        }
        else {
            ParameterResponse parameterHall = fillParameterResponcebyDefault();
            ParameterResponse parameterTableTitle =  fillParameterResponcebyDefault();
            ParameterResponse parameterText = fillParameterResponcebyDefault();
            NewModel.addObject("parameterHall",parameterHall);
            NewModel.addObject("parameterTableTitle",parameterTableTitle );
            NewModel.addObject("parameterText",parameterText);

        }
        return NewModel;
    }


    @Transactional
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @RequestMapping(value = { "/hallEvents" }, method = RequestMethod.GET)
    public ModelAndView editHallEvents() {
        ModelAndView NewModel = new ModelAndView("hallEvents");
        List<HallResponse> halls = fillHallResponse(hallRepository.findAll());
        List<StatusResponse> statuses = fillStatusResponse(statusEventRepository.findAll());
        HallEventsForm hallEventsForm = new HallEventsForm();
        EventForm eventForm = new EventForm();
        NewModel.addObject("hallEventsForm",hallEventsForm);
        NewModel.addObject("eventForm",eventForm);
        NewModel.addObject("halls",halls);
        NewModel.addObject("statuses",statuses);
        return NewModel;
    }

   @DateTimeFormat(pattern = "yyyy-MM-dd")
   @RequestMapping(value = { "/hallEvents" }, method = RequestMethod.POST)
   public ModelAndView editHallEvents( ModelAndView model,
                                       @ModelAttribute ("hallEventsForm") HallEventsForm halleventsForm,
                                       @ModelAttribute ("eventForm") EventForm eventForm) {

        HallEventsForm newEventsForm = new HallEventsForm();
        List<StatusResponse> statuses = fillStatusResponse(statusEventRepository.findAll());
        List<HallResponse> halls = fillHallResponse(hallRepository.findAll());
        EventForm neweventForm = new EventForm();
       if(halleventsForm.getDateStart()!=null) {
           Date dateStart = halleventsForm.getDateStart();
           int id = halleventsForm.getId();
           List<EventResponse> eventsresponse = fillEventRenspose(eventRepository.findAllByDateAndIdHall(dateStart, id));
           Hall hall =  hallRepository.getHallById(id);
           String hallName = hall.getName();
           Integer hallid = id;

           model.addObject("eventForm",neweventForm);
           model.addObject("events",eventsresponse);
           model.addObject("hallName",hallName);
           model.addObject("hallid",hallid);
       }
       else if(eventForm.getDate()!=null){
           int idHall = eventForm.getHall_number();
           Date date = eventForm.getDate();
           Hall hall = hallRepository.getHallById(idHall);
           String hallName = hall.getName();
           Event newEvent =  fillEventfromEventForm(eventForm);
           hallRepository.saveHall(hall);
           eventRepository.saveEvent(newEvent);
           List<EventResponse> eventsresponse = fillEventRenspose(eventRepository.findAllByDateAndIdHall(date, idHall));
           halleventsForm.setDateStart(date);
           halleventsForm.setId(idHall);
           model.addObject("eventForm",neweventForm);
           model.addObject("events",eventsresponse);
           model.addObject("hallName",hallName);
           model.addObject("hallid",idHall);
       }
       model.addObject("hallEventsForm",newEventsForm);
       model.addObject("halls",halls);
       model.addObject("statuses",statuses);
       return model;
   }




    @GetMapping("/delete/{id}")
     public ModelAndView delete(@PathVariable Integer id) {
        Event tmpevent = eventRepository.getEventById(id);
        int hall_Id = tmpevent.getIdHall();
        eventRepository.deleteEventbyId(id);

        return new ModelAndView("redirect:/hallEvents");
    }

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @RequestMapping(value = { "/editEvent/{id}" }, method = RequestMethod.GET)
    public ModelAndView editEvent(@PathVariable Integer id) {
        ModelAndView newModel = new ModelAndView("editEvent");
        EventForm eventForm = new EventForm();
        event = eventRepository.getEventById(id);

        Hall hall =  hallRepository.getHallById(event.getIdHall());
        String[] hiddenColomns = hall.getHiddencolloms().split(pivot);
        eventForm = sethideFields(hiddenColomns,eventForm);


        List<HallResponse> hallResponses = fillHallResponse(hallRepository.findAll());
        List<StatusResponse> statuses = fillStatusResponse(statusEventRepository.findAll());


            eventForm.setDate(event.getDate());
            eventForm.setDescription(event.getDescription());
            eventForm.setHall_number(event.getIdHall());
            eventForm.setNumber(event.getNumber());
            eventForm.setEstatus (event.getIdStatus());
            eventForm.setComposition(event.getComposition());
            eventForm.setTime(event.getTime().toString());

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
        String composition = (eventForm.getComposition());
        int estatus = eventForm.getEstatus();
        Date date= eventForm.getDate();

        Date time = new Date();
        String[] hours = (eventForm.getTime().split(timePivot));
        time.setHours(Integer.parseInt(hours[0]));
        time.setMinutes(Integer.parseInt(hours[1]));

        String defendant = eventForm.getDefendant();
        String plaintiff = eventForm.getPlaintiff();
        String contestation = eventForm.getContestation();
        String additionalstatus = eventForm.getAdditionalstatus();

        if (idEvent !=0   ) {
            eventRepository.updateEvent(idEvent,numberEvent,time,defendant,plaintiff,contestation,description,date,composition,additionalstatus,estatus,idHall);
            return new ModelAndView("redirect:/hallEvents");
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
        //final String currentUserName = SecurityContextHolder.getContext().getAuthentication().getName();
        //String userName = SecurityUtils.getUserName();
        List<HallResponse> halls = fillHallResponse(hallRepository.findAll());
        List<UserResponse> users = fillUserResponse(userRepository.findAll());
        Integer idhall = halls.get(0).getId();
        ModelAndView NewModel = new ModelAndView("index");
        NewModel.addObject("halls",halls);
        NewModel.addObject("users",users);
        NewModel.addObject("idhall",idhall);
        return  NewModel;
    }

   
    

    @RequestMapping(value = { "/settings/{idhall}" }, method = RequestMethod.GET)
    public ModelAndView settings(Map<String, Object> model,@PathVariable Integer idhall){
        idHallbydefault = idhall;
        Hall hall = hallRepository.getHallById(idhall);
        String[] hidencolomns = hall.getHiddencolloms().split(pivot);
        String hallName = hall.getName();
        EventfieldsForm hidefieldsForm = new EventfieldsForm();

        Field[] fields = hidefieldsForm.getClass().getDeclaredFields();

        for(String str : hidencolomns){
            for(Field field :fields){
                if(field.getName().equals(str)){
                    try {
                        field.setAccessible(true);
                        field.set(hidefieldsForm,true);
                    }
                    catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
            }
        }


        parameterList = parameterRepository.findAll();
        List<Hall> hallList = hallRepository.findAll();
        List<ParameterResponse> parameters = fillParameterResponce(parameterList);
        ModelAndView NewModel = new ModelAndView("settings");

        ParameterResponse responseSettingHall = parameters.get(0);
        ParameterResponse responseSettingTable = parameters.get(1);
        ParameterResponse responseSettingText = parameters.get(2);

        SettingForm settingFormHall = fillSettingForm(formHall,responseSettingHall);
        SettingForm settingFormTableTitle = fillSettingForm(formTabletitle,responseSettingTable);
        SettingForm settingText = fillSettingForm(formText,responseSettingText);

        HallForm hallForm = new HallForm();
        hallForm.setId(idhall);
        hallForm.setName(hallName);

        NewModel.addObject("settingFormHall",settingFormHall);
        NewModel.addObject("settingFormTableTitle",settingFormTableTitle);
        NewModel.addObject("hallForm",hallForm);
        NewModel.addObject("settingText",settingText);
        NewModel.addObject("hidefieldsForm",hidefieldsForm);
        NewModel.addObject("hallList",hallList);
        NewModel.addObject("hallName",hallName);
        return  NewModel;
    }
    @Transactional
    @RequestMapping(value = { "/settings" }, method = RequestMethod.POST)
    public ModelAndView settings(ModelAndView model,
                                 @ModelAttribute("settingForm") SettingForm settingFormHall,
                                 @ModelAttribute("hidefieldsForm")EventfieldsForm hidefieldsForm,
                                 @ModelAttribute("hallForm")HallForm hallForm ) {


            if(settingFormHall.getFormname() != null) {
                Parameter parameter = new Parameter();
                if (settingFormHall.getFormname().equals(formHall)) {
                    parameter = getFromForm(settingFormHall);
                    parameterRepository.saveParameter(parameter);
                } else if (settingFormHall.getFormname().equals(formTabletitle)) {
                    parameter = getFromForm(settingFormHall);
                    parameterRepository.saveParameter(parameter);
                } else if (settingFormHall.getFormname().equals(formText)) {
                    parameter = getFromForm(settingFormHall);
                    parameterRepository.saveParameter(parameter);
                }
            }
            //show hiden colomns in hall
           else if(hallForm.getId()!=0){
               return new ModelAndView("redirect:/settings/"+hallForm.getId());
           }
           else {
              //Hall hall = hallRepository.getHallById(hidefieldsForm.getIdHall());
              Hall hall = hallRepository.getHallById(idHallbydefault);
              String fields = hidenfieldsfill(hidefieldsForm);
              hall.setHiddencolloms(fields);
              hallRepository.saveHall(hall);
           }

        return new ModelAndView("redirect:/settings/"+idHallbydefault);
    }

    @Transactional
    @RequestMapping(value = { "/createDummies" }, method = RequestMethod.GET )
    public String createDummies() {
    	
    	List<User> users = DummyContentUtil.generateDummyUsers();
    	
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
        String hiddencolloms = new String();
        Integer idHall = hall.getId();
        String nameHall = (hallForm.getName());
        Date date = hallForm.getDate();
        Set<Event> events = hall.getEventSet();
        if (idHall !=0   ) {
            hallRepository.updateHall(idHall,nameHall,date,events,hiddencolloms);
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
    List<UserResponse> responses =  new LinkedList<>();
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

   // @DateTimeFormat(pattern = "yyyy-MM-dd")
    @RequestMapping(value = { "/getTime" }, method = RequestMethod.GET)
    public String getTime() {
        Date dateNow = new Date();
        SimpleDateFormat formatForDateNow = new SimpleDateFormat("yyyy.MM.dd");
        SimpleDateFormat formatForTimeNow = new SimpleDateFormat("hh:mm:ss");
        return  formatForDateNow.format(dateNow)+"<br>"+formatForTimeNow.format(dateNow);
    }

    private  List<?> fillRes(Class clazz,List<?>tlist){
       return fillResponse(clazz,tlist);
    }

    private <T> List<T> fillResponse(Class claz,List<T> tList)
    {
    	/*List<T> responses = new LinkedList<>();
        Class responsesclass = responses.getClass();*/

    	List<T> responses = new LinkedList<>();
        Class responsesclass = claz.getClass();

    	Class clazz = tList.getClass();
    	Field[] fields = clazz.getDeclaredFields();


        for(Field field:fields){
            try
                {
                    responsesclass.newInstance();
                }
                catch (InstantiationException e) {
                    e.printStackTrace();
                }
                catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
                Field[] responsefields = responsesclass.getDeclaredFields();
                    for(Field responsefield:responsefields){
                        if(field.getName().equals(responsefield.getName())){
                            responsefield = field;
                        }
                    }
                    responses.add((T) responsesclass);
            }
        return responses;

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

    private String hidenfieldsfill(Object eventForm){
        Field[] fields = eventForm.getClass().getDeclaredFields();
        StringBuffer buffer = new StringBuffer();
        try {
            for (Field field : fields) {
                field.setAccessible(true);
                String name = field.getName();
                Boolean value = (Boolean) field.get(eventForm);
                      if (value != null && value) {
                             buffer.append(name);
                             buffer.append(pivot);
                     }
            }
        }
        catch (Exception  e){
            System.out.println(e.getStackTrace());
        }
        return buffer.toString();
    }

    private Event fillEventfromEventForm(EventForm eventForm) {
        Event newEvent = new Event();
        newEvent.setNumber(eventForm.getNumber());
        newEvent.setIdHall(eventForm.getHall_number());
        newEvent.setDescription(eventForm.getDescription());
        newEvent.setIdStatus(eventForm.getEstatus());
        newEvent.setComposition(eventForm.getComposition());
        newEvent.setContestation(eventForm.getContestation());
        newEvent.setDefendant(eventForm.getDefendant());
        newEvent.setPlaintiff(eventForm.getPlaintiff());
        newEvent.setAdditionalstatus(eventForm.getAdditionalstatus());

        Date time = eventForm.getDate();
        String[] hours = (eventForm.getTime().split(timePivot));
        time.setHours(Integer.parseInt(hours[0]));
        time.setMinutes(Integer.parseInt(hours[1]));
        newEvent.setTime(time);

        newEvent.setDate(eventForm.getDate());
        return newEvent;
    }

    private ParameterResponse fillParameterResponcebyDefault() {
            ParameterResponse response = new ParameterResponse();
            response.setTextbackground("#4cafff");
            response.setTextcolor("#ff0000");
            response.setTextfont("Arial");
            response.setTextsize(20);
          return response;
    }

    private List<EventResponse> fillEventRenspose(List<Event> eventList) {

        List<EventResponse> eventsresponse = new ArrayList<>();
        for (Event event: eventList) {

                EventResponse response = new EventResponse();
                StatusEvent statusEvent = statusEventRepository.getStatusEventById(event.getIdStatus());
                response.setIdHall(event.getIdHall());
                response.setDate(event.getDate());
                response.setDescription(event.getDescription());
                response.setComposition(event.getComposition());
                response.setNumber(event.getNumber());
                response.setColor(statusEvent.getColor());
                response.setStatus(statusEvent.getStatus());
                response.setId(event.getId());
                response.setAdditionalstatus(event.getAdditionalstatus());
                response.setContestation(event.getContestation());
                response.setDefendant(event.getDefendant());
                response.setPlaintiff(event.getPlaintiff());
                Date time =event.getTime();
                response.setTime(event.getTime().toString());
                eventsresponse.add(response);
            }
        return eventsresponse;
    }

    private EventForm sethideFields(String[] hiddenColomns, EventForm neweventForm) {
        Field[] formfields = neweventForm.getClass().getDeclaredFields();

        for (String str:hiddenColomns) {
            for (Field field : formfields) {
                String name =field.getName();
                if(name.contains(hold)&&name.contains(str)){
                    try {
                        field.setAccessible(true);
                        field.set(neweventForm, true);
                    }
                    catch ( IllegalAccessException e){
                        e.printStackTrace();
                    }
                }

            }
        }
        return neweventForm;
    }


    private static SettingForm fillSettingForm(String formName, ParameterResponse response){
        SettingForm newForm = new SettingForm();
        newForm.setFormname(formName);
        newForm.setParameter(response.getParameter());
        newForm.setTextbackground(response.getTextbackground());
        newForm.setTextcolor(response.getTextcolor());
        newForm.setTextfont(response.getTextfont());
        newForm.setTextsize(response.getTextsize());
        newForm.setId(response.getId());
        return newForm;
    }

    private Parameter getFromForm(SettingForm settingForm) {
        Parameter newparameter = new Parameter();
        newparameter.setTextbackground(settingForm.getTextbackground());
        newparameter.setTextcolor(settingForm.getTextcolor());
        newparameter.setTextfont(settingForm.getTextfont());
        newparameter.setTextsize(settingForm.getTextsize());
        newparameter.setParameter(settingForm.getParameter());
        newparameter.setId(settingForm.getId());
        return  newparameter;
    }



}

