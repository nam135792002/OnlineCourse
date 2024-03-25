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
    @Autowired private ModelMapper modelMapper;

    @Override
    public VideoDto saveVideo(MultipartFile videoFile) {
        Video video = new Video();
        return savedVideoIntoDB(videoFile, video);
    }

    @Override
    public VideoDto updateVideo(Integer videoId, MultipartFile videoFile) {
        Video videoDB = videoRepository.findById(videoId)
                .orElseThrow(() -> new ResourceNotFoundException("Video", "id", videoId));

        uploadFile.deleteVideoInCloudinary(videoDB.getUrl());

        return savedVideoIntoDB(videoFile, videoDB);
    }

    @Override
    public String deleteVideo(Integer videoId) {
        Video videoDB = videoRepository.findById(videoId)
                .orElseThrow(() -> new ResourceNotFoundException("Video", "id", videoId));

        uploadFile.deleteVideoInCloudinary(videoDB.getUrl());

        videoRepository.delete(videoDB);
        return "Delete video successfully!";
    }

    @Override
    public VideoReturnResponse getVideo(Integer videoId) {
        Video videoInDB = videoRepository.findById(videoId)
                .orElseThrow(() -> new ResourceNotFoundException("Video", "id", videoId));
        return modelMapper.map(videoInDB, VideoReturnResponse.class);
    }

    private VideoDto savedVideoIntoDB(MultipartFile videoFile, Video video){
        String url = uploadFile.uploadFileOnCloudinary(videoFile);
        video.setUrl(url);

        LocalTime duration = getDurationVideo(url);
        video.setDuration(duration);

        Video savedVideo = videoRepository.save(video);

        return modelMapper.map(savedVideo, VideoDto.class);
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
