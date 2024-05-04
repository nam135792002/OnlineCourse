package com.springboot.courses.service.impl;

import com.springboot.courses.entity.Answer;
import com.springboot.courses.entity.Lesson;
import com.springboot.courses.entity.Quiz;
import com.springboot.courses.entity.QuizType;
import com.springboot.courses.exception.BlogApiException;
import com.springboot.courses.exception.ResourceNotFoundException;
import com.springboot.courses.payload.lesson.LessonRequestInQuiz;
import com.springboot.courses.payload.quiz.*;
import com.springboot.courses.repository.AnswerQuizRepository;
import com.springboot.courses.repository.LessonRepository;
import com.springboot.courses.repository.QuizRepository;
import com.springboot.courses.service.QuizService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class QuizServiceImpl implements QuizService {

    @Autowired private QuizRepository quizRepository;
    @Autowired private LessonRepository lessonRepository;
    @Autowired private AnswerQuizRepository answerQuizRepository;

    @Override
    public float gradeOfQuiz(LessonRequestInQuiz lessonRequestInQuiz) {
        Integer lessonId = lessonRequestInQuiz.getId();
        Lesson lesson = lessonRepository.findById(lessonId)
                .orElseThrow(() -> new ResourceNotFoundException("Lesson", "id", lessonId));

        float totalQuizzes = lesson.getQuizList().size();
        float correctTotalQuizzes = 0;

        for (QuizLearningRequest quizLearningRequest : lessonRequestInQuiz.getListQuizzes()){
            Integer quizId = quizLearningRequest.getId();
            Quiz quizInDB = quizRepository.findById(quizId)
                    .orElseThrow(() -> new ResourceNotFoundException("Quiz", "id", quizId));

            if(quizInDB.getQuizType().toString().equals("ONE_CHOICE")){
                Integer answerId = quizLearningRequest.getListAnswers().get(0).getId();
                Answer answer = answerQuizRepository.checkAnswerInCorrect(answerId);
                if(answer != null) {
                    ++correctTotalQuizzes;
                }
            }else if(quizInDB.getQuizType().toString().equals("PERFORATE")){
                List<Answer> listAnswers = answerQuizRepository.listAllAnswerIsCorrect(quizId);
                String contentAnswer = quizLearningRequest.getListAnswers().get(0).getContentPerforate();
                for (Answer answer : listAnswers){
                    if(contentAnswer.equalsIgnoreCase(answer.getContent())){
                        ++correctTotalQuizzes;
                        break;
                    }
                }
            }else{
                List<Answer> listAnswers = answerQuizRepository.listAllAnswerIsCorrect(quizId);
                float totalAnswerCorrectInList = listAnswers.size();
                float totalAnswerCorrectInThere = 0.0f;
                for (AnswerLearningRequest answerLearningRequest : quizLearningRequest.getListAnswers()){
                    Answer answer = answerQuizRepository.checkAnswerInCorrect(answerLearningRequest.getId());
                    if(answer != null){
                        ++totalAnswerCorrectInThere;
                    }else{
                        --totalAnswerCorrectInThere;
                    }
                }
                if(totalAnswerCorrectInThere < 0){
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
