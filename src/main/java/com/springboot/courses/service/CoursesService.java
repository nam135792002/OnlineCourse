package com.springboot.courses.service;

import com.springboot.courses.payload.ClassResponse;
import com.springboot.courses.payload.course.CourseReturnDetailPageResponse;
import com.springboot.courses.payload.course.CourseReturnHomePageResponse;
import com.springboot.courses.payload.course.CourseResponse;
import com.springboot.courses.payload.course.CoursesRequest;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface CoursesService {
    CourseResponse createCourse(CoursesRequest coursesRequest, MultipartFile image);
    ClassResponse getAll(int pageNo, int pageSize, String sortBy, String sortDir, String keyword, Integer categoryId);
    CourseResponse get(Integer courseId);
    CourseResponse update(Integer courseId, CoursesRequest coursesRequest, MultipartFile img);
    String delete(Integer courseId);
    List<CourseReturnHomePageResponse> getCourseIntoHomePage(Integer categoryId);
    CourseReturnDetailPageResponse getCourseDetail(Integer courseId);

}
