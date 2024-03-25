package com.springboot.courses.service.impl;

import com.springboot.courses.entity.Answer;
import com.springboot.courses.entity.Lesson;
import com.springboot.courses.entity.Quiz;
import com.springboot.courses.entity.QuizType;
import com.springboot.courses.exception.BlogApiException;
import com.springboot.courses.exception.ResourceNotFoundException;
import com.springboot.courses.payload.quiz.AnswerDto;
import com.springboot.courses.payload.quiz.QuizRequest;
import com.springboot.courses.payload.quiz.QuizResponse;
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
    @Autowired private ModelMapper modelMapper;

    @Override
    public QuizResponse createQuiz(QuizRequest quizRequest) {
        Lesson lesson = lessonRepository.findById(quizRequest.getLessonId())
                .orElseThrow(() -> new ResourceNotFoundException("Lesson", "id", quizRequest.getLessonId()));

        if(quizRepository.existsQuizByQuestionAndLesson(quizRequest.getQuestion(), lesson)){
            throw new BlogApiException(HttpStatus.BAD_REQUEST, "Question have existed in this lesson");
        }

        Quiz quiz = new Quiz();
        quiz.setQuestion(quizRequest.getQuestion());
        quiz.setQuizType(QuizType.valueOf(quizRequest.getQuizType()));
        quiz.setLesson(lesson);

        boolean flag = false;

        for (AnswerDto answerDto : quizRequest.getAnswerList()){
            if(answerDto.isCorrect()){
                flag = true;
            }
            quiz.add(answerDto.getContent(), answerDto.isCorrect());
        }

        if(!flag){
            throw new BlogApiException(HttpStatus.BAD_REQUEST, "There is no right answer in list answer");
        }

        Quiz savedQuiz = quizRepository.save(quiz);

        return modelMapper.map(savedQuiz, QuizResponse.class);
    }

    @Override
    public QuizResponse updateQuiz(QuizRequest quizRequest) {
        Quiz quizInDB = quizRepository.findById(quizRequest.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Quiz", "id", quizRequest.getId()));

        Lesson lesson = lessonRepository.findById(quizRequest.getLessonId())
                .orElseThrow(() -> new ResourceNotFoundException("Lesson", "id", quizRequest.getLessonId()));

        Quiz checkQuizDuplicate = quizRepository.findQuizByQuestionAndLesson(quizRequest.getQuestion(), lesson);

        if(!Objects.equals(quizInDB.getId(), checkQuizDuplicate.getId())){
            throw new BlogApiException(HttpStatus.BAD_REQUEST, "Question have existed in this lesson");
        }

        quizInDB.setQuestion(quizRequest.getQuestion());


        List<Answer> answerList = new ArrayList<>();

        for(AnswerDto dto : quizRequest.getAnswerList()){
            Answer answer = null;
            if(dto.getId() != null){
                answer = new Answer(dto.getId(), dto.getContent(), dto.isCorrect(), quizInDB);
            }else{
                answer = new Answer(dto.getContent(), dto.isCorrect(), quizInDB);
            }

            answerList.add(answer);
        }

        quizInDB.setAnswerList(answerList);

        Quiz savedQuiz = quizRepository.save(quizInDB);

        return modelMapper.map(savedQuiz, QuizResponse.class);
    }

    @Override
    public String deleteQuiz(Integer quizId) {
        Quiz quiz = quizRepository.findById(quizId).orElseThrow(() -> new ResourceNotFoundException("Quiz", "id", quizId));
        quizRepository.delete(quiz);
        return "Delete successfully quiz";
    }


}
