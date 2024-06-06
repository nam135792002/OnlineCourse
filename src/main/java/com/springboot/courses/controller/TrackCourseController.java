package com.springboot.courses.controller;

import com.springboot.courses.entity.Courses;
import com.springboot.courses.payload.track.InfoCourseRegistered;
import com.springboot.courses.payload.track.TrackCourseRequest;
import com.springboot.courses.service.CertificateService;
import com.springboot.courses.service.LessonService;
import com.springboot.courses.service.TrackCourseService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/track-course")
@Tag(
        name = "Track Course",
        description = "Endpoints for tracking course progress"
)
public class TrackCourseController {
    @Autowired
    private TrackCourseService trackCourseService;
    @Autowired
    private LessonService lessonService;
    @Autowired
    private CertificateService certificateService;

    @Operation(
            summary = "Get all tracked courses for a user",
            description = "Retrieve information about all courses that a user is tracking, based on the user's email and course slug."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of tracked courses retrieved",
                    content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "404", description = "No tracked courses found")})
    @SecurityRequirement(
            name = "Bear Authentication"
    )
    @GetMapping("/get-all")
    public ResponseEntity<InfoCourseRegistered> getAll(@RequestParam(value = "email") String email, @RequestParam(value = "slug") String slug) {
        return ResponseEntity.ok(trackCourseService.listTrackCourse(email, slug));
    }

    @Operation(
            summary = "Confirm completion of a lesson",
            description = "Confirm that a user has completed a lesson. If all lessons in the course are completed, generate a certificate."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lesson completion confirmed"),
            @ApiResponse(responseCode = "201", description = "Certificate generated"),
            @ApiResponse(responseCode = "404", description = "Lesson not found")}
    )
    @SecurityRequirement(
            name = "Bear Authentication"
    )
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

    @Operation(
            summary = "Get information about a specific lesson",
            description = "Retrieve details about a specific lesson based on its ID."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lesson information retrieved",
                    content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "404", description = "Lesson not found")})
    @SecurityRequirement(
            name = "Bear Authentication"
    )
    @GetMapping("/get-lesson")
    public ResponseEntity<?> learningLesson(@RequestParam(value = "lesson") Integer lessonId) {
        return ResponseEntity.ok(trackCourseService.getLesson(lessonId));
    }

    @Operation(
            summary = "Update tracked course progress",
            description = "Update the progress of a course being tracked by a user."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Track course progress updated"),
            @ApiResponse(responseCode = "400", description = "Invalid request")})
    @SecurityRequirement(
            name = "Bear Authentication"
    )
    @PostMapping("/update/track-course")
    public ResponseEntity<?> updatePeriodCurrent(@RequestBody @Valid TrackCourseRequest trackCourseRequest) {
        return ResponseEntity.ok(trackCourseService.updatePeriodCurrentOfVideo(trackCourseRequest));
    }
}
