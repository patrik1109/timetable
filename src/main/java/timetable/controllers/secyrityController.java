package timetable.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import timetable.entities.User;
import timetable.enums.UserRole;
import timetable.repository.UserRepository;
import timetable.responses.HallResponse;
import timetable.responses.UserResponse;
import timetable.service.HallService;
import timetable.service.UserService;
import timetable.thymeleaf_form.UserForm;
import timetable.utils.FillForms;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@RestController
@PreAuthorize("hasAuthority('ADMIN')")
public class secyrityController {
    @Autowired
    UserService userRepository;

    @Autowired
    UserRepository userRepo;

    @Autowired
    HallService hallRepository;

    private volatile User user;

    @RequestMapping(value = {"indexUser"},method = RequestMethod.GET)
    public ModelAndView indexUser(){
        ModelAndView NewModel = new ModelAndView("indexUser");
        List<UserResponse> users = new LinkedList<>();
        Integer idhall = 0;
        List<HallResponse> halls = new LinkedList<>();

        users =  FillForms.fillUserResponse(userRepository.findAll());
        halls = FillForms.fillHallResponse(hallRepository.findAll());
        if(halls!=null) {
            idhall = halls.get(0).getId();
        }
        NewModel.addObject("idhall",idhall);
        NewModel.addObject("users",users);

        return NewModel;
    }

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
        model.addObject("errorMessage", FillForms.errorMessage);

        return model;
    }
}
