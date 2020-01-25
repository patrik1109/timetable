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

import java.lang.reflect.Array;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@RestController
public class timeTableController {
    @Autowired
    EventService eventRepository;

    @Autowired
    HallService hallRepository;

    private Event event;
    @Value("${error.message}")

    private String errorMessage;

    @Transactional
    @GetMapping(value = { "/", "/index" } )
    public ModelAndView getBooks(Map<String, Object> model){
        List<Hall> hallList = hallRepository.findAll();
        List<HallResponse> halls = fillResponse(hallList);
        ModelAndView NewModel = new ModelAndView("index");
        NewModel.addObject("halls",halls);
        return  NewModel;
    }

    @Transactional
    @RequestMapping(value = { "/AddEvent" }, method = RequestMethod.GET )
    public ModelAndView getBooks(ModelAndView model){
        ModelAndView newModel = new ModelAndView("AddEvent");
        List<Hall> halls = hallRepository.findAll();
        List<HallResponse> hallResponses = fillResponse(halls);


        EventForm eventForm = new EventForm();
        newModel.addObject("eventForm", eventForm);
        newModel.addObject("halls",hallResponses);
        //newModel.addObject("eventsStatus",eventsStatus);
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
        int idHall = 0;
        List<Hall> halls = hallRepository.findAll();
        List<HallResponse> hallResponses = fillResponse(halls);
        event = eventRepository.getEventById(id);
            eventForm.setDate(event.getDate());
            eventForm.setDescription(event.getDescription());
            eventForm.setHall_number(event.getIdHall());
            eventForm.setNumber(event.getNumber());
        idHall = event.getIdHall();
        newModel.addObject("idHall",idHall);
        newModel.addObject("eventForm", eventForm);
        newModel.addObject("halls",hallResponses);
        return  newModel;
    }

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @RequestMapping(value = { "/editEvent" }, method = RequestMethod.POST)
    public ModelAndView editEvent(ModelAndView model,    @ModelAttribute("eventForm") EventForm eventForm) {
        Integer idEvent = event.getId();
        Integer idHall = eventForm.getHall_number();
        String numberEvent = eventForm.getNumber();
        String description = eventForm.getDescription();
        Date date= eventForm.getDate();
        if (idEvent !=0   ) {
            eventRepository.updateEvent(idEvent,numberEvent,description,date,idHall);
            return new ModelAndView("redirect:/hallEvents/"+idHall);
        }
        model.addObject("errorMessage", errorMessage);

        return model;
    }
    @GetMapping("/deleteHall/{id}")
    public ModelAndView deleteHall(@PathVariable Integer id) {
        Hall tmphall =  hallRepository.getHallById(id);
        int hall_Id = tmphall.getId();
        hallRepository.deleteHallbyId(hall_Id);
        return new ModelAndView("redirect:/index");
    }



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
