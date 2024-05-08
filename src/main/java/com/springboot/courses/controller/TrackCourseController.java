package com.springboot.courses.controller;

import com.springboot.courses.entity.Courses;
import com.springboot.courses.payload.track.InfoCourseRegistered;
import com.springboot.courses.payload.track.TrackCourseRequest;
import com.springboot.courses.service.CertificateService;
import com.springboot.courses.service.LessonService;
import com.springboot.courses.service.TrackCourseService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/track-course")
public class TrackCourseController {
    @Autowired
    private TrackCourseService trackCourseService;
    @Autowired
    private LessonService lessonService;
    @Autowired
    private CertificateService certificateService;

    @GetMapping("/get-all")
    public ResponseEntity<InfoCourseRegistered> getAll(@RequestParam(value = "email") String email, @RequestParam(value = "slug") String slug) {
        return ResponseEntity.ok(trackCourseService.listTrackCourse(email, slug));
    }

    @PostMapping("/confirm-done")
    public ResponseEntity<?> doneLesson(@RequestParam(value = "email") String email, @RequestParam(value = "lesson") Integer lessonId) {
        Integer lessonIdNext = trackCourseService.confirmLessonLearned(email, lessonId);
        Courses courses = lessonService.getCourse(lessonId);
        if (lessonIdNext != -1) {
            return ResponseEntity.ok("CONTINUE");
        } else {
            return ResponseEntity.status(HttpStatus.CREATED).body(certificateService.save(email, courses));
        }
    }

    @GetMapping("/get-lesson")
    public ResponseEntity<?> learningLesson(@RequestParam(value = "lesson") Integer lessonId) {
        return ResponseEntity.ok(trackCourseService.getLesson(lessonId));
    }

    @PostMapping("/update/track-course")
    public ResponseEntity<?> updatePeriodCurrent(@RequestBody @Valid TrackCourseRequest trackCourseRequest) {
        return ResponseEntity.ok(trackCourseService.updatePeriodCurrentOfVideo(trackCourseRequest));
    }
}
