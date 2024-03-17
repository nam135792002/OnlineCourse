package com.springboot.courses.controller;

import com.springboot.courses.payload.VideoDto;
import com.springboot.courses.service.VideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("api/videos")
public class VideoController {

    @Autowired private VideoService videoService;

    @PostMapping("/save")
    public ResponseEntity<VideoDto> saveVideo(@RequestParam(value = "video") MultipartFile video){
        return new ResponseEntity<>(videoService.saveVideo(video), HttpStatus.CREATED);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<VideoDto> updateVideo(@PathVariable(value = "id") Integer videoId,
                                                @RequestParam(value = "video") MultipartFile video){
        return ResponseEntity.ok(videoService.updateVideo(videoId, video));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteVideo(@PathVariable(value = "id") Integer videoId){
        return ResponseEntity.ok(videoService.deleteVideo(videoId));
    }
}
