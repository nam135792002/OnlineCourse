package com.springboot.courses.repository;

import com.springboot.courses.entity.TrackCourse;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TrackCourseRepository extends JpaRepository<TrackCourse, Integer> {

}
