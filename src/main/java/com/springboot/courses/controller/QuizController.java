package com.springboot.courses.controller;

import com.springboot.courses.payload.lesson.LessonRequestInQuiz;
import com.springboot.courses.payload.quiz.QuizRequest;
import com.springboot.courses.payload.quiz.QuizResponse;
import com.springboot.courses.service.QuizService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/quiz")
public class QuizController {

    @Autowired private QuizService quizService;

    @PostMapping("/check-answer")
    public ResponseEntity<?> checkAnswerIsCorrect(@RequestBody @Valid LessonRequestInQuiz lessonRequestInQuiz){
        return ResponseEntity.ok(quizService.gradeOfQuiz(lessonRequestInQuiz));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable(value = "id") Integer quizId){
        return ResponseEntity.ok(quizService.delete(quizId));
    }
}
