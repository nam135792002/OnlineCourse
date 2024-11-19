package vn.edu.ut.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.edu.ut.entity.Answer;
import vn.edu.ut.entity.Lesson;
import vn.edu.ut.entity.Quiz;
import vn.edu.ut.exception.ResourceNotFoundException;
import vn.edu.ut.payload.lesson.LessonRequestInQuiz;
import vn.edu.ut.payload.quiz.AnswerLearningRequest;
import vn.edu.ut.payload.quiz.QuizLearningRequest;
import vn.edu.ut.repository.AnswerQuizRepository;
import vn.edu.ut.repository.LessonRepository;
import vn.edu.ut.repository.QuizRepository;
import vn.edu.ut.service.QuizService;

import java.util.List;

@Service
public class QuizServiceImpl implements QuizService {

    @Autowired
    private QuizRepository quizRepository;
    @Autowired
    private LessonRepository lessonRepository;
    @Autowired
    private AnswerQuizRepository answerQuizRepository;

    @Override
    public float gradeOfQuiz(LessonRequestInQuiz lessonRequestInQuiz) {
        Integer lessonId = lessonRequestInQuiz.getId();
        Lesson lesson = lessonRepository.findById(lessonId)
                .orElseThrow(() -> new ResourceNotFoundException("Lesson", "id", lessonId));

        float totalQuizzes = lesson.getQuizList().size();
        float correctTotalQuizzes = 0;

        for (QuizLearningRequest quizLearningRequest : lessonRequestInQuiz.getListQuizzes()) {
            Integer quizId = quizLearningRequest.getId();
            Quiz quizInDB = quizRepository.findById(quizId)
                    .orElseThrow(() -> new ResourceNotFoundException("Quiz", "id", quizId));

            if (quizInDB.getQuizType().toString().equals("ONE_CHOICE")) {
                Integer answerId = quizLearningRequest.getListAnswers().get(0).getId();
                Answer answer = answerQuizRepository.checkAnswerInCorrect(answerId);
                if (answer != null) {
                    ++correctTotalQuizzes;
                }
            } else if (quizInDB.getQuizType().toString().equals("PERFORATE")) {
                List<Answer> listAnswers = answerQuizRepository.listAllAnswerIsCorrect(quizId);
                String contentAnswer = quizLearningRequest.getListAnswers().get(0).getContentPerforate();
                for (Answer answer : listAnswers) {
                    if (contentAnswer.equalsIgnoreCase(answer.getContent())) {
                        ++correctTotalQuizzes;
                        break;
                    }
                }
            } else {
                List<Answer> listAnswers = answerQuizRepository.listAllAnswerIsCorrect(quizId);
                float totalAnswerCorrectInList = listAnswers.size();
                float totalAnswerCorrectInThere = 0.0f;
                for (AnswerLearningRequest answerLearningRequest : quizLearningRequest.getListAnswers()) {
                    Answer answer = answerQuizRepository.checkAnswerInCorrect(answerLearningRequest.getId());
                    if (answer != null) {
                        ++totalAnswerCorrectInThere;
                    } else {
                        --totalAnswerCorrectInThere;
                    }
                }
                if (totalAnswerCorrectInThere < 0) {
                    totalAnswerCorrectInThere = 0.0f;
                }
                float percentMultipleChoiceQuiz = totalAnswerCorrectInThere / totalAnswerCorrectInList;
                correctTotalQuizzes += percentMultipleChoiceQuiz;
            }
        }
        float grade = (correctTotalQuizzes * 10) / totalQuizzes;
        return (float) (Math.round(grade * 100.0) / 100.0);
    }

    @Override
    public String delete(Integer quizId) {
        Quiz quiz = quizRepository.findById(quizId).get();
        quizRepository.delete(quiz);
        return "SUCCESS";
    }
}
