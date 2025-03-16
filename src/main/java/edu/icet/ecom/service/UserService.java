package edu.icet.ecom.service;

import edu.icet.ecom.dto.User;

public interface UserService {
    String registerUser(User user);

    String sendOtp(String email);

    String verifyOtp(String email, String otp);

    String resetPassword(String email, String newPassword);

    String login(String email, String password);
}
