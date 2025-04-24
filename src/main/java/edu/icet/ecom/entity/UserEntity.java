package edu.icet.ecom.entity;

import edu.icet.ecom.util.UserType;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "user")
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String fullName;
    private String email;
    private String password;
    private String otp;
    @Enumerated(EnumType.STRING)
    private UserType role;
}
