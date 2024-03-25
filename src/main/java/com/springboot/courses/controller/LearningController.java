package com.springboot.courses.controller;

import com.springboot.courses.service.VideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/learning")
public class LearningController {

    @Autowired private VideoService videoService;

    @GetMapping("/courses/{id}")
    public ResponseEntity<?> getDetailInLearningPage(@PathVariable(value = "id") Integer courseId){
        return null;
    }

    @GetMapping("/courses/video/{id}")
    public ResponseEntity<?> getVideoInDetailCourse(@PathVariable(value = "id") Integer videoId){
        return ResponseEntity.ok(videoService.getVideo(videoId));
    }
}
