package com.springboot.courses.service;

import com.springboot.courses.payload.lesson.LessonRequest;
import com.springboot.courses.payload.lesson.LessonResponse;

public interface LessonService {
    LessonResponse createLesson(LessonRequest lessonRequest);
    LessonResponse get(Integer lessonId);
    LessonResponse updateLesson(Integer lessonId, LessonRequest lessonRequest);
    String deleteLesson(Integer lessonId);
}
