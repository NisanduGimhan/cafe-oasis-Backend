package edu.icet.ecom.dto;


import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class User {

    private Long id;
    private String fullName;
    private String email;
    private String password;
    private String otp;
    private String role;
}
