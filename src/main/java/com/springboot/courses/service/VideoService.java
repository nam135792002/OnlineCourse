package com.springboot.courses.service;

import com.springboot.courses.entity.Video;
import com.springboot.courses.payload.video.VideoDto;
import org.springframework.web.multipart.MultipartFile;

public interface VideoService {
    Video saveVideo(VideoDto videoDto, MultipartFile videoFile);
    VideoDto updateVideo(Integer videoId, VideoDto videoDto, MultipartFile videoFile);
    String deleteVideo(Integer videoId);
}
