package vn.edu.ut.service;

import vn.edu.ut.payload.qa.QARequest;
import vn.edu.ut.payload.qa.QAResponse;

import java.util.List;

public interface QAService {
    QAResponse createQA(QARequest qaRequest);
    List<QAResponse> listAll(Integer lessonId);
    QAResponse updateQA(Integer qaId, String content);
    String deleteQA(Integer qaId);
}
