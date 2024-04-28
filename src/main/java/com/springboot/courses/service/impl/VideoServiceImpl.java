package com.springboot.courses.service.impl;

import com.springboot.courses.entity.Video;
import com.springboot.courses.exception.BlogApiException;
import com.springboot.courses.exception.ResourceNotFoundException;
import com.springboot.courses.payload.video.VideoDto;
import com.springboot.courses.payload.video.VideoReturnResponse;
import com.springboot.courses.repository.VideoRepository;
import com.springboot.courses.service.VideoService;
import com.springboot.courses.utils.UploadFile;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ws.schild.jave.EncoderException;
import ws.schild.jave.MultimediaObject;
import ws.schild.jave.info.MultimediaInfo;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalTime;

@Service
public class VideoServiceImpl implements VideoService {

    @Autowired private VideoRepository videoRepository;
    @Autowired private UploadFile uploadFile;

    @Override
    public Video saveVideo(VideoDto videoDto, MultipartFile videoFile) {
        Video video = new Video();
        String url = uploadFile.uploadFileOnCloudinary(videoFile);
        video.setUrl(url);

        LocalTime duration = getDurationVideo(url);
        video.setDuration(duration);

        video.setDescription(videoDto.getDescription());

        return videoRepository.save(video);
    }

    @Override
    public Video updateVideo(VideoDto videoDto, MultipartFile videoFile) {
        Video videoDB = videoRepository.findById(videoDto.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Video", "id", videoDto.getId()));
        if(videoFile != null){
            uploadFile.deleteVideoInCloudinary(videoDB.getUrl());
            String url = uploadFile.uploadFileOnCloudinary(videoFile);
            videoDB.setUrl(url);

            LocalTime duration = getDurationVideo(url);
            videoDB.setDuration(duration);

        }
        videoDB.setDescription(videoDto.getDescription());
        return videoRepository.save(videoDB);

    }

    private LocalTime getDurationVideo(String url){
        try {
            URL url1 = new URL(url);
            MultimediaObject multimediaObject = new MultimediaObject(url1);
            MultimediaInfo multimediaInfo = multimediaObject.getInfo();

            long minutes = (multimediaInfo.getDuration() / 1000) / 60;
            long seconds = (multimediaInfo.getDuration() / 1000) % 60;

            return LocalTime.of(0,(int) minutes, (int) seconds);
        } catch (MalformedURLException | EncoderException e) {
            throw new BlogApiException(HttpStatus.BAD_REQUEST, "Upload video failed!");
        }
    }
}
