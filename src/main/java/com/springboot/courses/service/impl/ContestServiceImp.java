package com.springboot.courses.service.impl;

import com.springboot.courses.entity.*;
import com.springboot.courses.exception.BlogApiException;
import com.springboot.courses.exception.ResourceNotFoundException;
import com.springboot.courses.payload.contest.ContestRequest;
import com.springboot.courses.payload.contest.ContestResponse;
import com.springboot.courses.payload.contest.ContestReturnInTest;
import com.springboot.courses.payload.quiz.AnswerDto;
import com.springboot.courses.payload.quiz.QuizRequest;
import com.springboot.courses.payload.quiz.QuizReturnLearningPage;
import com.springboot.courses.repository.ContestRepository;
import com.springboot.courses.repository.QuizRepository;
import com.springboot.courses.service.ContestService;
import com.springboot.courses.utils.Utils;
import jakarta.persistence.EntityManager;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Service
@Transactional
public class ContestServiceImp implements ContestService {
    @Autowired private ContestRepository contestRepository;
    @Autowired private ModelMapper modelMapper;
    @Autowired private QuizRepository quizRepository;
    @Autowired private EntityManager entityManager;

    @Override
    public ContestResponse saveContest(ContestRequest contestRequest) {
        if(contestRepository.existsContestByTitle(contestRequest.getTitle())){
            throw new BlogApiException(HttpStatus.BAD_REQUEST, "Tên bài test này đã tồn tại trước đó!");
        }
        Contest contest = new Contest();
        contest.setTitle(contestRequest.getTitle());
        contest.setPeriod(contestRequest.getPeriod());
        contest.setEnabled(contestRequest.isEnabled());
        contest.setCreatedAt(new Date());
        for (QuizRequest quizRequest : contestRequest.getQuizList()){
            contest.add(Utils.convertToQuizEntity(quizRequest));
        }

        Contest savedContest = contestRepository.save(contest);
        return modelMapper.map(savedContest, ContestResponse.class);
    }

    @Override
    public ContestResponse getInAdministration(Integer contestId) {
        Contest contestInDB = contestRepository.findById(contestId)
                .orElseThrow(() -> new ResourceNotFoundException("Contest", "id", contestId));

        return modelMapper.map(contestInDB, ContestResponse.class);
    }


    @Override
    public String deleteContest(Integer contestId) {
        Contest contest = contestRepository.findById(contestId)
                .orElseThrow(() -> new ResourceNotFoundException("Contest", "id", contestId));

        contestRepository.delete(contest);
        return "Xóa bài thi thành công";
    }

    @Override
    public String switchEnabled(Integer contestId, boolean enabled) {
        Contest contestInDB = contestRepository.findById(contestId)
                .orElseThrow(() -> new ResourceNotFoundException("Contest", "id", contestId));

        contestRepository.switchEnabled(contestInDB.getId(), enabled);
        return "SUCCESS";
    }

    @Override
    public List<ContestResponse> listAll() {
        List<Contest> listContests = contestRepository.findAll();
        return listContests.stream().map(
                contest -> {
                    ContestResponse response = modelMapper.map(contest, ContestResponse.class);
                    response.setNumberQuestion(contest.getListQuizzes().size());
                    response.setListQuizzes(null);
                    return response;
        }).toList();
    }

    @Override
    public ContestResponse updateContest(Integer contestId, ContestRequest contestRequest) {
        Contest contestInDB = contestRepository.findById(contestId)
                .orElseThrow(() -> new ResourceNotFoundException("Contest", "id", contestId));

        Contest contestCheckDuplicate = contestRepository.findContestByTitle(contestRequest.getTitle());

        if(contestCheckDuplicate != null){
            if(!Objects.equals(contestInDB.getId(), contestCheckDuplicate.getId())){
                throw new BlogApiException(HttpStatus.BAD_REQUEST, "Tên bài test này đã tồn tại trước đó!");
            }
        }

        contestInDB.setTitle(contestRequest.getTitle());
        contestInDB.setPeriod(contestRequest.getPeriod());
        contestInDB.setEnabled(contestRequest.isEnabled());

        List<Quiz> listQuizzes = new ArrayList<>();
        for (QuizRequest quizRequest : contestRequest.getQuizList()){
            Quiz quiz = quizRequest.getId() == null ? Utils.convertToQuizEntity(quizRequest) : updateQuiz(quizRequest, contestInDB);
            quiz.setContest(contestInDB);
            listQuizzes.add(quiz);
        }
        contestInDB.setQuizList(listQuizzes);
        Contest savedContest = contestRepository.save(contestInDB);
        return modelMapper.map(savedContest, ContestResponse.class);
    }

    private Quiz updateQuiz(QuizRequest quizRequest, Contest contest) {
        Quiz quizInDB = quizRepository.findById(quizRequest.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Quiz", "id", quizRequest.getId()));

//        Quiz quizCheckDuplicate = quizRepository.findQuizByQuestionAndContest(quizRequest.getQuestion(), contest);
//        if(quizCheckDuplicate != null){
//            if(!Objects.equals(quizInDB.getId(), quizCheckDuplicate.getId())){
//                throw new BlogApiException(HttpStatus.BAD_REQUEST, "Câu hỏi đã tồn tại trong bài thi này trước đó.");
//            }
//        }

        quizInDB.setQuestion(quizRequest.getQuestion());
        quizInDB.setQuizType(QuizType.valueOf(quizRequest.getQuizType()));
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

        return quizInDB;
    }

    @Override
    public List<ContestResponse> search(String keyword) {
        List<Contest> listContests = contestRepository.search(keyword);
        return listContests.stream().map(
                contest -> {
                    ContestResponse response = modelMapper.map(contest, ContestResponse.class);
                    response.setNumberQuestion(contest.getListQuizzes().size());
                    response.setListQuizzes(null);
                    return response;
                }).toList();
    }

    @Override
    public ContestReturnInTest joinTest(Integer contestId) {
        Contest contestInDB = contestRepository.findById(contestId)
                .orElseThrow(() -> new ResourceNotFoundException("Contest", "id", contestId));

        ContestReturnInTest responseContest = modelMapper.map(contestInDB, ContestReturnInTest.class);
        List<QuizReturnLearningPage> quizReturnLearningPages = convertToQuizLearningPage(contestInDB.getListQuizzes());
        responseContest.setListQuizzes(quizReturnLearningPages);
        responseContest.setNumberQuestion(quizReturnLearningPages.size());
        return responseContest;
    }

    private List<QuizReturnLearningPage> convertToQuizLearningPage(List<Quiz> quizzes) {
        List<QuizReturnLearningPage> listQuizzes = new ArrayList<>();
        int i = 0;
        for (Quiz quiz : quizzes) {
            QuizReturnLearningPage quizReturnLearningPage = modelMapper.map(quiz, QuizReturnLearningPage.class);
            if (quiz.getQuizType().toString().equals("PERFORATE")) {
                quizReturnLearningPage.setAnswerList(null);
            }
            quizReturnLearningPage.setOrder(++i);
            listQuizzes.add(quizReturnLearningPage);
        }
        return listQuizzes;
    }
}
