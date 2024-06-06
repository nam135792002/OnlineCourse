package com.springboot.courses.controller;

import com.springboot.courses.payload.course.CourseReturnMyLearning;
import com.springboot.courses.service.LearningService;
import com.springboot.courses.service.LessonService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/learning")
@Tag(
        name = "Learning Controller",
        description = "REST APIs related to Learning Entity"
)
public class LearningController {

    @Autowired private LearningService learningService;

    @Operation(
            summary = "Get Course Details in Learning Page REST API",
            description = "This REST API is used to get details of a course in the learning page"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Http Status 200 OK - Course details retrieved successfully"
    )
    @ApiResponse(
            responseCode = "404",
            description = "Http Status 404 Not Found - Course not found"
    )
    @SecurityRequirement(
            name = "Bear Authentication"
    )
    @GetMapping("/courses/{slug}")
    public ResponseEntity<?> getDetailInLearningPage(@PathVariable(value = "slug") String slug){
        return ResponseEntity.ok(learningService.getCourseReturnLearningPage(slug));
    }

    @Operation(
            summary = "Get All Courses in My Learning REST API",
            description = "This REST API is used to get all courses registered by a customer in My Learning section"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Http Status 200 OK - Courses retrieved successfully"
    )
    @ApiResponse(
            responseCode = "204",
            description = "Http Status 204 No Content - No courses found"
    )
    @SecurityRequirement(
            name = "Bear Authentication"
    )
    @GetMapping ("/my/course/list-all")
    public ResponseEntity<?> getListAllCourseMyLearning(@RequestParam(value = "email") String email){
        List<CourseReturnMyLearning> listCourse = learningService.listAllCourseRegisteredByCustomer(email);
        if(listCourse.isEmpty()){
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(listCourse);
    }

    @Operation(
            summary = "Check if User is Registered in Course REST API",
            description = "This REST API is used to check if a user is registered in a specific course"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Http Status 200 OK - Registration status retrieved successfully"
    )
    @ApiResponse(
            responseCode = "404",
            description = "Http Status 404 Not Found - Course or user not found"
    )
    @SecurityRequirement(
            name = "Bear Authentication"
    )
    @GetMapping("/check/exist-course/{slug}")
    public ResponseEntity<?> checkExistRegisterCourse(@PathVariable(value = "slug") String slug,
                                                      @RequestParam(value = "email") String email){
        return ResponseEntity.ok(learningService.isRegisterInThisCourse(slug, email));
    }
}
