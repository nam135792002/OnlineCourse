package vn.edu.ut.service;

import vn.edu.ut.payload.lesson.LessonReturnLearningResponse;
import vn.edu.ut.payload.track.InfoCourseRegistered;
import vn.edu.ut.payload.track.TrackCourseRequest;

public interface TrackCourseService {
    InfoCourseRegistered listTrackCourse(String email , String slug);
    Integer confirmLessonLearned(String email , Integer lessonIdPre);
    LessonReturnLearningResponse getLesson(Integer lessonId);
    String updatePeriodCurrentOfVideo(TrackCourseRequest trackCourseRequest);
}
