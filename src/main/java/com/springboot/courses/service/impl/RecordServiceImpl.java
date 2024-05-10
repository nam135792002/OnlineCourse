package com.springboot.courses.service.impl;

import com.springboot.courses.entity.*;
import com.springboot.courses.entity.Record;
import com.springboot.courses.exception.ResourceNotFoundException;
import com.springboot.courses.payload.quiz.AnswerLearningRequest;
import com.springboot.courses.payload.quiz.QuizLearningRequest;
import com.springboot.courses.payload.record.RecordRequest;
import com.springboot.courses.payload.record.RecordResponse;
import com.springboot.courses.repository.*;
import com.springboot.courses.service.RecordService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class RecordServiceImpl implements RecordService {

    @Autowired private RecordRepository recordRepository;
    @Autowired private UserRepository userRepository;
    @Autowired private ContestRepository contestRepository;
    @Autowired private QuizRepository quizRepository;
    @Autowired private AnswerQuizRepository answerQuizRepository;
    @Autowired private ModelMapper modelMapper;

    @Override
    public RecordResponse saveRecord(RecordRequest recordRequest) {
        User user = userRepository.findById(recordRequest.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", recordRequest.getUserId()));

        Contest contest = contestRepository.findById(recordRequest.getContestId())
                .orElseThrow(() -> new ResourceNotFoundException("Contest", "id", recordRequest.getContestId()));

        Record record = new Record();
        record.setUser(user);
        record.setContest(contest);
        record.setPeriod(recordRequest.getPeriod());
        record.setJoinedAt(new Date());

        float totalQuizzes = contest.getListQuizzes().size();
        float correctTotalQuizzes = 0;

        for (QuizLearningRequest quizLearningRequest : recordRequest.getListQuizzes()){
            Integer quizId = quizLearningRequest.getId();
            Quiz quizInDB = quizRepository.findById(quizId)
                    .orElseThrow(() -> new ResourceNotFoundException("Quiz", "id", quizId));

            Set<Answer> answers = new HashSet<>();
            String contentPerforate = null;

            if(quizInDB.getQuizType().toString().equals("ONE_CHOICE")){
                Integer answerId = quizLearningRequest.getListAnswers().get(0).getId();

                Answer answerByIDInDB = answerQuizRepository.findById(answerId)
                        .orElseThrow(() -> new ResourceNotFoundException("Answer", "id", answerId));

                Answer answer = answerQuizRepository.checkAnswerInCorrect(answerId);
                if(answer != null) {
                    ++correctTotalQuizzes;
                }

                answers.add(answerByIDInDB);

            }else if(quizInDB.getQuizType().toString().equals("PERFORATE")){
                List<Answer> listAnswers = answerQuizRepository.listAllAnswerIsCorrect(quizId);
                String contentAnswer = quizLearningRequest.getListAnswers().get(0).getContentPerforate();
                for (Answer answer : listAnswers){
                    if(contentAnswer.equalsIgnoreCase(answer.getContent())){
                        ++correctTotalQuizzes;
                        break;
                    }
                }

                contentPerforate = contentAnswer;
            }else{
                List<Answer> listAnswers = answerQuizRepository.listAllAnswerIsCorrect(quizId);
                float totalAnswerCorrectInList = listAnswers.size();
                float totalAnswerCorrectInThere = 0.0f;
                for (AnswerLearningRequest answerLearningRequest : quizLearningRequest.getListAnswers()){
                    Integer answerId = answerLearningRequest.getId();

                    Answer answer = answerQuizRepository.checkAnswerInCorrect(answerId);

                    Answer answerByIDInDB = answerQuizRepository.findById(answerId)
                            .orElseThrow(() -> new ResourceNotFoundException("Answer", "id", answerId));

                    if(answer != null){
                        ++totalAnswerCorrectInThere;
                    }else{
                        --totalAnswerCorrectInThere;
                    }

                    answers.add(answerByIDInDB);
                }
                if(totalAnswerCorrectInThere < 0){
                    totalAnswerCorrectInThere = 0.0f;
                }
                float percentMultipleChoiceQuiz = totalAnswerCorrectInThere / totalAnswerCorrectInList;
                correctTotalQuizzes += percentMultipleChoiceQuiz;
            }
            record.add(quizInDB, answers, contentPerforate);
        }
        float grade = (correctTotalQuizzes * 10) / totalQuizzes;
        grade = (float) (Math.round(grade * 100.0) / 100.0);

        record.setGrade(grade);


        Record savedRecord = recordRepository.save(record);
        return convertToResponse(savedRecord);
    }

    private RecordResponse convertToResponse(Record record){
        RecordResponse recordResponse = modelMapper.map(record, RecordResponse.class);
        recordResponse.setUserId(record.getUser().getId());
        recordResponse.setUsername(record.getUser().getUsername());
        recordResponse.setContestId(record.getContest().getId());
        recordResponse.setTitleContest(record.getContest().getTitle());

        return recordResponse;
    }

    @Override
    public List<RecordResponse> listAllRecord(Integer userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));

        List<Record> listRecords = recordRepository.findAllByUser(user);
        return listRecords.stream().map(this::convertToResponse).toList();
    }
}
