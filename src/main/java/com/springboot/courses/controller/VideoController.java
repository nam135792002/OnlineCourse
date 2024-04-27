package com.springboot.courses.controller;

import com.springboot.courses.payload.video.VideoDto;
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

    @PutMapping("/update/{id}")
    public ResponseEntity<VideoDto> updateVideo(@PathVariable(value = "id") Integer videoId,
                                                @RequestPart(value = "video") VideoDto videoDto,
                                                @RequestParam(value = "video", required = false) MultipartFile video){
        return ResponseEntity.ok(videoService.updateVideo(videoId, videoDto, video));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteVideo(@PathVariable(value = "id") Integer videoId){
        return ResponseEntity.ok(videoService.deleteVideo(videoId));
    }
}
