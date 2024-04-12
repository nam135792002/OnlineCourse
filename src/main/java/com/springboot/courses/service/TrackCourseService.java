package com.springboot.courses.service;

import com.springboot.courses.payload.TrackCourses.InfoCourseRegistered;
import jakarta.servlet.http.HttpServletRequest;

import java.util.List;

public interface TrackCourseService {
    InfoCourseRegistered listTrackCourse(String email , String slug);
    Integer confirmLessonLearned(String email , Integer lessonIdPre);
}
