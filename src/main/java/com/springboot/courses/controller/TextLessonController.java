package com.springboot.courses.controller;

import com.springboot.courses.payload.TextLessonDto;
import com.springboot.courses.service.TextLessonService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/text-lesson")
public class TextLessonController {

    @Autowired private TextLessonService textLessonService;

    @PostMapping("/create")
    public ResponseEntity<TextLessonDto> saveTextLesson(@RequestBody @Valid TextLessonDto textLessonDto){
        return new ResponseEntity<>(textLessonService.createTextLesson(textLessonDto), HttpStatus.CREATED);
    }
}
