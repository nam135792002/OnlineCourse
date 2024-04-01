package com.springboot.courses.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "full_name", length = 45, nullable = false)
    private String fullName;

    @Column(length = 45, nullable = false)
    private String username;

    @Column(nullable = false, unique = true, length = 30)
    private String email;

    @Column(name = "phone_number", nullable = false, unique = true, length = 11)
    private String phoneNumber;

    @Column(length = 100)
    private String photo;

    @Column(length = 64, nullable = false)
    private String password;

    @Column(name = "created_time", nullable = false)
    private Date createdTime;

    private boolean enabled;

    @Column(name = "verification_code", length = 64)
    private String verificationCode;

    @Column(name = "reset_password_token", length = 30)
    private String resetPasswordToken;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "role_id")
    private Role role;

}
