package com.gft.ControleStarterMVC.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class AppController {

    @GetMapping("")
    public ModelAndView viewHomePage() {
        ModelAndView mv = new ModelAndView("index.html");
        return mv;
    }

    @GetMapping("login")
    public String viewLoginPage() {
        return "login";
    }
}

