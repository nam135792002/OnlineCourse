package com.springboot.courses.service;

import com.springboot.courses.payload.qa.QARequest;
import com.springboot.courses.payload.qa.QAResponse;

import java.util.List;

public interface QAService {
    QAResponse createQA(QARequest qaRequest);
    List<QAResponse> listAll(Integer lessonId);
    QAResponse updateQA(Integer qaId, String content);
    String deleteQA(Integer qaId);
}
