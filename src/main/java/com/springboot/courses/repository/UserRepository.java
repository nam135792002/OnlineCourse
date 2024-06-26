package com.springboot.courses.repository;

import com.springboot.courses.entity.Role;
import com.springboot.courses.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {
    boolean existsUserByEmail(String email);
    boolean existsUserByPhoneNumber(String phoneNumber);
    boolean existsUserByUsername(String username);
    @Query("select u from User u where u.fullName like %?1%" +
            "or u.phoneNumber like %?1%" +
            "or u.email like %?1%")
    Page<User> search(String keyword, Pageable pageable);
    User findUserByVerificationCode(String code);
    @Modifying
    @Query("update User u set u.enabled = true, u.verificationCode = null where u.id = ?1")
    void enable(Integer id);
    Optional<User> findByEmail(String email);
    User findUserByResetPasswordToken(String token);
}
