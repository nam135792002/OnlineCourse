package com.springboot.courses.service;

import com.springboot.courses.payload.video.VideoDto;
import com.springboot.courses.payload.video.VideoReturnResponse;
import org.springframework.web.multipart.MultipartFile;

public interface VideoService {
    VideoDto saveVideo(VideoDto videoDto, MultipartFile videoFile);
    VideoDto updateVideo(Integer videoId, VideoDto videoDto, MultipartFile videoFile);
    String deleteVideo(Integer videoId);
}
