package com.tavi.duongnt.user_service.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class ViewController {
    @RequestMapping(value = {"/home"}, method = RequestMethod.GET)
    public String home(Model model) {
        return "home";
    }
    @RequestMapping(value = {"/", "/login"}, method = RequestMethod.GET)
    public String login(Model model) {
        System.out.println("login");
        return "login";
    }
}
