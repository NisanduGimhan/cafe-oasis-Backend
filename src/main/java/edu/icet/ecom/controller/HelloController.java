package edu.icet.ecom.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @GetMapping("/")
    public String greet(HttpServletRequest request) {
        return "Welcome User"+request.getSession().getId();
    }

    @GetMapping("/api/greet")
    public String apiGreet() {
        return "Welcome User (API)";
    }
}
