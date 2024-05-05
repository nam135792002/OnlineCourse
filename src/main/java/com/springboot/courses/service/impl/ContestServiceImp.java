package com.springboot.courses.service.impl;

import com.springboot.courses.entity.Contest;
import com.springboot.courses.entity.Quiz;
import com.springboot.courses.exception.ResourceNotFoundException;
import com.springboot.courses.payload.contest.ContestRequest;
import com.springboot.courses.payload.contest.ContestResponse;
import com.springboot.courses.payload.quiz.AnswerDto;
import com.springboot.courses.payload.quiz.QuizRequest;
import com.springboot.courses.repository.ContestRepository;
import com.springboot.courses.service.ContestService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class ContestServiceImp implements ContestService {

    @Autowired private ContestRepository contestRepository;
    @Autowired private ModelMapper modelMapper;

    @Override
    public ContestResponse saveContest(ContestRequest contestRequest) {
        Contest contest = modelMapper.map(contestRequest, Contest.class);
        contest.setCreatedAt(new Date());
        for (QuizRequest quizRequest : contestRequest.getListQuiz()){
            Quiz quiz = modelMapper.map(quizRequest, Quiz.class);
            for (AnswerDto answerDto : quizRequest.getAnswerList()){
                quiz.add(answerDto.getContent(), answerDto.isCorrect());
            }
            contest.addQuiz(quiz);
        }

        Contest savedContest = contestRepository.save(contest);
        return modelMapper.map(savedContest, ContestResponse.class);
    }

    @Override
    public String deleteContest(Integer contestId) {
        Contest contest = contestRepository.findById(contestId)
                .orElseThrow(() -> new ResourceNotFoundException("Contest", "id", contestId));

        contestRepository.delete(contest);
        return "Xóa bài thi thành công";
    }
}
