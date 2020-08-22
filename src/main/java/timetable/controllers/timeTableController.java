package timetable.controllers;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.tomcat.jni.Time;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import timetable.entities.*;
import timetable.responses.EventResponse;
import timetable.responses.*;
import timetable.service.*;
import timetable.thymeleaf_form.*;
import timetable.responses.HallResponse;
import timetable.service.EventService;
import timetable.service.HallService;
import timetable.thymeleaf_form.EventForm;
import timetable.thymeleaf_form.HallEventsForm;
import timetable.thymeleaf_form.HallForm;
import timetable.utils.FillForms;

import java.time.LocalDateTime;
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
    StatusEventService statusEventRepository;

    @Autowired
    ParameterService parameterRepository;

    private String formHall = "formHall";
    private String formTabletitle = "formTabletitle";
    private String formText ="formText";

    private volatile Event event;
    private volatile Hall hall;
    private volatile List<Parameter> parameterList;

    private volatile List<EventResponse> eventsresponse ;
    private volatile Date currentdate ;

    private Integer idHallbydefault;

    @Value("${error.message}")

    final private String hold = "hold";
    final private String pivot ="#";
    final private String timePivot=":";


    private String errorMessage;

    //
    //EVENTS PART
    //








    @RequestMapping(value = { "/upevent/{id}" }, method = RequestMethod.GET)
    public ModelAndView upevent(@PathVariable Integer id) {
        Event temporaryevent = eventRepository.getEventById(id);
        int tempordernumber = temporaryevent.getOrdernumber();
        List<Event> eventList = eventRepository.findAllWithDateandIdHallOrdered(temporaryevent.getDate(),temporaryevent.getIdHall());

                if(eventList.get(0).equals(temporaryevent)){
                    return new ModelAndView("redirect:/hallEvents");
                }
                Event swapevent = eventList.get(tempordernumber-1);
                    temporaryevent.setOrdernumber(swapevent.getOrdernumber());
                    swapevent.setOrdernumber(tempordernumber);
                    eventRepository.saveEvent(temporaryevent);
                    eventRepository.saveEvent(swapevent);


        return new ModelAndView("redirect:/hallEvents");
    }

    @RequestMapping(value = { "/downevent/{id}" }, method = RequestMethod.GET)
    public ModelAndView downevent(@PathVariable Integer id) {
        Event temporaryevent = eventRepository.getEventById(id);
        int tempordernumber = temporaryevent.getOrdernumber();
        List<Event> eventList = eventRepository.findAllWithDateandIdHallOrdered(temporaryevent.getDate(),temporaryevent.getIdHall());

                if(eventList.get(eventList.size()-1).equals(temporaryevent)){
                     return new ModelAndView("redirect:/hallEvents");
                 }
                    Event swapevent = eventList.get(tempordernumber+1);
                    temporaryevent.setOrdernumber(swapevent.getOrdernumber());
                    swapevent.setOrdernumber(tempordernumber);
                    eventRepository.saveEvent(temporaryevent);
                    eventRepository.saveEvent(swapevent);


        return new ModelAndView("redirect:/hallEvents");
    }

    @Transactional
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @RequestMapping(value = { "/showHall/{id}" }, method = RequestMethod.GET)
    public ModelAndView showHall(@PathVariable Integer id) {
        ModelAndView NewModel = new ModelAndView("showHall");
        Hall hall =hallRepository.getHallById(id);
        String  hallName = hall.getName();
        String hidencoloms = hall.getHiddencolloms();
        String[] hiddenColomns = new  String[0];

        if(hidencoloms!=null) {
            hiddenColomns = hall.getHiddencolloms().split(pivot);
        }

        List<ParameterResponse> listparameter =fillParameterResponce(parameterRepository.findAll());
        Date date =  new Date();
        List<EventResponse> eventsresponse = fillEventRenspose(eventRepository.findAllWithDateandIdHallandNohiddenOrdered(date,id,false));
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
    @RequestMapping(value = { "/refreshTable/{id}" }, method = RequestMethod.GET)
    public ModelAndView refreshTable(@PathVariable Integer id) {
        ModelAndView NewModel = new ModelAndView("refreshTable");
        Hall hall =hallRepository.getHallById(id);
        String  hallName = hall.getName();
        String[] hiddenColomns =  hall.getHiddencolloms().split(pivot);
        List<ParameterResponse> listparameter =fillParameterResponce(parameterRepository.findAll());
        Date date =  new Date();
        List<EventResponse> eventsresponse = fillEventRenspose(eventRepository.findAllWithDateandIdHallandNohiddenOrdered(date,id,false));
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
    @PreAuthorize("hasAuthority('SUPERADMIN')" + " || hasAuthority('ADMIN')" +"|| hasAuthority('USER')")
    @RequestMapping(value = { "/hallEvents" }, method = RequestMethod.GET)
    public ModelAndView HallEvents() {
        ModelAndView NewModel = new ModelAndView("hallEvents");
        List<HallResponse> halls = FillForms.fillHallResponse(hallRepository.findAll());
        List<StatusResponse> statuses = fillStatusResponse(statusEventRepository.findAll());
        HallEventsForm hallEventsForm = new HallEventsForm();
        EventForm eventForm = new EventForm();
        String hallName = new String();

        Date today = new Date();
         eventForm.setDate(today);

        int hallid = halls.get(0).getId();

                if(eventsresponse != null ){
                    eventsresponse = fillEventRenspose(eventRepository.findAllWithDateandIdHallOrdered(currentdate,hall.getId()));
                    NewModel.addObject("events",eventsresponse);

                }

        if(hall!=null){
            hallName = hall.getName();
        }

        NewModel.addObject("hallEventsForm",hallEventsForm);
        NewModel.addObject("eventForm",eventForm);
        NewModel.addObject("halls",halls);
        NewModel.addObject("statuses",statuses);
        NewModel.addObject("idhall",hallid);
        NewModel.addObject("hallName",hallName);

        return NewModel;
    }


   @DateTimeFormat(pattern = "yyyy-MM-dd")
   @PreAuthorize("hasAuthority('SUPERADMIN')" + " || hasAuthority('ADMIN')" +"|| hasAuthority('USER')")
   @RequestMapping(value = { "/hallEvents" }, method = RequestMethod.POST)
   public ModelAndView HallEvents( ModelAndView model,
                                       @ModelAttribute ("hallEventsForm") HallEventsForm halleventsForm,
                                       @ModelAttribute ("eventForm") EventForm eventForm) {

        HallEventsForm newEventsForm = new HallEventsForm();
        List<StatusResponse> statuses = fillStatusResponse(statusEventRepository.findAll());
        List<HallResponse> halls = FillForms.fillHallResponse(hallRepository.findAll());


        String hallName = new String();

        EventForm neweventForm = new EventForm();
            Date today = new Date();
            neweventForm.setDate(today);

       if(halleventsForm.getDateStart()!=null) {
          currentdate = halleventsForm.getDateStart();
          hall = hallRepository.findHallById(halleventsForm.getId());
          int id = halleventsForm.getId();
           hallName = hall.getName();
           eventsresponse = fillEventRenspose(eventRepository.findAllWithDateandIdHallOrdered(currentdate, id));
           hall =  hallRepository.getHallById(id);
           Integer hallid = id;
           model.addObject("eventForm",neweventForm);
           model.addObject("events",eventsresponse);
           model.addObject("hallName",hallName);
           model.addObject("idhall",hallid);
       }
       else if(eventForm.getDate()!=null){
           int idHall = eventForm.getHall_number();
           currentdate = eventForm.getDate();
           hall = hallRepository.getHallById(idHall);
           hallName = hall.getName();
           Event oldevent = eventRepository.findEventByNumber(eventForm.getNumber());
                 if(oldevent == null){
                     Integer lastnumber = eventRepository.findMaxOrderNumberByDate(eventForm.getDate(),eventForm.getHall_number());
                        if(lastnumber == null){
                             lastnumber=0;
                        }
                        else {
                            lastnumber++;
                        }
                     Event newEvent = fillEventfromEventForm(eventForm,lastnumber);
                     eventRepository.saveEvent(newEvent);
                 }

           eventsresponse = fillEventRenspose(eventRepository.findAllWithDateandIdHallOrdered(currentdate, idHall));
           halleventsForm.setDateStart(currentdate);
           halleventsForm.setId(idHall);
           model.addObject("eventForm",neweventForm);
           model.addObject("events",eventsresponse);
           model.addObject("hallName",hallName);
           model.addObject("idhall",idHall);
       }

       model.addObject("hallEventsForm",newEventsForm);
       model.addObject("halls",halls);
       model.addObject("statuses",statuses);
       model.addObject("hallName",hallName);

       return model;
   }



    @PreAuthorize("hasAuthority('SUPERADMIN')" + " || hasAuthority('ADMIN')" +"|| hasAuthority('USER')")
    @GetMapping("/delete/{id}")
     public ModelAndView delete(ModelAndView model,@PathVariable Integer id) {
        Event temporaryevent = eventRepository.getEventById(id);
        eventRepository.deleteEventbyId(id);
        List<Event> eventList = eventRepository.findAllWithDateandIdHallOrdered(temporaryevent.getDate(),temporaryevent.getIdHall());

            for(int i=temporaryevent.getOrdernumber();i<eventList.size();i++){
                Event event = eventList.get(i);
                int ordernumber = event.getOrdernumber();
                    ordernumber--;
                        event.setOrdernumber(ordernumber);
                        eventRepository.deleteEventbyId(event.getId());
                        eventRepository.saveEvent(event);
            }




        return new ModelAndView("redirect:/hallEvents");

    }

    @RequestMapping(value = {"/my-error-page"},method = RequestMethod.GET)
    public ModelAndView errorPage(){
        return new ModelAndView("errorPage");
    }

    @PreAuthorize("hasAuthority('SUPERADMIN')" + " || hasAuthority('ADMIN')" +"|| hasAuthority('USER')")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @RequestMapping(value = { "/editEvent/{id}" }, method = RequestMethod.GET)
    public ModelAndView editEvent(@PathVariable Integer id) {
        ModelAndView newModel = new ModelAndView("editEvent");
        EventForm eventForm = new EventForm();
        event = eventRepository.getEventById(id);

        Hall hall =  hallRepository.getHallById(event.getIdHall());
        String[] hiddenColomns = hall.getHiddencolloms().split(pivot);
        eventForm = sethideFields(hiddenColomns,eventForm);


        List<HallResponse> hallResponses = FillForms.fillHallResponse(hallRepository.findAll());
        List<StatusResponse> statuses = fillStatusResponse(statusEventRepository.findAll());

            eventForm.setIdEvent(id);
            eventForm.setDate(event.getDate());
            eventForm.setDescription(event.getDescription());
            eventForm.setHall_number(event.getIdHall());
            eventForm.setNumber(event.getNumber());
            eventForm.setEstatus (event.getIdStatus());
            eventForm.setComposition(event.getComposition());
            eventForm.setTime(event.getTime().toString());
            eventForm.setAdditionalstatus(event.getAdditionalstatus());
            eventForm.setContestation(event.getContestation());
            eventForm.setDefendant(event.getDefendant());
            eventForm.setPlaintiff(event.getPlaintiff());
            eventForm.setOrdernumber(event.getOrdernumber());
            eventForm.setHide(event.isHide());

        newModel.addObject("eventForm", eventForm);
        newModel.addObject("halls",hallResponses);
        newModel.addObject("statuses",statuses);
        return  newModel;
    }


    @PreAuthorize("hasAuthority('SUPERADMIN')" + " || hasAuthority('ADMIN')" +"|| hasAuthority('USER')")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @RequestMapping(value = { "/editEvent" }, method = RequestMethod.POST)
    public ModelAndView editEvent(ModelAndView model,    @ModelAttribute("eventForm") EventForm eventForm) {

        int idHall = eventForm.getHall_number();
        hall = hallRepository.getHallById(idHall);
        String hallName = hall.getName();

        Integer idEvent = eventForm.getIdEvent();
        Event oldEvent = eventRepository.findEventById(idEvent);
        Date oldDate = oldEvent.getDate();
        int oldIdHall = oldEvent.getIdHall();
        int oldorderNumber = oldEvent.getOrdernumber();

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-DD");
        String oldDatestr = format.format(oldEvent.getDate());
        String newDatestr = format.format(eventForm.getDate());


        Event newEvent =  new Event();



        hall.setName(hallName);
        hallRepository.saveHall(hall);

        //1. remove from one hall to other
            if(oldIdHall != eventForm.getHall_number()){
                int ordernumber = 0 ;
                    try {
                         ordernumber = eventRepository.findMaxOrderNumberByDate(eventForm.getDate(), eventForm.getHall_number());
                         ordernumber++;
                        }
                    catch (NullPointerException e){

                        }
                    newEvent = fillEventfromEventForm(eventForm,ordernumber);
                    eventRepository.saveEvent(newEvent);





                List<Event> eventList = eventRepository.findAllWithDateandIdHallOrdered(oldDate,oldIdHall);
                            if(eventList.size()==1){
                                Event event = eventList.get(0);
                                event.setOrdernumber(0);
                                int id = event.getId();
                                eventRepository.deleteEventbyId(event.getId());
                                eventRepository.saveEvent(event);

                            }
                            else {
                                for (int i = oldorderNumber; i < eventList.size(); i++) {
                                    Event event = eventList.get(i);
                                    int eventOrdernumber = event.getOrdernumber();
                                    eventOrdernumber--;
                                    event.setOrdernumber(eventOrdernumber);
                                    eventRepository.deleteEventbyId(event.getId());
                                    eventRepository.saveEvent(event);
                                }
                            }
            }

          //2. Edit event in one hall and one day
             else if(oldIdHall == eventForm.getHall_number() && oldDatestr.equals(newDatestr)){
                    newEvent = fillEventfromEventForm(eventForm,oldorderNumber);
                    eventRepository.saveEvent(newEvent);
            }

          //3. Edit event in one hall and different days
            else if(oldIdHall== eventForm.getHall_number() && !oldDatestr.equals(newDatestr)){
                int ordernumber = 0 ;
                    try {
                        ordernumber = eventRepository.findMaxOrderNumberByDate(eventForm.getDate(), eventForm.getHall_number());
                        ordernumber++;
                    }
                    catch (NullPointerException e){

                }
                newEvent = fillEventfromEventForm(eventForm,ordernumber);

                eventRepository.saveEvent(newEvent);

                List<Event> eventList = eventRepository.findAllWithDateandIdHallOrdered(oldDate,oldIdHall);
                if(eventList.size()==1){
                    Event event = eventList.get(0);
                    event.setOrdernumber(0);
                    int id = event.getId();
                    eventRepository.deleteEventbyId(event.getId());
                    eventRepository.saveEvent(event);

                }
                else {
                    for (int i = oldorderNumber; i < eventList.size(); i++) {
                        Event event = eventList.get(i);
                        int eventOrdernumber = event.getOrdernumber();
                        eventOrdernumber--;
                        event.setOrdernumber(eventOrdernumber);
                        eventRepository.deleteEventbyId(event.getId());
                        eventRepository.saveEvent(event);
                    }
                }

            }

            eventsresponse = fillEventRenspose(eventRepository.findAllWithDateandIdHallOrdered(currentdate, idHall));
            return new ModelAndView("redirect:/hallEvents");
    }

    //
    //   PART OF HALL CONTROLLERS
    //

    @PreAuthorize("hasAuthority('SUPERADMIN')" + " || hasAuthority('ADMIN')" +"|| hasAuthority('USER')")
    @GetMapping(value = {"/" } )
    public ModelAndView main(Map<String, Object> model){
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username =new String();
        if(principal instanceof UserDetails){
            username = ((UserDetails) principal). getUsername();
        }
        else{
            username = principal.toString();
        }
        ModelAndView newmodel = new ModelAndView("greeting");
        newmodel.addObject("username",username);
        return newmodel;
    }

    @PreAuthorize("hasAuthority('SUPERADMIN')" + " || hasAuthority('ADMIN')" +"|| hasAuthority('USER')")
    @GetMapping(value = {"/index"} )
    public ModelAndView index(Map<String, Object> model){
        List<HallResponse> halls = new LinkedList<>();
           halls = FillForms.fillHallResponse(hallRepository.findAll());

        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username =new String();
        if(principal instanceof UserDetails){
            username = ((UserDetails) principal).getUsername();
        }
        else{
            username = principal.toString();
        }


           Integer idhall =0;

            if(halls!=null) {
                 idhall = halls.get(0).getId();
            }
        ModelAndView NewModel = new ModelAndView("index");
        NewModel.addObject("halls",halls);
        NewModel.addObject("idhall",idhall);
        return  NewModel;
    }

    @RequestMapping(value = { "/settings/{idhall}" }, method = RequestMethod.GET)
    @PreAuthorize("hasAuthority('SUPERADMIN')" + " || hasAuthority('ADMIN')" )
    public ModelAndView settings(Map<String, Object> model,@PathVariable Integer idhall){
        idHallbydefault = idhall;
        Hall hall = hallRepository.getHallById(idhall);
        String hallName = hall.getName();
        String hidencoloms = hall.getHiddencolloms();
        String[] hidencolomns = new String[0];
        EventfieldsForm hidefieldsForm = new EventfieldsForm();
        Field[] fields = hidefieldsForm.getClass().getDeclaredFields();

            if(hidencoloms!=null) {
                hidencolomns = hall.getHiddencolloms().split(pivot);
            }
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
    @PreAuthorize("hasAuthority('SUPERADMIN')" + " || hasAuthority('ADMIN')" )
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
           else if(hallForm.getId()!=0){
               return new ModelAndView("redirect:/settings/"+hallForm.getId());
           }
           else {
              Hall hall = hallRepository.getHallById(idHallbydefault);
              String fields = hidenfieldsfill(hidefieldsForm);
              hall.setHiddencolloms(fields);
              hallRepository.saveHall(hall);
           }

        return new ModelAndView("redirect:/settings/"+idHallbydefault);
    }

    @PreAuthorize("hasAuthority('SUPERADMIN')" + " || hasAuthority('ADMIN')" )
    @RequestMapping(value = {"indexHall"},method = RequestMethod.GET)
    public ModelAndView indexHall(){
        ModelAndView NewModel = new ModelAndView("indexHall");
        List<HallResponse> halls = new LinkedList<>();
        halls = FillForms.fillHallResponse(hallRepository.findAll());
        Integer idhall =0;

        if(halls!=null) {
            idhall = halls.get(0).getId();
        }

        NewModel.addObject("halls",halls);
        NewModel.addObject("idhall",idhall);
        return NewModel;
    }

    @Transactional
    @PreAuthorize("hasAuthority('SUPERADMIN')" + " || hasAuthority('ADMIN')" )
    @RequestMapping(value = { "/addHall" }, method = RequestMethod.GET )
    public ModelAndView addHall(ModelAndView model){
        ModelAndView newModel = new ModelAndView("addHall");
        HallForm hallForm = new HallForm();
        newModel.addObject("hallForm", hallForm);

        return  newModel;
    }

    @PreAuthorize("hasAuthority('SUPERADMIN')" + " || hasAuthority('ADMIN')" )
    @RequestMapping(value = { "/addHall" }, method = RequestMethod.POST)
    public ModelAndView addHall(ModelAndView model, @ModelAttribute("hallForm") HallForm hallForm) {

        Hall newHall = new Hall();
        newHall.setName(hallForm.getName());
        newHall.setDate(hallForm.getDate());
        hallRepository.saveHall(newHall);
        return new ModelAndView("redirect:/indexHall");

    }

    @PreAuthorize("hasAuthority('SUPERADMIN')" + " || hasAuthority('ADMIN')" )
    @GetMapping("/deleteHall/{id}")
    public ModelAndView deleteHall(@PathVariable Integer id) {
        Hall tmphall =  hallRepository.getHallById(id);
        int hall_Id = tmphall.getId();
        hallRepository.deleteHallbyId(hall_Id);
        return new ModelAndView("redirect:/indexHall");
    }

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @PreAuthorize("hasAuthority('SUPERADMIN')" + " || hasAuthority('ADMIN')" )
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
    @PreAuthorize("hasAuthority('SUPERADMIN')" + " || hasAuthority('ADMIN')" )
    @RequestMapping(value = { "/editHall" }, method = RequestMethod.POST)
    public ModelAndView editHall(ModelAndView model,    @ModelAttribute("eventHall") HallForm hallForm) {
        String hiddencolloms = new String();
        Integer idHall = hall.getId();
        String nameHall = (hallForm.getName());
        Date date = hallForm.getDate();
        Set<Event> events = hall.getEventSet();
        if (idHall !=0   ) {
            hallRepository.updateHall(idHall,nameHall,date,events,hiddencolloms);
            return new ModelAndView("redirect:/indexHall");
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
        SimpleDateFormat formatForTimeNow = new SimpleDateFormat("HH:mm:ss");
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

    private Event fillEventfromEventForm(EventForm eventForm,Integer lastordernumber) {

        if(lastordernumber == null){
                lastordernumber =0;
            }
        Event newEvent = new Event();
        newEvent.setId(eventForm.getIdEvent());
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
        newEvent.setHide(eventForm.isHide());
        newEvent.setDate(eventForm.getDate());
        newEvent.setOrdernumber(lastordernumber);
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
                response.setDate(event.getDate().toString());
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
                response.setTime(timeformater(event));
                response.setHide(event.isHide());
                response.setOrdernymber(event.getOrdernumber());
                eventsresponse.add(response);
            }
        return eventsresponse;
    }

    private String timeformater (Event event){
        String time = new String();
            time = time+event.getTime().getHours()+":";//+event.getTime().getMinutes();
            if( event.getTime().getMinutes()<10){
                time = time+"0"+ event.getTime().getMinutes();
            }
            else {
                time =time+ event.getTime().getMinutes();
            }
        return time;
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

