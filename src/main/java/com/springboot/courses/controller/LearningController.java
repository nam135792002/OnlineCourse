package com.springboot.courses.controller;

import com.springboot.courses.exception.BlogApiException;
import com.springboot.courses.payload.course.CourseReturnMyLearning;
import com.springboot.courses.payload.lesson.LessonResponse;
import com.springboot.courses.service.LearningService;
import com.springboot.courses.service.LessonService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/learning")
public class LearningController {

    @Autowired private LearningService learningService;
    @Autowired private LessonService lessonService;

    @PreAuthorize("hasRole('CUSTOMER')")
    @GetMapping("/courses/{slug}")
    public ResponseEntity<?> getDetailInLearningPage(@PathVariable(value = "slug") String slug){
        return ResponseEntity.ok(learningService.getCourseReturnLearningPage(slug));
    }

    @PreAuthorize("hasRole('CUSTOMER')")
    @GetMapping("/my/course/list-all")
    public ResponseEntity<?> getListAllCourseMyLearning(HttpServletRequest request){
        List<CourseReturnMyLearning> listCourse = learningService.listAllCourseRegisteredByCustomer(request);
        if(listCourse.isEmpty()){
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(listCourse);
    }

    @GetMapping("/check/exist-course/{slug}")
    public ResponseEntity<?> checkExistRegisterCourse(@PathVariable(value = "slug") String slug,
                                                      HttpServletRequest request){
        return ResponseEntity.ok(learningService.isRegisterInThisCourse(slug, request));
    }
}
