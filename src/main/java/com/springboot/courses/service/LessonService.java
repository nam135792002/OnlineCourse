package com.springboot.courses.service;

import com.springboot.courses.entity.TextLesson;
import com.springboot.courses.entity.Video;
import com.springboot.courses.payload.lesson.LessonRequest;
import com.springboot.courses.payload.lesson.LessonResponse;
import com.springboot.courses.payload.quiz.QuizRequest;

public interface LessonService {
    LessonResponse createLesson(LessonRequest lessonRequest, Video video, TextLesson textLesson, QuizRequest[] quizRequest);
    LessonResponse get(Integer lessonId);
    LessonResponse updateLesson(Integer lessonId, LessonRequest lessonRequest, Video video, TextLesson textLesson, QuizRequest[] quizRequest);
    String deleteLesson(Integer lessonId);
}
