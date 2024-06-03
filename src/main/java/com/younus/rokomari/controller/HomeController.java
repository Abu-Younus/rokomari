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

    //category page show api
    @GetMapping("/category")
    public String category() {
        return "pages/front-end/category/category";
    }

    //product details show api
    @GetMapping("/product-details")
    public String productDetails() {
        return "pages/front-end/product/details";
    }
}
