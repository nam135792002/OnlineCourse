package vn.edu.ut.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import vn.edu.ut.constant.AppConstants;
import vn.edu.ut.payload.ClassResponse;
import vn.edu.ut.payload.course.CourseResponse;
import vn.edu.ut.payload.course.CourseReturnHomePageResponse;
import vn.edu.ut.payload.course.CourseReturnSearch;
import vn.edu.ut.payload.course.CoursesRequest;
import vn.edu.ut.service.ICoursesService;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/courses")
@RequiredArgsConstructor
@Tag(
        name = "CRUD REST APIs for Course Resource"
)
public class CourseController {
    private final ICoursesService iCoursesService;

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
    public ResponseEntity<CourseResponse> createCourse(@RequestPart(value = "course") @Valid CoursesRequest coursesRequest,
                                                       @RequestParam(value = "img") MultipartFile img) {
        CourseResponse courseResponse = iCoursesService.createCourse(coursesRequest, img);
        URI uri = URI.create("/api/courses/get/" + courseResponse.getId());

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
    ) {
        ClassResponse classResponse = iCoursesService.getAll(pageNo, pageSize, sortBy, sortDir, keyword, categoryId);
        if (classResponse.getContent().isEmpty()) {
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
    public ResponseEntity<CourseResponse> getCourseById(@PathVariable(value = "id") Integer courseId) {
        return ResponseEntity.ok(iCoursesService.get(courseId));
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
    public ResponseEntity<?> getCourseReturnHomePage(@RequestParam(value = "categoryId", required = false) Integer categoryId) {
        List<CourseReturnHomePageResponse> listCourses = iCoursesService.getCourseIntoHomePage(categoryId);
        if (listCourses.isEmpty()) {
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
    public ResponseEntity<?> getCourseDetailById(@PathVariable(value = "slug") String slug) {
        return ResponseEntity.ok(iCoursesService.getCourseDetail(slug));
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
                                                       @RequestParam(value = "img", required = false) MultipartFile img) {
        return ResponseEntity.ok(iCoursesService.update(courseId, coursesRequest, img));
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
    public ResponseEntity<Void> deleteCourse(@PathVariable(value = "id") Integer courseId) {
        iCoursesService.delete(courseId);
        return ResponseEntity.noContent().build();
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
    public ResponseEntity<Void> updateIsEnabled(@RequestParam(value = "course") Integer courseId,
                                                @RequestParam(value = "enabled") boolean isEnabled) {
        iCoursesService.updateIsEnabled(courseId, isEnabled);
        return ResponseEntity.ok().build();
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
    public ResponseEntity<Void> updateIsPublished(@RequestParam(value = "course") Integer courseId,
                                                  @RequestParam(value = "published") boolean isPublished) {
        iCoursesService.updateIsPublished(courseId, isPublished);
        return ResponseEntity.ok().build();
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
    public ResponseEntity<Void> updateIsFinished(@RequestParam(value = "course") Integer courseId,
                                                 @RequestParam(value = "finished") boolean isFinished) {
        iCoursesService.updateIsFinished(courseId, isFinished);
        return ResponseEntity.ok().build();
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
    public ResponseEntity<?> search(@RequestParam(value = "keyword") String keyword) {
        List<CourseReturnSearch> listCourses = iCoursesService.listAllCourseByKeyword(keyword);
        if (listCourses.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(listCourses);
    }
}
