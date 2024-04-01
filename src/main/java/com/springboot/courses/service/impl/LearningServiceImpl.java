package com.springboot.courses.service.impl;

import com.springboot.courses.entity.*;
import com.springboot.courses.exception.ResourceNotFoundException;
import com.springboot.courses.payload.chapter.ChapterReturnDetailResponse;
import com.springboot.courses.payload.course.CourseReturnLearningPageResponse;
import com.springboot.courses.payload.lesson.LessonReturnDetailResponse;
import com.springboot.courses.payload.quiz.QuizReturnLearningPage;
import com.springboot.courses.payload.video.VideoReturnResponse;
import com.springboot.courses.repository.CoursesRepository;
import com.springboot.courses.repository.LessonRepository;
import com.springboot.courses.repository.VideoRepository;
import com.springboot.courses.service.LearningService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalTime;
import java.util.Comparator;
import java.util.List;

@Service
public class LearningServiceImpl implements LearningService {

    @Autowired private CoursesRepository coursesRepository;
    @Autowired private ModelMapper modelMapper;
    @Autowired private LessonRepository lessonRepository;
    @Autowired private VideoRepository videoRepository;

    @Override
    public CourseReturnLearningPageResponse getCourseReturnLearningPage(Integer courseId) {
        Courses coursesInDB = coursesRepository.findById(courseId)
                .orElseThrow(() -> new ResourceNotFoundException("Course", "id", courseId));

        CourseReturnLearningPageResponse course = modelMapper.map(coursesInDB, CourseReturnLearningPageResponse.class);
        sortChapterAndLesson(course);
        return course;
    }

    @Override
    public VideoReturnResponse getVideo(Integer courseId, Integer lessonId) {
        Courses courses = coursesRepository.findById(courseId)
                .orElseThrow(() -> new ResourceNotFoundException("Course", "id", courseId));

        Lesson lesson = lessonRepository.findById(lessonId)
                .orElseThrow(() -> new ResourceNotFoundException("Lesson", "id", lessonId));

        Integer videoId = lesson.getVideo().getId();

        Video video = videoRepository.findById(videoId)
                .orElseThrow(() -> new ResourceNotFoundException("Video", "id", videoId));

        return modelMapper.map(video, VideoReturnResponse.class);
    }

    @Override
    public List<QuizReturnLearningPage> getQuiz(Integer courseId, Integer lessonId) {
        Courses courses = coursesRepository.findById(courseId)
                .orElseThrow(() -> new ResourceNotFoundException("Course", "id", courseId));

        Lesson lesson = lessonRepository.findById(lessonId)
                .orElseThrow(() -> new ResourceNotFoundException("Lesson", "id", lessonId));

        List<Quiz> quizzes = lesson.getQuizList();
        return quizzes.stream().map(quiz -> modelMapper.map(quiz, QuizReturnLearningPage.class)).toList();
    }

    private void sortChapterAndLesson(CourseReturnLearningPageResponse course) {
        int totalLessonInCourse = 0;

        List<ChapterReturnDetailResponse> chapterList = course.getChapterList().stream().
                map(chapter -> modelMapper.map(chapter, ChapterReturnDetailResponse.class))
                .sorted(Comparator.comparingInt(ChapterReturnDetailResponse::getOrders)).toList();

        for (ChapterReturnDetailResponse chapter : chapterList){
            List<LessonReturnDetailResponse> listLesson = chapter.getLessonList();
            listLesson.sort(Comparator.comparingInt(LessonReturnDetailResponse::getOrders));

            Duration durationInChapter = Duration.ZERO;

            for (LessonReturnDetailResponse lesson : listLesson){
                if (lesson.getLessonType().equals(LessonType.VIDEO)){
                    Lesson lessonInDB = lessonRepository.findById(lesson.getId()).get();
                    Video video = videoRepository.findById(lessonInDB.getVideo().getId()).get();
                    lesson.setDuration(video.getDuration());

                    durationInChapter = durationInChapter.plus(
                            Duration.ofMinutes(video.getDuration().getMinute())
                                    .plusSeconds(video.getDuration().getSecond())
                    );
                }else{
                    LocalTime time = LocalTime.of(0,1,0);
                    lesson.setDuration(time);

                    durationInChapter = durationInChapter.plus(
                            Duration.ofMinutes(1)
                    );
                }
            }
            totalLessonInCourse += listLesson.size();
            chapter.setTotalLesson(listLesson.size());

            long hours = durationInChapter.toHours();
            long minutes = durationInChapter.toMinutes();
            long seconds = durationInChapter.minusMinutes(minutes).getSeconds();

            LocalTime localTime = LocalTime.of((int) hours, (int) minutes, (int) seconds);
            chapter.setDurationChapter(localTime);
        }
        course.setTotalLesson(totalLessonInCourse);
    }
}
