package com.springboot.courses.service;

import com.springboot.courses.payload.TrackCourses.InfoCourseRegistered;
import jakarta.servlet.http.HttpServletRequest;

import java.util.List;

public interface TrackCourseService {
    InfoCourseRegistered listTrackCourse(HttpServletRequest request, String slug);
    Integer confirmLessonLearned(HttpServletRequest request, Integer lessonIdPre);
}
