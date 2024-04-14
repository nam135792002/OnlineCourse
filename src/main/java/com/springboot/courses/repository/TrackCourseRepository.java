package com.springboot.courses.repository;

import com.springboot.courses.entity.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.sound.midi.Track;
import java.util.List;

public interface TrackCourseRepository extends JpaRepository<TrackCourse, Integer> {
    @Modifying
    @Query("update TrackCourse tc set tc.isCompleted = true, tc.isCurrent = false where (tc.user.id = ?1 and tc.lesson.id = ?2)")
    void updateTrackCourseLessonPre(Integer userId, Integer lessonIdPre);
    @Modifying
    @Query("update TrackCourse tc set tc.isUnlock = true, tc.isCurrent = true where (tc.user.id = ?1 and tc.lesson.id = ?2)")
    void updateTrackCourseLessonNext(Integer userId, Integer lessonIdNext);
    List<TrackCourse> findTrackCourseByCoursesAndChapterAndUser(Courses courses, Chapter chapter, User user);
    TrackCourse findTrackCourseByLessonAndUser(Lesson lesson, User user);
    List<TrackCourse> findAllByCoursesAndUser(Courses courses, User user);
    @Query("select tc from TrackCourse tc where tc.courses.id = ?1 and tc.user.id = ?2 and tc.isCurrent = true")
    TrackCourse findTrackCoursesByCurrent(Integer courseId, Integer userId);
}
