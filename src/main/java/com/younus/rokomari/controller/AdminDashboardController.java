package com.younus.rokomari.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AdminDashboardController {

    //dashboard api
    @GetMapping("/dashboard")
    public String dashboard() {
        return "pages/back-end/dashboard";
    }
}
