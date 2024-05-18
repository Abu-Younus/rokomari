package com.younus.rokomari.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    //application home
    @GetMapping("")
    public String home() {
        return "pages/front-end/home";
    }
}
