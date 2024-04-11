package com.springboot.courses.service.impl;

import com.springboot.courses.entity.Courses;
import com.springboot.courses.entity.TrackCourse;
import com.springboot.courses.entity.User;
import com.springboot.courses.exception.ResourceNotFoundException;
import com.springboot.courses.payload.TrackCourses.InfoCourseRegistered;
import com.springboot.courses.payload.TrackCourses.TrackCourseResponse;
import com.springboot.courses.repository.CoursesRepository;
import com.springboot.courses.repository.TrackCourseRepository;
import com.springboot.courses.repository.UserRepository;
import com.springboot.courses.service.TrackCourseService;
import com.springboot.courses.utils.Utils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Transactional
@Service
public class TrackCourseServiceImpl implements TrackCourseService {

    @Autowired private TrackCourseRepository trackCourseRepository;
    @Autowired private UserRepository userRepository;
    @Autowired private CoursesRepository coursesRepository;
    @Autowired private ModelMapper modelMapper;

    @Override
    public InfoCourseRegistered listTrackCourse(HttpServletRequest request, String slug) {
        String email = Utils.getEmailOfAuthenticatedCustomer(request);
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("Customer", "email", email));

        Courses courses = coursesRepository.findCoursesBySlug(slug)
                .orElseThrow(() -> new ResourceNotFoundException("Courses", "slug", slug));

        int averageAchieved = 0;
        int totalLessonLearned = 0;
        int totalLesson = 0;

        List<TrackCourse> listTrackCourses = trackCourseRepository.findAllByCoursesAndUser(courses, user);
        List<TrackCourseResponse> listTrackCoursesResponse = new ArrayList<>();
        for (TrackCourse trackCourse : listTrackCourses){
            listTrackCoursesResponse.add(convertToResponse(trackCourse));
            if (trackCourse.isCompleted()){
                ++totalLessonLearned;
            }
            ++totalLesson;
        }
        float percent = (float) (totalLessonLearned * 100) / totalLesson;
        averageAchieved = (int) Math.round(percent);
        return new InfoCourseRegistered(listTrackCoursesResponse, averageAchieved, totalLessonLearned);
    }

    @Override
    public String confirmLessonLearned(HttpServletRequest request, Integer lessonIdPre, Integer lessonIdNext) {
        String email = Utils.getEmailOfAuthenticatedCustomer(request);
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("Customer", "email", email));
        trackCourseRepository.updateTrackCourseLessonPre(user.getId(), lessonIdPre);
        trackCourseRepository.updateTrackCourseLessonNext(user.getId(), lessonIdNext);
        return "Bạn đã hoàn thành xong bài học này!";
    }

    public TrackCourseResponse convertToResponse(TrackCourse trackCourse){
        TrackCourseResponse response = new TrackCourseResponse();
        response = modelMapper.map(trackCourse, TrackCourseResponse.class);
        response.setLessonId(trackCourse.getLesson().getId());
        return response;
    }
}
