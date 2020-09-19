package timetable.controllers;


import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;

@Controller
public class errorHandler implements ErrorController {
    @Override
    public String getErrorPath() {
        return "/error";
    }

    @RequestMapping(value = {"/error-page"},method = RequestMethod.GET)
    public ModelAndView errorPage(){

        return new ModelAndView("errorPage");
    }

    @RequestMapping(value = {"/error500"},method = RequestMethod.GET)
    public ModelAndView errorPage500(){
        return new ModelAndView("error500");
    }

    @RequestMapping(value = {"/error404"},method = RequestMethod.GET)
    public ModelAndView errorPage404(){
        return new ModelAndView("error404");
    }

    @RequestMapping(value = {"/error403"},method = RequestMethod.GET)
    public ModelAndView errorPage403(){
        return new ModelAndView("error403");
    }


    @RequestMapping("/error")
    public ModelAndView handleError(HttpServletRequest request) {
        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);

        if (status != null) {
            Integer statusCode = Integer.valueOf(status.toString());

            if(statusCode == HttpStatus.NOT_FOUND.value()) {
                return new ModelAndView("redirect:/");//404
            }
            else if(statusCode == HttpStatus.INTERNAL_SERVER_ERROR.value()) {
                return new ModelAndView("redirect:/error500");//500
            }
            else if(statusCode == HttpStatus.FORBIDDEN.value()) {
                return new ModelAndView("redirect:/error403");//403
            }

        }
        return new ModelAndView("redirect:/error-page");
    }


}
