package com.springboot.courses.repository;

import com.springboot.courses.entity.Certificate;
import com.springboot.courses.entity.Courses;
import com.springboot.courses.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CertificateRepository extends JpaRepository<Certificate, Integer> {
    Certificate findCertificateByUserAndCourses(User user, Courses courses);
}
