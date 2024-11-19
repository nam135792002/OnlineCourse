package vn.edu.ut.service;

import vn.edu.ut.payload.TextLessonDto;
import vn.edu.ut.payload.course.CourseReturnLearningPageResponse;
import vn.edu.ut.payload.course.CourseReturnMyLearning;
import vn.edu.ut.payload.quiz.QuizReturnLearningPage;
import vn.edu.ut.payload.video.VideoReturnResponse;

import java.util.List;

public interface LearningService {

    CourseReturnLearningPageResponse getCourseReturnLearningPage(String slug);
    VideoReturnResponse getVideo(Integer lessonId);
    List<QuizReturnLearningPage> getQuiz(Integer lessonId);
    TextLessonDto getText(Integer lessonId);
    List<CourseReturnMyLearning> listAllCourseRegisteredByCustomer(String email);
    boolean isRegisterInThisCourse(String slug, String email);

}
