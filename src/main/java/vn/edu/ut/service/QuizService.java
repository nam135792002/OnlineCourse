package vn.edu.ut.service;

import vn.edu.ut.payload.lesson.LessonRequestInQuiz;

public interface QuizService {
    float gradeOfQuiz(LessonRequestInQuiz lessonRequestInQuiz);
    String delete(Integer quizId);
}
