package com.springboot.courses.controller;

import com.springboot.courses.payload.ClassResponse;
import com.springboot.courses.payload.course.*;
import com.springboot.courses.service.CoursesService;
import com.springboot.courses.utils.AppConstants;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/courses")
@Tag(
        name = "CRUD REST APIs for Course Resource"
)
public class CourseController {

    @Autowired private CoursesService coursesService;

    @Operation(
            summary = "Create course REST API",
            description = "Create course REST API is used to save course into database"
    )
    @ApiResponse(
            responseCode = "201",
            description = "Http Status 201 CREATED"
    )
    @SecurityRequirement(
            name = "Bear Authentication"
    )
    @PostMapping("/create")
    public ResponseEntity<?> createCourse(@RequestPart(value = "course") @Valid CoursesRequest coursesRequest,
                                          @RequestParam(value = "img") MultipartFile img){
        CourseResponse courseResponse = coursesService.createCourse(coursesRequest, img);
        URI uri = URI.create("/api/courses/create/" + courseResponse.getId());

        return ResponseEntity.created(uri).body(courseResponse);
    }

    @Operation(
            summary = "List all course REST API",
            description = "This REST API is used to retrieve a paginated list of all courses from the database. " +
                    "The list can be sorted and filtered using query parameters."
    )
    @ApiResponse(
            responseCode = "200",
            description = "Http Status 200 OK - Successful retrieval of course list"
    )
    @ApiResponse(
            responseCode = "204",
            description = "Http Status 204 No Content - No courses found"
    )
    @SecurityRequirement(
            name = "Bear Authentication"
    )
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

    @Operation(
            summary = "Get course by ID REST API",
            description = "Get course by ID REST API is used to get single course from the database"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Http Status 200 SUCCESS"
    )
    @ApiResponse(
            responseCode = "404",
            description = "Http Status 404 NOT FOUND"
    )
    @SecurityRequirement(
            name = "Bear Authentication"
    )
    @GetMapping("/get/{id}")
    public ResponseEntity<CourseResponse> getCourseById(@PathVariable(value = "id") Integer courseId){
        return ResponseEntity.ok(coursesService.get(courseId));
    }

    @Operation(
            summary = "Get all course REST API in Home Page for all end-users",
            description = "Get all course REST API is used to list of all courses from the database"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Http Status 200 SUCCESS"
    )
    @ApiResponse(
            responseCode = "404",
            description = "Http Status 404 NOT FOUND"
    )
    @GetMapping("/home-page")
    public ResponseEntity<?> getCourseReturnHomePage(@RequestParam(value = "categoryId", required = false) Integer categoryId){
        List<CourseReturnHomePageResponse> listCourses = coursesService.getCourseIntoHomePage(categoryId);
        if(listCourses.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(listCourses);
    }

    @Operation(
            summary = "Get course by slug REST API for all end-users",
            description = "Get course by slug REST API is used to get single course from the database"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Http Status 200 SUCCESS"
    )
    @ApiResponse(
            responseCode = "404",
            description = "Http Status 404 NOT FOUND"
    )
    @GetMapping("/get-detail/{slug}")
    public ResponseEntity<?> getCourseDetailById(@PathVariable(value = "slug") String slug){
        return ResponseEntity.ok(coursesService.getCourseDetail(slug));
    }

    @Operation(
            summary = "Update course by ID REST API",
            description = "Update course REST API is used to update a particular course into the database"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Http Status 200 SUCCESS"
    )
    @ApiResponse(
            responseCode = "404",
            description = "Http Status 404 NOT FOUND"
    )
    @SecurityRequirement(
            name = "Bear Authentication"
    )
    @PutMapping("/update/{id}")
    public ResponseEntity<CourseResponse> updateCourse(@PathVariable(value = "id") Integer courseId,
                                                       @RequestPart(value = "course") @Valid CoursesRequest coursesRequest,
                                                       @RequestParam(value = "img", required = false) MultipartFile img){
        return ResponseEntity.ok(coursesService.update(courseId, coursesRequest, img));
    }

    @Operation(
            summary = "Delete course REST API",
            description = "Delete course REST API is used to delete a particular course from the database"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Http Status 200 SUCCESS"
    )
    @ApiResponse(
            responseCode = "404",
            description = "Http Status 404 NOT FOUND"
    )
    @SecurityRequirement(
            name = "Bear Authentication"
    )
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteCourse(@PathVariable(value = "id") Integer courseId){
        return ResponseEntity.ok(coursesService.delete(courseId));
    }

    @Operation(
            summary = "Update Course Enabled Status REST API",
            description = "This REST API is used to update the enabled status of a course identified by its ID."
    )
    @ApiResponse(
            responseCode = "200",
            description = "Http Status 200 OK - Course enabled status updated successfully"
    )
    @SecurityRequirement(
            name = "Bear Authentication"
    )
    @PostMapping("/switch-enabled")
    public ResponseEntity<?> updateIsEnabled(@RequestParam(value = "course") Integer courseId,
                                             @RequestParam(value = "enabled") boolean isEnabled){
        return ResponseEntity.ok(coursesService.updateIsEnabled(courseId, isEnabled));
    }

    @Operation(
            summary = "Update Course Published Status REST API",
            description = "This REST API is used to update the published status of a course identified by its ID."
    )
    @ApiResponse(
            responseCode = "200",
            description = "Http Status 200 OK - Course published status updated successfully"
    )
    @SecurityRequirement(
            name = "Bear Authentication"
    )
    @PostMapping("/switch-published")
    public ResponseEntity<?> updateIsPublished(@RequestParam(value = "course") Integer courseId,
                                             @RequestParam(value = "published") boolean isPublished){
        return ResponseEntity.ok(coursesService.updateIsPublished(courseId, isPublished));
    }

    @Operation(
            summary = "Update Course Finished Course Status REST API",
            description = "This REST API is used to update the finished status of a course identified by its ID."
    )
    @ApiResponse(
            responseCode = "200",
            description = "Http Status 200 OK"
    )
    @SecurityRequirement(
            name = "Bear Authentication"
    )
    @PostMapping("/switch-finished")
    public ResponseEntity<?> updateIsFinished(@RequestParam(value = "course") Integer courseId,
                                               @RequestParam(value = "finished") boolean isFinished){
        return ResponseEntity.ok(coursesService.updateIsFinished(courseId, isFinished));
    }

    @Operation(
            summary = "Search courses REST API",
            description = "This REST API is used to search all courses by keyword from the database. "
    )
    @ApiResponse(
            responseCode = "200",
            description = "Http Status 200 OK - Successful retrieval of course list"
    )
    @ApiResponse(
            responseCode = "204",
            description = "Http Status 204 No Content - No courses found"
    )
    @GetMapping("/search")
    public ResponseEntity<?> search(@RequestParam(value = "keyword") String keyword){
        List<CourseReturnSearch> listCourses = coursesService.listAllCourseByKeyword(keyword);
        if(listCourses.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(listCourses);
    }
}
