package edu.icet.ecom.controller;


import edu.icet.ecom.dto.User;
import edu.icet.ecom.service.UserService;
import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

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

    @PostMapping("/login/{email}/{password}")
    public ResponseEntity<Map<String, Object>> login(@PathVariable String email, @PathVariable String password) {
        String token = userService.login(email, password);
        User user = userService.loginReturnUser(email, password);

        Map<String, Object> response = new HashMap<>();
        response.put("token", token);
        response.put("role", user.getRole());
        response.put("email", user.getEmail());
        response.put("fullName", user.getFullName());

        return ResponseEntity.ok(response);
    }


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
//

