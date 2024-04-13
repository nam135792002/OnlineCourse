package com.springboot.courses.controller;

import com.springboot.courses.exception.BlogApiException;
import com.springboot.courses.payload.track.InfoCourseRegistered;
import com.springboot.courses.payload.lesson.LessonResponse;
import com.springboot.courses.service.LearningService;
import com.springboot.courses.service.LessonService;
import com.springboot.courses.service.TrackCourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/track-course")
public class TrackCourseController {
    @Autowired private TrackCourseService trackCourseService;
    @Autowired private LessonService lessonService;
    @Autowired private LearningService learningService;

    @GetMapping("/get-all")
    public ResponseEntity<InfoCourseRegistered> getAll(@RequestParam(value = "email") String email,
                                                       @RequestParam(value = "slug") String slug){
        return ResponseEntity.ok(trackCourseService.listTrackCourse(email, slug));
    }

    @PostMapping("/confirm-done")
    public ResponseEntity<?> doneLesson(@RequestParam(value = "email") String email ,
                                        @RequestParam(value = "lesson") Integer lessonId){
        Integer lessonIdNext = trackCourseService.confirmLessonLearned(email, lessonId);
        if(lessonIdNext != -1){
            return getLesson(lessonIdNext);
        }else{
            return ResponseEntity.ok("Chúc mừng bạn đã hoàn thành khóa học!");
        }
    }

    @PostMapping("/get-lesson")
    public ResponseEntity<?> learningLesson(@RequestParam(value = "lesson") Integer lessonId){
        return getLesson(lessonId);
    }

    private ResponseEntity<?> getLesson(Integer lessonId){
        LessonResponse lessonResponse = lessonService.get(lessonId);
        switch (lessonResponse.getLessonType()){
            case VIDEO -> {
                return ResponseEntity.ok(learningService.getVideo(lessonId));
            }

            case QUIZ -> {
                return ResponseEntity.ok(learningService.getQuiz(lessonId));
            }

            default -> {
                return ResponseEntity.ok(new BlogApiException(HttpStatus.NOT_FOUND, "Not found this lesson"));
            }
        }
    }
}
