package com.springboot.courses.controller;

import com.springboot.courses.payload.ClassResponse;
import com.springboot.courses.payload.course.CourseReturnHomePageResponse;
import com.springboot.courses.payload.course.CourseResponse;
import com.springboot.courses.payload.course.CoursesRequest;
import com.springboot.courses.service.CoursesService;
import com.springboot.courses.utils.AppConstants;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/courses")
public class CourseController {

    @Autowired private CoursesService coursesService;

    @PostMapping("/create")
    public ResponseEntity<?> createCourse(@RequestPart(value = "course") @Valid CoursesRequest coursesRequest,
                                          @RequestParam(value = "img") MultipartFile img){
        CourseResponse courseResponse = coursesService.createCourse(coursesRequest, img);
        URI uri = URI.create("/api/courses/create/" + courseResponse.getId());

        return ResponseEntity.created(uri).body(courseResponse);
    }

    @GetMapping("/list-all")
    public ResponseEntity<ClassResponse> listAllCourses(
            @RequestParam(value = "pageNo", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER, required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = AppConstants.DEFAULT_PAGE_SIZE, required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = AppConstants.DEFAULT_SORT_BY, required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = AppConstants.DEFAULT_SORT_DIRECTION, required = false) String sortDir,
            @RequestParam(value = "keyword", required = false) String keyword,
            @RequestParam(value = "categoryId", required = false) Integer categoryId
    ){
        ClassResponse classResponse = coursesService.getAll(pageNo, pageSize, sortBy, sortDir, keyword, categoryId);
        if(classResponse.getContent().isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(classResponse);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<CourseResponse> getCourseById(@PathVariable(value = "id") Integer courseId){
        return ResponseEntity.ok(coursesService.get(courseId));
    }

    @GetMapping("/home-page")
    public ResponseEntity<?> getCourseReturnHomePage(@RequestParam(value = "categoryId", required = false) Integer categoryId){
        List<CourseReturnHomePageResponse> listCourses = coursesService.getCourseIntoHomePage(categoryId);
        if(listCourses.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(listCourses);
    }

    @GetMapping("/get-detail/{id}")
    public ResponseEntity<?> getCourseDetailById(@PathVariable(value = "id") Integer courseId){
        return ResponseEntity.ok(coursesService.getCourseDetail(courseId));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<CourseResponse> updateCourse(@PathVariable(value = "id") Integer courseId,
                                                       @RequestPart(value = "course") @Valid CoursesRequest coursesRequest,
                                                       @RequestParam(value = "img", required = false) MultipartFile img){
        System.out.println(coursesRequest.getInfoList().size());
        return ResponseEntity.ok(coursesService.update(courseId, coursesRequest, img));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteCourse(@PathVariable(value = "id") Integer courseId){
        return ResponseEntity.ok(coursesService.delete(courseId));
    }
}
