package com.springboot.courses.repository;

import com.springboot.courses.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Integer> {
    boolean existsUserByEmail(String email);

    boolean existsUserByPhoneNumber(String phoneNumber);

    @Query("select u from User u where u.fullName like %?1%" +
            "or u.phoneNumber like %?1%" +
            "or u.email like %?1%")
    Page<User> search(String keyword, Pageable pageable);

}
