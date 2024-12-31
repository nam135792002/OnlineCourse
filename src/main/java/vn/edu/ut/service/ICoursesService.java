package vn.edu.ut.service;

import org.springframework.web.multipart.MultipartFile;
import vn.edu.ut.payload.ClassResponse;
import vn.edu.ut.payload.course.CourseResponse;
import vn.edu.ut.payload.course.CourseReturnDetailPageResponse;
import vn.edu.ut.payload.course.CourseReturnHomePageResponse;
import vn.edu.ut.payload.course.CourseReturnSearch;
import vn.edu.ut.payload.course.CoursesRequest;

import java.util.List;

public interface ICoursesService {
    CourseResponse createCourse(CoursesRequest coursesRequest, MultipartFile image);

    ClassResponse getAll(int pageNo, int pageSize, String sortBy, String sortDir, String keyword, Integer categoryId);

    CourseResponse get(Integer courseId);

    CourseResponse update(Integer courseId, CoursesRequest coursesRequest, MultipartFile img);

    void delete(Integer courseId);

    List<CourseReturnHomePageResponse> getCourseIntoHomePage(Integer categoryId);

    CourseReturnDetailPageResponse getCourseDetail(String slug);

    void updateIsEnabled(Integer courseId, boolean isEnabled);

    void updateIsPublished(Integer courseId, boolean isPublished);

    void updateIsFinished(Integer courseId, boolean isFinished);

    List<CourseReturnSearch> listAllCourseByKeyword(String keyword);

}
