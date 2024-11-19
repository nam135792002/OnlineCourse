package vn.edu.ut.service;

import vn.edu.ut.payload.record.RecordRequest;
import vn.edu.ut.payload.record.RecordResponse;
import vn.edu.ut.payload.record.RecordReturnInRank;
import vn.edu.ut.payload.record.RecordReturnToReview;

import java.util.List;

public interface RecordService{
    RecordResponse saveRecord(RecordRequest recordRequest);
    List<RecordResponse> listAllRecord(Integer userId);
    List<RecordResponse> listAllRecordByUserAndContest(Integer userId, Integer contestId);
    RecordReturnToReview review(Integer recordId);
    List<RecordReturnInRank> ranking(Integer contestId);
}
