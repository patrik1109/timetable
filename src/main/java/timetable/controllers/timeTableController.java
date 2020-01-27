package timetable.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import timetable.entities.Event;
import timetable.entities.Hall;
import timetable.enums.EventStatus;
import timetable.responses.EventResponse;
import timetable.responses.HallResponse;
import timetable.service.EventService;
import timetable.service.HallService;
import timetable.thymeleaf_form.EventForm;
import timetable.thymeleaf_form.HallForm;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Array;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.util.*;

@RestController
public class timeTableController {
    @Autowired
    EventService eventRepository;

    @Autowired
    HallService hallRepository;

    private volatile Event event;
    private volatile Hall hall;
    @Value("${error.message}")

    private String errorMessage;

    //
    //EVENTS PART
    //


    @Transactional
    @RequestMapping(value = { "/AddEvent" }, method = RequestMethod.GET )
    public ModelAndView addEvent(ModelAndView model){
        ModelAndView newModel = new ModelAndView("AddEvent");
        List<Hall> halls = hallRepository.findAll();
        List<HallResponse> hallResponses = fillResponse(halls);
        EventForm eventForm = new EventForm();
        newModel.addObject("eventForm", eventForm);
        newModel.addObject("halls",hallResponses);
        return  newModel;
    }

    
    @RequestMapping(value = { "/AddEvent" }, method = RequestMethod.POST)
    public ModelAndView addEvent(ModelAndView model, @ModelAttribute("eventForm") EventForm eventForm) {
        Event newEvent = new Event();
        newEvent.setDate(eventForm.getDate());   
        
        newEvent.setDescription(eventForm.getDescription());
        newEvent.setIdHall(eventForm.getHall_number());
        newEvent.setNumber(eventForm.getNumber());
        newEvent.setStatus(eventForm.getestatus());
        eventRepository.saveEvent(newEvent);
        int hall_Id = eventForm.getHall_number();
        return new ModelAndView("redirect:/hallEvents/"+hall_Id);
    }

    @Transactional
    @RequestMapping(value = { "/showHall/{id}" }, method = RequestMethod.GET)
    public ModelAndView showHall(@PathVariable Integer id) {
        ModelAndView NewModel = new ModelAndView("showHall");
        String  hallName = hallRepository.getHallById(id).getName();
        List<Event> events = eventRepository.findAll();
        List<EventResponse> eventsresponse = new LinkedList<>();
        for (Event event: events) {
            if(event.getIdHall()==id) {
                EventResponse response = new EventResponse();
                response.setDate(event.getDate());
                response.setDescription(event.getDescription());
                response.setNumber(event.getNumber());
                response.setStatus(event.getStatus());
                eventsresponse.add(response);
            }
        }
        NewModel.addObject("events",eventsresponse);
        NewModel.addObject("hallName",hallName);
        return NewModel;
    }
    @Transactional
    @RequestMapping(value = { "/hallEvents/{id}" }, method = RequestMethod.GET)
    public ModelAndView editHallEvents(@PathVariable Integer id) {
        ModelAndView NewModel = new ModelAndView("hallEvents");
        String  hallName = hallRepository.getHallById(id).getName();
        List<Event> events = eventRepository.findAll();
        List<EventResponse> eventsresponse = new LinkedList<>();
        for (Event event: events) {
            if(event.getIdHall()==id) {
                EventResponse response = new EventResponse();
                response.setDate(event.getDate());
                response.setDescription(event.getDescription());
                response.setNumber(event.getNumber());
                response.setId(event.getId());
                response.setStatus(event.getStatus());
                eventsresponse.add(response);
            }
        }
        NewModel.addObject("events",eventsresponse);
        NewModel.addObject("hallName",hallName);
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
        EventStatus[] statuses =EventStatus.values();
        List<Hall> halls = hallRepository.findAll();
        List<HallResponse> hallResponses = fillResponse(halls);
        event = eventRepository.getEventById(id);
            eventForm.setDate(event.getDate());
            eventForm.setDescription(event.getDescription());
            eventForm.setHall_number(event.getIdHall());
            eventForm.setNumber(event.getNumber());
            eventForm.setestatus(event.getStatus());
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
        EventStatus estatus = eventForm.getestatus();
        EventStatus status = eventForm.getestatus();
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
        List<Hall> hallList = hallRepository.findAll();
        List<HallResponse> halls = fillResponse(hallList);
        ModelAndView NewModel = new ModelAndView("index");
        NewModel.addObject("halls",halls);
        return  NewModel;
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

  //
  // SUPPORT PART OF METHODS
  //


    private  List<HallResponse> fillResponse (List<Hall> hallList){

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
}

