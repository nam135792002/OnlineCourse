package com.springboot.courses.controller;

import com.springboot.courses.payload.TrackCourses.InfoCourseRegistered;
import com.springboot.courses.service.TrackCourseService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/track-course")
public class TrackCourseController {
    @Autowired private TrackCourseService trackCourseService;

    @PostMapping("/get-all")
    public ResponseEntity<InfoCourseRegistered> getAll(HttpServletRequest request,
                                                       @RequestParam(value = "slug") String slug){
        return ResponseEntity.ok(trackCourseService.listTrackCourse(request, slug));
    }

    @PostMapping("/confirm-done")
    public ResponseEntity<?> doneLesson(HttpServletRequest request,
                                        @RequestParam(value = "lesson_id_pre") Integer lessonIdPre,
                                        @RequestParam(value = "lesson_id_next") Integer lessonIdNext){
        return ResponseEntity.ok(trackCourseService.confirmLessonLearned(request, lessonIdPre, lessonIdNext));
    }
}
