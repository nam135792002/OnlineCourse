package com.springboot.courses.repository;

import com.springboot.courses.entity.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.sound.midi.Track;
import java.util.List;

public interface TrackCourseRepository extends JpaRepository<TrackCourse, Integer> {
    @Modifying
    @Query("update TrackCourse tc set tc.isCompleted = true where (tc.user.id = ?1 and tc.lesson.id = ?2)")
    void updateTrackCourseLessonPre(Integer userId, Integer lessonIdPre);
    @Modifying
    @Query("update TrackCourse tc set tc.isUnlock = true where (tc.user.id = ?1 and tc.lesson.id = ?2)")
    void updateTrackCourseLessonNext(Integer userId, Integer lessonIdNext);
    List<TrackCourse> findTrackCourseByCoursesAndChapterAndUser(Courses courses, Chapter chapter, User user);
    TrackCourse findTrackCourseByLessonAndUser(Lesson lesson, User user);
}
