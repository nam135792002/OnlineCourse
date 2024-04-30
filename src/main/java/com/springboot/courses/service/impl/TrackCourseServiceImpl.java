package com.springboot.courses.service.impl;

import com.springboot.courses.entity.*;
import com.springboot.courses.exception.BlogApiException;
import com.springboot.courses.exception.ResourceNotFoundException;
import com.springboot.courses.payload.lesson.LessonReturnLearningResponse;
import com.springboot.courses.payload.quiz.QuizReturnLearningPage;
import com.springboot.courses.payload.track.InfoCourseRegistered;
import com.springboot.courses.payload.track.TrackCourseRequest;
import com.springboot.courses.payload.track.TrackCourseResponse;
import com.springboot.courses.repository.CoursesRepository;
import com.springboot.courses.repository.LessonRepository;
import com.springboot.courses.repository.TrackCourseRepository;
import com.springboot.courses.repository.UserRepository;
import com.springboot.courses.service.TrackCourseService;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.*;

@Transactional
@Service
public class TrackCourseServiceImpl implements TrackCourseService {

    @Autowired private TrackCourseRepository trackCourseRepository;
    @Autowired private UserRepository userRepository;
    @Autowired private CoursesRepository coursesRepository;
    @Autowired private LessonRepository lessonRepository;
    @Autowired private ModelMapper modelMapper;

    @Override
    public InfoCourseRegistered listTrackCourse(String email , String slug) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("Customer", "email", email));

        Courses courses = coursesRepository.findCoursesBySlug(slug)
                .orElseThrow(() -> new ResourceNotFoundException("Courses", "slug", slug));

        int averageAchieved = 0;
        int totalLessonLearned = 0;
        int totalLesson = 0;

        List<TrackCourse> listTrackCourses = sortTrackCourse(courses, user);
        List<TrackCourseResponse> listTrackCoursesResponse = new ArrayList<>();
        for (TrackCourse trackCourse : listTrackCourses){
            listTrackCoursesResponse.add(convertToResponse(trackCourse));
            if (trackCourse.isCompleted()){
                ++totalLessonLearned;
            }
            ++totalLesson;
        }
        float percent = (float) (totalLessonLearned * 100) / totalLesson;
        averageAchieved = Math.round(percent);
        return new InfoCourseRegistered(listTrackCoursesResponse, averageAchieved, totalLessonLearned);
    }

    @Override
    public Integer confirmLessonLearned(String email, Integer lessonIdPre) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("Customer", "email", email));
        Lesson lesson = lessonRepository.findById(lessonIdPre)
                        .orElseThrow(() -> new ResourceNotFoundException("Lesson", "id", lessonIdPre));

        TrackCourse trackCoursePre = trackCourseRepository.findTrackCourseByLessonAndUser(lesson, user);

        if(!trackCoursePre.isUnlock()){
            throw new BlogApiException(HttpStatus.FORBIDDEN, "Bài học này chưa được mở khóa");
        }else{
            trackCourseRepository.updateTrackCourseLessonPre(user.getId(), lessonIdPre);

            Courses courses = trackCourseRepository.findTrackCourseByLessonAndUser(lesson, user).getCourses();
            List<TrackCourse> listTrackCourses = sortTrackCourse(courses, user);
            Optional<Integer> lessonIdNext = listTrackCourses.stream()
                    .filter(track -> track.getLesson().getId().equals(lessonIdPre))
                    .map(track -> {
                        int index = listTrackCourses.indexOf(track);
                        if(index != -1 && index < listTrackCourses.size() - 1){
                            return listTrackCourses.get(index + 1).getLesson().getId();
                        }else{
                            return -1;
                        }
                    }).findFirst();

            Lesson lessonNext = lessonRepository.findById(lessonIdNext.get())
                    .orElseThrow(() -> new ResourceNotFoundException("Lesson", "id", lessonIdNext.get()));

            TrackCourse trackCourseNext = trackCourseRepository.findTrackCourseByLessonAndUser(lessonNext, user);

            if(lessonIdNext.get() != -1 && !trackCourseNext.isUnlock()){
                trackCourseRepository.updateTrackCourseLessonNext(user.getId(), lessonIdNext.get());
            }
            return lessonIdNext.get();
        }
    }

    @Override
    public LessonReturnLearningResponse getLesson(Integer lessonId) {
        Lesson lesson = lessonRepository.findById(lessonId)
                .orElseThrow(() -> new ResourceNotFoundException("Lesson", "id", lessonId));

        LessonReturnLearningResponse lessonReturn = modelMapper.map(lesson, LessonReturnLearningResponse.class);
        if(lessonReturn.getLessonType().toString().equals("QUIZ")){
            int i = 0;
            for (QuizReturnLearningPage quiz : lessonReturn.getQuizList()){
                quiz.setOrder(++i);
                if (quiz.getQuizType().toString().equals("PERFORATE")){
                    quiz.setAnswerList(null);
                }
            }
        }

        return lessonReturn;
    }

    @Override
    public String updatePeriodCurrentOfVideo(TrackCourseRequest trackCourseRequest) {
        Lesson lesson = lessonRepository.findById(trackCourseRequest.getLessonId())
                .orElseThrow(() -> new ResourceNotFoundException("Lesson", "id", trackCourseRequest.getLessonId()));

        User user = userRepository.findById(trackCourseRequest.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", trackCourseRequest.getUserId()));

        TrackCourse trackCourseInDB = trackCourseRepository.findTrackCourseByLessonAndUser(lesson, user);
        trackCourseInDB.setDurationVideo(trackCourseRequest.getPeriodCurrent());

        trackCourseRepository.save(trackCourseInDB);
        return "SUCCESS";
    }

    List<TrackCourse> sortTrackCourse(Courses courses, User user){
        List<TrackCourse> listTrackCourses = new ArrayList<>();
        for (Chapter chapter : courses.getChapterList()){
            List<TrackCourse> listTrackByChapter = trackCourseRepository.findTrackCourseByCoursesAndChapterAndUser(courses, chapter, user);
            listTrackByChapter.sort(Comparator.comparingInt(track -> track.getLesson().getOrders()));
            listTrackCourses.addAll(listTrackByChapter);
        }
        return listTrackCourses;
    }

    public TrackCourseResponse convertToResponse(TrackCourse trackCourse){
        TrackCourseResponse response = new TrackCourseResponse();
        response = modelMapper.map(trackCourse, TrackCourseResponse.class);
        response.setLessonId(trackCourse.getLesson().getId());
        return response;
    }
}
