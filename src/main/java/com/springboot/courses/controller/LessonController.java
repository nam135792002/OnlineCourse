package com.springboot.courses.controller;

import com.springboot.courses.payload.lesson.LessonRequest;
import com.springboot.courses.payload.lesson.LessonResponse;
import com.springboot.courses.service.LessonService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/lessons")
public class LessonController {

    @Autowired private LessonService lessonService;

    @PostMapping("/create")
    public ResponseEntity<LessonResponse> createLesson(@RequestBody @Valid LessonRequest lessonRequest){
        return new ResponseEntity<>(lessonService.createLesson(lessonRequest), HttpStatus.CREATED);
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
