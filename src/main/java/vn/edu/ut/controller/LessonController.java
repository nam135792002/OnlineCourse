package vn.edu.ut.controller;

import vn.edu.ut.entity.TextLesson;
import vn.edu.ut.entity.Video;
import vn.edu.ut.payload.TextLessonDto;
import vn.edu.ut.payload.lesson.LessonRequest;
import vn.edu.ut.payload.lesson.LessonResponse;
import vn.edu.ut.payload.quiz.QuizRequest;
import vn.edu.ut.payload.video.VideoDto;
import vn.edu.ut.service.ILessonService;
import vn.edu.ut.service.TextLessonService;
import vn.edu.ut.service.VideoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/lessons")
@Tag(
        name = "CRUD REST APIs for Lesson Resource"
)
public class LessonController {

    @Autowired private ILessonService ILessonService;
    @Autowired private VideoService videoService;
    @Autowired private TextLessonService textLessonService;

    @Operation(
            summary = "Create lesson REST API including VIDEO and TEXT",
            description = "Create lesson REST API is used to save lesson into database"
    )
    @ApiResponse(
            responseCode = "201",
            description = "Http Status 201 CREATED"
    )
    @SecurityRequirement(
            name = "Bear Authentication"
    )
    @PostMapping("/create")
    public ResponseEntity<?> createLesson(@RequestPart(value = "lesson") @Valid LessonRequest lessonRequest,
                                          @RequestParam(value = "video_upload", required = false) MultipartFile videoUpload,
                                          @RequestPart(value = "video", required = false) VideoDto  videoDto,
                                          @RequestPart(value = "text", required = false) @Valid TextLessonDto textLessonDto,
                                          @RequestPart(value = "quizzes", required = false) @Valid QuizRequest[] quizRequest) {
        Video savedVideo = null;
        TextLesson savedText = null;
        switch (lessonRequest.getLessonType()) {
            case "VIDEO" -> savedVideo = videoService.saveVideo(videoDto, videoUpload);
            case "TEXT" -> savedText = textLessonService.createTextLesson(textLessonDto);
        }

        return new ResponseEntity<>(ILessonService.createLesson(lessonRequest, savedVideo, savedText, quizRequest), HttpStatus.CREATED);
    }

    @Operation(
            summary = "Get lesson by ID REST API",
            description = "Get lesson by ID REST API is used to get single lesson from the database"
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
    public ResponseEntity<LessonResponse> get(@PathVariable(value = "id") Integer lessonId){
        return ResponseEntity.ok(ILessonService.get(lessonId));
    }

    @Operation(
            summary = "Update lesson by ID REST API",
            description = "Update lesson REST API is used to update a particular lesson into the database"
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
    public ResponseEntity<LessonResponse> update(@PathVariable(value = "id") Integer lessonId,
                                                 @RequestPart(value = "lesson") @Valid LessonRequest lessonRequest,
                                                 @RequestParam(value = "video_upload", required = false) MultipartFile videoUpload,
                                                 @RequestPart(value = "video", required = false) VideoDto  videoDto,
                                                 @RequestPart(value = "text", required = false) @Valid TextLessonDto textLessonDto,
                                                 @RequestPart(value = "quizzes", required = false) @Valid QuizRequest[] quizRequest){
        Video savedVideo = null;
        TextLesson savedText = null;
        switch (lessonRequest.getLessonType()) {
            case "VIDEO" -> savedVideo = videoService.updateVideo(videoDto, videoUpload);
            case "TEXT" -> savedText = textLessonService.updateTextLesson(textLessonDto);
        }
        return ResponseEntity.ok(ILessonService.updateLesson(lessonId, lessonRequest, savedVideo, savedText, quizRequest));
    }

    @Operation(
            summary = "Delete lesson REST API",
            description = "Delete lesson REST API is used to delete a particular lesson from the database"
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
    public ResponseEntity<String> delete(@PathVariable(value = "id") Integer lessonId){
        return ResponseEntity.ok(ILessonService.deleteLesson(lessonId));
    }
}
