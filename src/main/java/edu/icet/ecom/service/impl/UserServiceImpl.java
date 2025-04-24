package edu.icet.ecom.service.impl;

import edu.icet.ecom.dto.User;
import edu.icet.ecom.entity.UserEntity;
import edu.icet.ecom.repository.UserRepository;
import edu.icet.ecom.service.UserService;
import edu.icet.ecom.util.UserPrincipal;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{

    private final JavaMailSender mailSender;
    private final UserRepository userRepository;
    private final ModelMapper mapper;


    @Override
    public User registerUser(User user) {
        UserEntity save = userRepository.save(mapper.map(user, UserEntity.class));
        User map = mapper.map(save, User.class);
        return map;
    }

    @Override
    public String sendOtp(String email) {
        return "";
    }

    @Override
    public String verifyOtp(String email, String otp) {
        return "";
    }

    @Override
    public String resetPassword(String email, String newPassword) {
        return "";
    }

    @Override
    public String login(String email, String password) {
        return "";
    }
}




//
//    public String registerUser(User user) {
//        List<UserEntity> users = userRepository.findByEmail(user.getEmail());
//        if (!users.isEmpty()) {
//            return "Email already exists!";
//        }
//
//        // No encoding
//        userRepository.save(mapper.map(user, UserEntity.class));
//        return "User registered successfully!";
//    }
//
//    public String sendOtp(String email) {
//        List<UserEntity> userOpt = userRepository.findByEmail(email);
//        if (userOpt.isEmpty()) {
//            return "Email not found!";
//        }
//        UserEntity user = userOpt.get(0);
//        String otp = String.format("%06d", new Random().nextInt(999999));
//
//        user.setOtp(otp);
//        userRepository.save(user);
//
//        // Send OTP via email
//        SimpleMailMessage message = new SimpleMailMessage();
//        message.setTo(email);
//        message.setSubject("Your OTP Code From MossBurger");
//        message.setText("Your OTP code is: " + otp);
//        mailSender.send(message);
//
//        return "OTP sent!";
//    }
//
//    public String verifyOtp(String email, String otp) {
//        List<UserEntity> userOpt = userRepository.findByEmail(email);
//        if (!userOpt.isEmpty() && otp.equals(userOpt.get(0).getOtp())) {
//            return "OTP Verified!";
//        }
//        return "Invalid OTP!";
//    }
//
//    public String resetPassword(String email, String newPassword) {
//        List<UserEntity> userOpt = userRepository.findByEmail(email);
//        if (userOpt.isEmpty()) {
//            return "Email not found!";
//        }
//
//        UserEntity user = userOpt.get(0);
//        user.setPassword(newPassword);  // No encoding
//        user.setOtp(null); // Reset OTP
//        userRepository.save(user);
//        return "Password reset successfully!";
//    }
//
//    public String login(String email, String password) {
//        List<UserEntity> userOpt = userRepository.findByEmail(email);
//
//        if (!userOpt.isEmpty()) {
//            UserEntity user = userOpt.get(0);
//            if (password.equals(user.getPassword())) {  // Plain string comparison
//                return "Login successful!";
//            } else {
//                return "Incorrect password!";
//            }
//        }
//        return "User not found!";
//    }



