package com.springboot.courses.service;

import com.springboot.courses.payload.record.RecordRequest;
import com.springboot.courses.payload.record.RecordResponse;
import com.springboot.courses.payload.record.RecordReturnInRank;
import com.springboot.courses.payload.record.RecordReturnToReview;

import java.util.List;

public interface RecordService{
    RecordResponse saveRecord(RecordRequest recordRequest);
    List<RecordResponse> listAllRecord(Integer userId);
    List<RecordResponse> listAllRecordByUserAndContest(Integer userId, Integer contestId);
    RecordReturnToReview review(Integer recordId);
    List<RecordReturnInRank> ranking(Integer contestId);
}
