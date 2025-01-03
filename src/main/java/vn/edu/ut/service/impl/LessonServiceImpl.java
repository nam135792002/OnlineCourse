package vn.edu.ut.service.impl;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import vn.edu.ut.entity.Answer;
import vn.edu.ut.entity.Chapter;
import vn.edu.ut.entity.Courses;
import vn.edu.ut.entity.Lesson;
import vn.edu.ut.entity.Order;
import vn.edu.ut.entity.Quiz;
import vn.edu.ut.entity.TextLesson;
import vn.edu.ut.entity.TrackCourse;
import vn.edu.ut.entity.Video;
import vn.edu.ut.enums.LessonType;
import vn.edu.ut.enums.QuizType;
import vn.edu.ut.exception.AppException;
import vn.edu.ut.exception.ResourceNotFoundException;
import vn.edu.ut.payload.lesson.LessonRequest;
import vn.edu.ut.payload.lesson.LessonResponse;
import vn.edu.ut.payload.quiz.AnswerDto;
import vn.edu.ut.payload.quiz.QuizRequest;
import vn.edu.ut.repository.ChapterRepository;
import vn.edu.ut.repository.LessonRepository;
import vn.edu.ut.repository.OrderRepository;
import vn.edu.ut.repository.QuizRepository;
import vn.edu.ut.repository.TrackCourseRepository;
import vn.edu.ut.service.ILessonService;
import vn.edu.ut.utils.UploadFile;
import vn.edu.ut.utils.Utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class LessonServiceImpl implements ILessonService {
    private final LessonRepository lessonRepository;
    private final ChapterRepository chapterRepository;
    private final ModelMapper modelMapper;
    private final UploadFile uploadFile;
    private final OrderRepository orderRepository;
    private final TrackCourseRepository trackCourseRepository;
    private final QuizRepository quizRepository;

    @Override
    public LessonResponse createLesson(LessonRequest lessonRequest, Video video, TextLesson textLesson, QuizRequest[] quizRequest) {
        Chapter chapter = chapterRepository.findById(lessonRequest.getChapterId())
                .orElseThrow(() -> new ResourceNotFoundException("Chapter", "id", lessonRequest.getChapterId()));

        if (lessonRepository.existsLessonByNameAndChapter(lessonRequest.getName(), chapter)) {
            throw new AppException(HttpStatus.BAD_REQUEST, "Tên bài học đã từng tồn tại trong chương này!");
        }


        Lesson lesson = new Lesson();
        lesson.setName(lessonRequest.getName());
        lesson.setLessonType(LessonType.valueOf(lessonRequest.getLessonType()));
        lesson.setChapter(chapter);
        lesson.setVideo(video);
        lesson.setText(textLesson);
        lesson.setOrders(lessonRequest.getOrders());

        if (lessonRequest.getLessonType().equals("QUIZ") && quizRequest != null) {
            for (QuizRequest quiz : quizRequest) {
                lesson.add(Utils.convertToQuizEntity(quiz));
            }
        }

        Lesson savedLesson = lessonRepository.save(lesson);

        Courses courses = savedLesson.getChapter().getCourse();
        List<Order> listOrder = orderRepository.findAllByCourses(courses);
        if (!listOrder.isEmpty()) {
            for (Order order : listOrder) {
                TrackCourse trackCourse = new TrackCourse();
                trackCourse.setUser(order.getUser());
                trackCourse.setCourses(order.getCourses());
                trackCourse.setChapter(savedLesson.getChapter());
                trackCourse.setLesson(savedLesson);

                TrackCourse trackCourseCurrentLesson = trackCourseRepository.findTrackCoursesByCurrent(courses.getId(), order.getUser().getId());
                if (trackCourseCurrentLesson != null) {
                    int chapterCurrentLessIdOrder = trackCourseCurrentLesson.getChapter().getOrders();
                    int lessonCurrentLessIdOrder = trackCourseCurrentLesson.getLesson().getOrders();
                    if (chapterCurrentLessIdOrder > savedLesson.getChapter().getOrders()) {
                        trackCourse.setUnlock(true);
                    } else if (chapterCurrentLessIdOrder == savedLesson.getChapter().getOrders()) {
                        if (lessonCurrentLessIdOrder > savedLesson.getOrders()) {
                            trackCourse.setUnlock(true);
                        }
                    }
                } else {
                    trackCourse.setUnlock(true);
                    trackCourse.setCurrent(true);
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
    public LessonResponse updateLesson(Integer lessonId, LessonRequest lessonRequest, Video video, TextLesson textLesson, QuizRequest[] quizRequest) {
        Chapter chapter = chapterRepository.findById(lessonRequest.getChapterId())
                .orElseThrow(() -> new ResourceNotFoundException("Chapter", "id", lessonRequest.getChapterId()));

        Lesson lessonInDB = lessonRepository.findById(lessonId)
                .orElseThrow(() -> new ResourceNotFoundException("Lesson", "id", lessonId));

        Lesson lessonCheckDuplicate = lessonRepository.findLessonByNameAndChapter(lessonRequest.getName(), chapter);
        if (lessonCheckDuplicate != null) {
            if (!Objects.equals(lessonInDB.getId(), lessonCheckDuplicate.getId())) {
                throw new AppException(HttpStatus.BAD_REQUEST, "Tên bài học đã từng tồn tại trong chương này!");
            }
        }

        lessonInDB.setName(lessonRequest.getName());

        if (video != null && video.getId() != null) {
            lessonInDB.setVideo(video);
        }

        if (textLesson != null && textLesson.getId() != null) {
            lessonInDB.setText(textLesson);
        }

        if (lessonRequest.getLessonType().equals("QUIZ") && quizRequest != null) {
            List<Quiz> listQuizzes = new ArrayList<>();
            for (QuizRequest quizInList : quizRequest) {
                Quiz quiz = quizInList.getId() == null ? Utils.convertToQuizEntity(quizInList) : updateQuiz(quizInList, lessonInDB);
                quiz.setLesson(lessonInDB);
                listQuizzes.add(quiz);
            }

            lessonInDB.setQuizList(listQuizzes);
        }

        return convertToResponse(lessonRepository.save(lessonInDB));
    }

    private Quiz updateQuiz(QuizRequest quizRequest, Lesson lesson) {
        Quiz quizInDB = quizRepository.findById(quizRequest.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Quiz", "id", quizRequest.getId()));

        Quiz checkQuizDuplicate = quizRepository.findQuizByQuestionAndLesson(quizRequest.getQuestion(), lesson);

        if (checkQuizDuplicate != null) {
            if (!Objects.equals(quizInDB.getId(), checkQuizDuplicate.getId())) {
                throw new AppException(HttpStatus.BAD_REQUEST, "Câu hỏi đã từng tồn tại trong bài học này!");
            }
        }

        quizInDB.setQuestion(quizRequest.getQuestion());
        quizInDB.setQuizType(QuizType.valueOf(quizRequest.getQuizType()));
        List<Answer> answerList = new ArrayList<>();

        for (AnswerDto dto : quizRequest.getAnswerList()) {
            Answer answer = null;
            if (dto.getId() != null) {
                answer = new Answer(dto.getId(), dto.getContent(), dto.isCorrect(), quizInDB);
            } else {
                answer = new Answer(dto.getContent(), dto.isCorrect(), quizInDB);
            }

            answerList.add(answer);
        }

        quizInDB.setAnswerList(answerList);

        return quizInDB;
    }

    @Override
    public String deleteLesson(Integer lessonId) {
        Lesson lessonInDB = lessonRepository.findById(lessonId)
                .orElseThrow(() -> new ResourceNotFoundException("Lesson", "id", lessonId));

        if (lessonInDB.getVideo() != null) {
            String url = lessonInDB.getVideo().getUrl();
            uploadFile.deleteVideoInCloudinary(url);
        }

        lessonRepository.delete(lessonInDB);
        return "Xóa bài học thành công";
    }

    @Override
    public Courses getCourse(Integer lessonId) {
        Lesson lesson = lessonRepository.findById(lessonId).get();
        return lesson.getChapter().getCourse();
    }

    private LessonResponse convertToResponse(Lesson lesson) {
        LessonResponse response = modelMapper.map(lesson, LessonResponse.class);
        response.setChapterId(lesson.getChapter().getId());

        return response;
    }
}
