package com.springboot.courses.controller;

import com.springboot.courses.payload.lesson.LessonRequestInQuiz;
import com.springboot.courses.payload.quiz.QuizRequest;
import com.springboot.courses.payload.quiz.QuizResponse;
import com.springboot.courses.service.QuizService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/quiz")
@Tag(
        name = "Quiz Controller",
        description = "REST APIs related to Quiz Entity"
)
public class QuizController {

    @Autowired private QuizService quizService;

    @Operation(
            summary = "Check Answer REST API",
            description = "This REST API is used to handle quiz test and return grade of this test"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Http Status 200 OK - Answer checked successfully"
    )
    @SecurityRequirement(
            name = "Bear Authentication"
    )
    @PostMapping("/check-answer")
    public ResponseEntity<?> checkAnswerIsCorrect(@RequestBody @Valid LessonRequestInQuiz lessonRequestInQuiz){
        return ResponseEntity.ok(quizService.gradeOfQuiz(lessonRequestInQuiz));
    }

    @Operation(
            summary = "Delete Quiz REST API",
            description = "This REST API is used to delete a quiz by its ID."
    )
    @ApiResponse(
            responseCode = "200",
            description = "Http Status 200 OK - Quiz deleted successfully"
    )
    @ApiResponse(
            responseCode = "404",
            description = "Http Status 404 Not Found - Quiz not found"
    )
    @SecurityRequirement(
            name = "Bear Authentication"
    )
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable(value = "id") Integer quizId){
        return ResponseEntity.ok(quizService.delete(quizId));
    }
}
