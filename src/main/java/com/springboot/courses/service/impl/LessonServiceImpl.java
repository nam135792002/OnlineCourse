package com.springboot.courses.service.impl;

import com.springboot.courses.entity.*;
import com.springboot.courses.exception.BlogApiException;
import com.springboot.courses.exception.ResourceNotFoundException;
import com.springboot.courses.payload.TextLessonDto;
import com.springboot.courses.payload.lesson.LessonRequest;
import com.springboot.courses.payload.lesson.LessonResponse;
import com.springboot.courses.repository.*;
import com.springboot.courses.service.LessonService;
import com.springboot.courses.utils.UploadFile;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Objects;

@Service
public class LessonServiceImpl implements LessonService {

    @Autowired private LessonRepository lessonRepository;
    @Autowired private ChapterRepository chapterRepository;
    @Autowired private VideoRepository videoRepository;
    @Autowired private TextLessonRepository textLessonRepository;
    @Autowired private ModelMapper modelMapper;
    @Autowired private UploadFile uploadFile;
    @Autowired private OrderRepository orderRepository;
    @Autowired private TrackCourseRepository trackCourseRepository;

    @Override
    public LessonResponse createLesson(LessonRequest lessonRequest) {
        Chapter chapter = chapterRepository.findById(lessonRequest.getChapterId())
                .orElseThrow(() -> new ResourceNotFoundException("Chapter", "id", lessonRequest.getChapterId()));

        // save video if possible
        Video video = null;
        if(lessonRequest.getVideoId() != null){
            video = videoRepository.findById(lessonRequest.getVideoId())
                    .orElseThrow(() -> new ResourceNotFoundException("Video", "id", lessonRequest.getVideoId()));

            if(lessonRepository.existsLessonByVideo(video)){
                throw new BlogApiException(HttpStatus.BAD_REQUEST, "Video have existed!");
            }

        }

        // save lesson by text if possible
        TextLesson textLesson = null;
        if(lessonRequest.getTextId() != null){
            textLesson = textLessonRepository.findById(lessonRequest.getTextId())
                    .orElseThrow(() -> new ResourceNotFoundException("Text", "id", lessonRequest.getTextId()));

            if(lessonRepository.existsLessonByText(textLesson)){
                throw new BlogApiException(HttpStatus.BAD_REQUEST, "Text lesson have existed!");
            }
        }

        if(lessonRepository.existsLessonByNameAndChapter(lessonRequest.getName(),chapter)){
            throw new BlogApiException(HttpStatus.BAD_REQUEST, "Lesson name have existed in this chapter.");
        }


        Lesson lesson = new Lesson();
        lesson.setName(lessonRequest.getName());
        lesson.setCreatedAt(new Date());
        lesson.setLessonType(LessonType.valueOf(lessonRequest.getLessonType()));
        lesson.setChapter(chapter);
        lesson.setVideo(video);
        lesson.setText(textLesson);
        lesson.setOrders(lessonRequest.getOrders());

        Lesson savedLesson = lessonRepository.save(lesson);

        Courses courses = savedLesson.getChapter().getCourse();
        List<Order> listOrder = orderRepository.findAllByCourses(courses);
        if (!listOrder.isEmpty()){
            for (Order order : listOrder){
                TrackCourse trackCourse = new TrackCourse();
                trackCourse.setUser(order.getUser());
                trackCourse.setCourses(order.getCourses());
                trackCourse.setChapter(savedLesson.getChapter());
                trackCourse.setLesson(savedLesson);

                TrackCourse trackCourseCurrentLesson = trackCourseRepository.findTrackCoursesByCurrent(courses.getId(), order.getUser().getId());
                int chapterCurrentLessIdOrder = trackCourseCurrentLesson.getChapter().getOrders();
                int lessonCurrentLessIdOrder = trackCourseCurrentLesson.getLesson().getOrders();
                if(chapterCurrentLessIdOrder > savedLesson.getChapter().getOrders()){
                    trackCourse.setUnlock(true);
                }else if(chapterCurrentLessIdOrder == savedLesson.getChapter().getOrders()){
                    if(lessonCurrentLessIdOrder > savedLesson.getOrders()){
                        trackCourse.setUnlock(true);
                    }
                }
                trackCourseRepository.save(trackCourse);
            }
        }
        return convertToResponse(savedLesson);
    }

    @Override
    public LessonResponse get(Integer lessonId) {
        Lesson lesson = lessonRepository.findById(lessonId)
                .orElseThrow(() -> new ResourceNotFoundException("Lesson", "id", lessonId));


        return convertToResponse(lesson);
    }

    @Override
    public LessonResponse updateLesson(Integer lessonId, LessonRequest lessonRequest) {
        Chapter chapter = chapterRepository.findById(lessonRequest.getChapterId())
                .orElseThrow(() -> new ResourceNotFoundException("Chapter", "id", lessonRequest.getChapterId()));

        Video video = null;
        if(lessonRequest.getVideoId() != null){
            video = videoRepository.findById(lessonRequest.getVideoId())
                    .orElseThrow(() -> new ResourceNotFoundException("Video", "id", lessonRequest.getVideoId()));
        }

        Lesson lessonInDB = lessonRepository.findById(lessonId)
                .orElseThrow(() -> new ResourceNotFoundException("Lesson", "id", lessonId));

        Lesson lessonCheckDuplicate = lessonRepository.findLessonByNameAndChapter(lessonRequest.getName(), chapter);
        if (lessonCheckDuplicate != null){
            if(!Objects.equals(lessonInDB.getId(), lessonCheckDuplicate.getId())){
                throw new BlogApiException(HttpStatus.BAD_REQUEST, "Lesson name have existed in this chapter");
            }
        }

        lessonInDB.setName(lessonRequest.getName());
        lessonInDB.setVideo(video);

       return convertToResponse(lessonRepository.save(lessonInDB));
    }

    @Override
    public String deleteLesson(Integer lessonId) {
        Lesson lessonInDB = lessonRepository.findById(lessonId)
                .orElseThrow(() -> new ResourceNotFoundException("Lesson", "id", lessonId));

        if(lessonInDB.getVideo() != null){
            String url = lessonInDB.getVideo().getUrl();
            uploadFile.deleteVideoInCloudinary(url);
        }

        lessonRepository.delete(lessonInDB);



        return "Delete lesson successfully!";
    }

    private LessonResponse convertToResponse(Lesson lesson){
        LessonResponse response = modelMapper.map(lesson, LessonResponse.class);
        response.setChapterId(lesson.getChapter().getId());

        return response;
    }
}
