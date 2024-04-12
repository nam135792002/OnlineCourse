package com.springboot.courses.controller;

import com.springboot.courses.payload.course.CourseReturnMyLearning;
import com.springboot.courses.service.LearningService;
import com.springboot.courses.service.LessonService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/learning")
public class LearningController {

    @Autowired private LearningService learningService;

    @GetMapping("/courses/{slug}")
    public ResponseEntity<?> getDetailInLearningPage(@PathVariable(value = "slug") String slug){
        return ResponseEntity.ok(learningService.getCourseReturnLearningPage(slug));
    }

    @PostMapping ("/my/course/list-all")
    public ResponseEntity<?> getListAllCourseMyLearning(@RequestParam(value = "email") String email){
        List<CourseReturnMyLearning> listCourse = learningService.listAllCourseRegisteredByCustomer(email);
        if(listCourse.isEmpty()){
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(listCourse);
    }

    @PostMapping("/check/exist-course/{slug}")
    public ResponseEntity<?> checkExistRegisterCourse(@PathVariable(value = "slug") String slug,
                                                      @RequestParam(value = "email") String email){
        return ResponseEntity.ok(learningService.isRegisterInThisCourse(slug, email));
    }
}
