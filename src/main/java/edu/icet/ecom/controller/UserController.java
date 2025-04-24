package edu.icet.ecom.controller;


import edu.icet.ecom.dto.User;
import edu.icet.ecom.service.UserService;
import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
@CrossOrigin
public class UserController {

    private final UserService userService;

    @PostMapping("/register")
    public User register(@RequestBody User user) {
        return userService.registerUser(user);
    }
//
//    @PostMapping("/forgot-password/{email}")
//    public String forgotPassword(@PathVariable String email) {
//        return userService.sendOtp(email);
//    }
//
//    @PostMapping("/verify-otp/{email}/{otp}")
//    public String verifyOtp(@PathVariable String email, @PathVariable String otp) {
//        return userService.verifyOtp(email, otp);
//    }
//
//    @PostMapping("/reset-password/{email}/{newPassword}")
//    public String resetPassword(@PathVariable String email, @PathVariable String newPassword) {
//        return userService.resetPassword(email, newPassword);
//    }
//
//    @PostMapping("/login/{email}/{password}")
//    public String login(@PathVariable String email, @PathVariable String password) {
//        return userService.login(email, password);
//    }
}
