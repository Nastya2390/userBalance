package com.example.userBalance.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DefaultController {

    @GetMapping("/")
    public String geDefaultPage() {
        StringBuilder builder = new StringBuilder();
        builder.append("Go to database console - http://localhost:8080/h2-console").append("<br>")
               .append("Available requests examples: ").append("<br>")
               .append("Get balance GET - http://localhost:8080/balance/1").append("<br>")
               .append("Increase balance POST - http://localhost:8080/balance/increase").append("<br>")
               .append("Decrease balance POST - http://localhost:8080/balance/decrease");
        return builder.toString();
    }

}
