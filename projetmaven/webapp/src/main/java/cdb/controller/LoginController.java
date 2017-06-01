package cdb.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class LoginController {

    private final Logger LOGGER = LoggerFactory.getLogger(LoginController.class);

    @RequestMapping(value = {"/login"}, method = RequestMethod.GET)
    public String login(ModelMap model, HttpServletRequest request, HttpServletResponse response) {
        if (request.getParameter("logout") != null) {
            model.addAttribute("isLoggingOut", true);
        }

        return "login";
    }

    @RequestMapping(value = {"/logout"}, method = RequestMethod.GET)
    public String logout(ModelMap model) {
        model.addAttribute("loggingOut", true);
        return "login";
    }

    /*
    @RequestMapping(method = RequestMethod.POST)
    public String validate() {

    }*/
}
