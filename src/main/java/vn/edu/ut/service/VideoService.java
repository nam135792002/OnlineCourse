package vn.edu.ut.service;

import vn.edu.ut.entity.Video;
import vn.edu.ut.payload.video.VideoDto;
import org.springframework.web.multipart.MultipartFile;

public interface VideoService {
    Video saveVideo(VideoDto videoDto, MultipartFile videoFile);
    Video updateVideo(VideoDto videoDto, MultipartFile videoFile);
}
