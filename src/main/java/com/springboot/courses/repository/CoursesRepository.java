package com.springboot.courses.repository;

import com.springboot.courses.entity.Courses;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CoursesRepository extends JpaRepository<Courses, Integer> {

}
