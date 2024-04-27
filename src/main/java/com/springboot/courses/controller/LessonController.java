package com.springboot.courses.controller;

import com.springboot.courses.entity.TextLesson;
import com.springboot.courses.entity.Video;
import com.springboot.courses.exception.BlogApiException;
import com.springboot.courses.payload.TextLessonDto;
import com.springboot.courses.payload.lesson.LessonRequest;
import com.springboot.courses.payload.lesson.LessonResponse;
import com.springboot.courses.payload.quiz.QuizRequest;
import com.springboot.courses.payload.video.VideoDto;
import com.springboot.courses.service.LessonService;
import com.springboot.courses.service.QuizService;
import com.springboot.courses.service.TextLessonService;
import com.springboot.courses.service.VideoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/lessons")
public class LessonController {

    @Autowired private LessonService lessonService;
    @Autowired private VideoService videoService;
    @Autowired private TextLessonService textLessonService;

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

        return new ResponseEntity<>(lessonService.createLesson(lessonRequest, savedVideo, savedText, quizRequest), HttpStatus.CREATED);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<LessonResponse> get(@PathVariable(value = "id") Integer lessonId){
        return ResponseEntity.ok(lessonService.get(lessonId));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<LessonResponse> update(@PathVariable(value = "id") Integer lessonId,
                                                 @RequestBody @Valid LessonRequest lessonRequest){
        return ResponseEntity.ok(lessonService.updateLesson(lessonId, lessonRequest));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable(value = "id") Integer lessonId){
        return ResponseEntity.ok(lessonService.deleteLesson(lessonId));
    }
}
