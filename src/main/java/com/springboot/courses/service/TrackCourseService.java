package com.springboot.courses.service;

import com.springboot.courses.payload.lesson.LessonReturnLearningResponse;
import com.springboot.courses.payload.track.InfoCourseRegistered;
import com.springboot.courses.payload.track.TrackCourseRequest;

public interface TrackCourseService {
    InfoCourseRegistered listTrackCourse(String email , String slug);
    Integer confirmLessonLearned(String email , Integer lessonIdPre);
    LessonReturnLearningResponse getLesson(Integer lessonId);
    String updatePeriodCurrentOfVideo(TrackCourseRequest trackCourseRequest);
}
