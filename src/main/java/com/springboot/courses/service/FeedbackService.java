package com.springboot.courses.service;

import com.springboot.courses.entity.Feedback;
import com.springboot.courses.payload.feedback.FeedbackRequest;
import com.springboot.courses.payload.feedback.FeedbackResponse;

import java.util.List;

public interface FeedbackService {
    FeedbackResponse save(FeedbackRequest feedbackRequest);
    FeedbackResponse get(Integer feedbackId);
    List<FeedbackResponse> listAll();
    String delete(Integer feedbackId);
}
