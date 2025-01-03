package vn.edu.ut.service;

import vn.edu.ut.entity.Courses;
import vn.edu.ut.entity.TextLesson;
import vn.edu.ut.entity.Video;
import vn.edu.ut.payload.lesson.LessonRequest;
import vn.edu.ut.payload.lesson.LessonResponse;
import vn.edu.ut.payload.quiz.QuizRequest;

public interface ILessonService {
    LessonResponse createLesson(LessonRequest lessonRequest, Video video, TextLesson textLesson, QuizRequest[] quizRequest);

    LessonResponse get(Integer lessonId);

    LessonResponse updateLesson(Integer lessonId, LessonRequest lessonRequest, Video video, TextLesson textLesson, QuizRequest[] quizRequest);

    String deleteLesson(Integer lessonId);

    Courses getCourse(Integer lessonId);
}
