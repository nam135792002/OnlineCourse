package com.springboot.courses.repository;

import com.springboot.courses.entity.Courses;
import com.springboot.courses.entity.Lesson;
import com.springboot.courses.entity.TrackCourse;
import com.springboot.courses.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TrackCourseRepository extends JpaRepository<TrackCourse, Integer> {
    List<TrackCourse> findAllByCoursesAndUser(Courses courses, User user);

    @Modifying
    @Query("update TrackCourse tc set tc.isCompleted = true where (tc.user.id = ?1 and tc.lesson.id = ?2)")
    void updateTrackCourseLessonPre(Integer userId, Integer lessonIdPre);

    @Modifying
    @Query("update TrackCourse tc set tc.isUnlock = true where (tc.user.id = ?1 and tc.lesson.id = ?2)")
    void updateTrackCourseLessonNext(Integer userId, Integer lessonIdNext);
}
