package com.springboot.courses.service;

import com.springboot.courses.payload.course.CourseReturnLearningPageResponse;
import com.springboot.courses.payload.quiz.QuizReturnLearningPage;
import com.springboot.courses.payload.video.VideoReturnResponse;

import java.util.List;

public interface LearningService {

    CourseReturnLearningPageResponse getCourseReturnLearningPage(Integer courseId);
    VideoReturnResponse getVideo(Integer courseId, Integer lessonId);
    List<QuizReturnLearningPage> getQuiz(Integer courseId, Integer lessonId);
}
