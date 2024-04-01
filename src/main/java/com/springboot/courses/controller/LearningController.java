package com.springboot.courses.controller;

import com.springboot.courses.entity.Lesson;
import com.springboot.courses.exception.BlogApiException;
import com.springboot.courses.payload.lesson.LessonResponse;
import com.springboot.courses.service.LearningService;
import com.springboot.courses.service.LessonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/learning")
public class LearningController {

    @Autowired private LearningService learningService;
    @Autowired private LessonService lessonService;

    @GetMapping("/courses/{id}")
    public ResponseEntity<?> getDetailInLearningPage(@PathVariable(value = "id") Integer courseId){
        return ResponseEntity.ok(learningService.getCourseReturnLearningPage(courseId));
    }

    @GetMapping("/courses/{courseId}/lesson/{lessonId}")
    public ResponseEntity<?> getVideoInDetailCourse(@PathVariable(value = "lessonId") Integer lessonId,
                                                    @PathVariable(value = "courseId") Integer courseId){
        LessonResponse lessonResponse = lessonService.get(lessonId);
        switch (lessonResponse.getLessonType()){
            case VIDEO -> {
                return ResponseEntity.ok(learningService.getVideo(courseId, lessonId));
            }

            case QUIZ -> {
                return ResponseEntity.ok(learningService.getQuiz(courseId, lessonId));
            }

            default -> {
                return ResponseEntity.ok(new BlogApiException(HttpStatus.NOT_FOUND, "Not found this lesson"));
            }
        }
    }
}
