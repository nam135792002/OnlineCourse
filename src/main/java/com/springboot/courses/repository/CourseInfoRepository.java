package com.springboot.courses.repository;

import com.springboot.courses.entity.CourseInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseInfoRepository extends JpaRepository<CourseInfo, Integer> {

}
