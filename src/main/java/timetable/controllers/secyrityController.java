
package timetable.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import timetable.entities.User;


import timetable.repository.UserRepo;
import timetable.responses.HallResponse;
import timetable.service.HallService;
import timetable.thymeleaf_form.UserForm;
import timetable.utils.FillForms;

import java.util.*;

import static java.util.Collections.*;


@RestController
@PreAuthorize("hasAuthority('SUPERADMIN')")
public class secyrityController {
    @Autowired
    UserRepo userRepo;

    @Autowired
    HallService hallRepository;



    @RequestMapping(value = {"indexUser"},method = RequestMethod.GET)
    public ModelAndView indexUser(){
        try {
            ModelAndView NewModel = new ModelAndView("indexUser");
            List<User> users = userRepo.findAll();
            Integer idhall = 0;
            List<HallResponse> halls = new LinkedList<>();
            halls = FillForms.fillHallResponse(hallRepository.findAll());
            if (halls != null) {
                idhall = halls.get(0).getId();
            }
            NewModel.addObject("idhall", idhall);
            NewModel.addObject("users", users);

            return NewModel;
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }
        return new ModelAndView("redirect:/index");
    }

    @GetMapping("/deleteUser/{id}")
    public ModelAndView deleteUser(@PathVariable Long id) {
        userRepo.deleteById(id);
        return new ModelAndView("redirect:/indexUser");
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
    public ModelAndView addUser(ModelAndView model, @ModelAttribute("userForm") UserForm userForm,User user) {

        user.setActive(true);
        user.setRoles(singleton(userForm.getRole()));

        userRepo.save(user);
        return new ModelAndView("redirect:/indexUser");
    }

    @RequestMapping(value = {"/editUser/{id}" }, method = RequestMethod.GET)
    public ModelAndView editUser(@PathVariable Long id) {
        ModelAndView model = new ModelAndView("editUser");
        User user = userRepo.findByid(id);
        UserForm userForm = new UserForm();
        userForm.setUsername(user.getUsername());
        model.addObject("user",user);
        model.addObject("userForm", userForm);
        return model;
    }

    @RequestMapping(value = { "/editUser" }, method = RequestMethod.POST)
    public ModelAndView editUser(ModelAndView model, @ModelAttribute("userForm") UserForm userForm,User user) {
        user.setUsername(userForm.getUsername());
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        user.setPassword(passwordEncoder.encode(userForm.getPassword()));
        user.setRoles(singleton(userForm.getRole()));
        userRepo.save(user);
        return new ModelAndView("redirect:/indexUser");
    }

}

