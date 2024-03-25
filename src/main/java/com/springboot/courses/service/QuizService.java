package com.springboot.courses.service;

import com.springboot.courses.payload.quiz.QuizRequest;
import com.springboot.courses.payload.quiz.QuizResponse;

public interface QuizService {
    QuizResponse createQuiz(QuizRequest quizRequest);
    QuizResponse updateQuiz(QuizRequest quizRequest);
    String deleteQuiz(Integer quizId);
}
