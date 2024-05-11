package com.springboot.courses.service.impl;

import com.springboot.courses.entity.*;
import com.springboot.courses.entity.Record;
import com.springboot.courses.exception.ResourceNotFoundException;
import com.springboot.courses.payload.quiz.AnswerLearningRequest;
import com.springboot.courses.payload.quiz.QuizLearningRequest;
import com.springboot.courses.payload.quiz.QuizReturnInRecord;
import com.springboot.courses.payload.record.RecordRequest;
import com.springboot.courses.payload.record.RecordResponse;
import com.springboot.courses.payload.record.RecordReturnInRank;
import com.springboot.courses.payload.record.RecordReturnToReview;
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
    @Autowired private RecordDetailRepository recordDetailRepository;

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
        RecordResponse response =  convertToResponse(savedRecord);
        response.setTotalQuizzes(totalQuizzes);
        response.setTotalQuizIsCorrect(correctTotalQuizzes);
        return response;
    }

    private RecordResponse convertToResponse(Record record){
        RecordResponse recordResponse = modelMapper.map(record, RecordResponse.class);
        recordResponse.setUserId(record.getUser().getId());
        recordResponse.setUsername(record.getUser().getUsername());
        recordResponse.setContestId(record.getContest().getId());
        recordResponse.setTitleContest(record.getContest().getTitle());
        recordResponse.setTotalQuizzes(record.getContest().getListQuizzes().size());

        return recordResponse;
    }

    @Override
    public List<RecordResponse> listAllRecord(Integer userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));

        List<Record> listRecords = recordRepository.findAllByUser(user);
        return listRecords.stream().map(this::convertToResponse).toList();
    }

    @Override
    public List<RecordResponse> listAllRecordByUserAndContest(Integer userId, Integer contestId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));

        Contest contest = contestRepository.findById(contestId)
                .orElseThrow(() -> new ResourceNotFoundException("Contest", "id", contestId));

        List<Record> listRecords = recordRepository.findAllByUserAndContest(user, contest);
        return listRecords.stream().map(this::convertToResponse).toList();
    }

    @Override
    public RecordReturnToReview review(Integer recordId) {
        Record recordInDB = recordRepository.findById(recordId)
                .orElseThrow(() -> new ResourceNotFoundException("Record", "id", recordId));

        RecordReturnToReview reviewRecord = modelMapper.map(recordInDB, RecordReturnToReview.class);
        reviewRecord.setTitleContest(recordInDB.getContest().getTitle());

        List<QuizReturnInRecord> listQuizzesInRecord = new ArrayList<>();

        float totalQuizzes = recordInDB.getContest().getListQuizzes().size();
        float correctTotalQuizzes = 0;
        int i = 0;

        for (Quiz quiz : recordInDB.getContest().getListQuizzes()){
            Quiz quizInDB = quizRepository.findById(quiz.getId())
                    .orElseThrow(() -> new ResourceNotFoundException("Quiz", "id", quiz.getId()));

            QuizReturnInRecord quizInRecord = modelMapper.map(quizInDB, QuizReturnInRecord.class);
            quizInRecord.setOrder(++i);

            RecordDetail recordDetail = recordDetailRepository.findRecordDetailByRecordAndQuiz(recordInDB, quizInDB);

            if(quizInDB.getQuizType().toString().equals("ONE_CHOICE")){
                for (Answer answerInSet : recordDetail.getAnswer()){
                    quizInRecord.getAnswerList().stream()
                            .filter(quizInStream -> quizInStream.getId().equals(answerInSet.getId()))
                            .forEach(quizInStream -> quizInStream.setAnswerOfCustomer(true));

                    Answer answer = answerQuizRepository.checkAnswerInCorrect(answerInSet.getId());
                    if(answer != null) {
                        ++correctTotalQuizzes;
                        quizInRecord.setCorrectForAnswer(true);
                    }
                }
            }else if(quizInDB.getQuizType().toString().equals("PERFORATE")){
                List<Answer> listAnswers = answerQuizRepository.listAllAnswerIsCorrect(quizInDB.getId());
                String contentAnswer = recordDetail.getContentPerforate();
                for (Answer answer : listAnswers){
                    if(contentAnswer.equalsIgnoreCase(answer.getContent())){
                        quizInRecord.setCorrectForAnswer(true);
                        ++correctTotalQuizzes;
                    }

                    quizInRecord.getAnswerList().stream()
                            .filter(quizInStream -> quizInStream.getId().equals(answer.getId()))
                            .forEach(quizInStream -> quizInStream.setContentPerforateOfCustomer(contentAnswer));
                }
            }else{
                List<Answer> listAnswers = answerQuizRepository.listAllAnswerIsCorrect(quizInDB.getId());
                float totalAnswerCorrectInList = listAnswers.size();
                float totalAnswerCorrectInThere = 0.0f;
                for (Answer answerInSet : recordDetail.getAnswer()){
                    Integer answerId = answerInSet.getId();
                    Answer answer = answerQuizRepository.checkAnswerInCorrect(answerId);

                    quizInRecord.getAnswerList().stream()
                            .filter(quizInStream -> quizInStream.getId().equals(answerInSet.getId()))
                            .forEach(quizInStream -> quizInStream.setAnswerOfCustomer(true));

                    if(answer != null){
                        ++totalAnswerCorrectInThere;
                    }else{
                        --totalAnswerCorrectInThere;
                    }

                }
                if(totalAnswerCorrectInThere < 0){
                    totalAnswerCorrectInThere = 0.0f;
                }

                if(totalAnswerCorrectInThere == totalAnswerCorrectInList){
                    quizInRecord.setCorrectForAnswer(true);
                }

                float percentMultipleChoiceQuiz = totalAnswerCorrectInThere / totalAnswerCorrectInList;
                correctTotalQuizzes += percentMultipleChoiceQuiz;
            }

            listQuizzesInRecord.add(quizInRecord);
        }
        float grade = (correctTotalQuizzes * 10) / totalQuizzes;
        grade = (float) (Math.round(grade * 100.0) / 100.0);

        reviewRecord.setGrade(grade);
        reviewRecord.setTotalQuizzes(totalQuizzes);
        reviewRecord.setTotalQuizIsCorrect(correctTotalQuizzes);
        reviewRecord.setListQuizzes(listQuizzesInRecord);

        return reviewRecord;
    }

    @Override
    public List<RecordReturnInRank> ranking(Integer contestId) {
        Contest contest = contestRepository.findById(contestId)
                .orElseThrow(() -> new ResourceNotFoundException("Contest", "id", contestId));

        List<Record> listRecords = recordRepository.findAllByContest(contest);

        Map<Integer, Record> recordMap = new HashMap<>();
        for(Record record : listRecords){
            Integer userId = record.getUser().getId();
            if(!recordMap.containsKey(userId)){
                recordMap.put(userId, record);
            }else{
                Record recordFromMap = recordMap.get(userId);
                if(record.getJoinedAt().toInstant().isBefore(recordFromMap.getJoinedAt().toInstant())){
                    recordMap.put(userId, record);
                }
            }
        }

        List<Record> filteredRecords = new ArrayList<>(recordMap.values());
        filteredRecords.sort(Comparator.comparing(Record::getGrade).reversed()
                .thenComparing(Record::getPeriod)
                .thenComparing(Record::getJoinedAt));

        return filteredRecords.stream().map(this::convertToRank).toList();
    }

    private RecordReturnInRank convertToRank(Record record){
        RecordReturnInRank rank = modelMapper.map(record, RecordReturnInRank.class);
        rank.setUsername(record.getUser().getUsername());

        return rank;
    }
}
