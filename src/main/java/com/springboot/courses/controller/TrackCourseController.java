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
            return ResponseEntity.ok("CONTINUE");
        }else{
            return ResponseEntity.ok("DONE");
        }
    }

    @GetMapping("/get-lesson")
    public ResponseEntity<?> learningLesson(@RequestParam(value = "lesson") Integer lessonId){
        return ResponseEntity.ok(trackCourseService.getLesson(lessonId));
    }
}
