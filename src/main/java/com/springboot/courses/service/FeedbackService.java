package com.springboot.courses.service;

import com.springboot.courses.payload.feedback.FeedbackRequest;
import com.springboot.courses.payload.feedback.FeedbackResponse;
import com.springboot.courses.payload.feedback.SendEmail;

import java.util.List;

public interface FeedbackService {
    FeedbackResponse save(FeedbackRequest feedbackRequest);
    FeedbackResponse get(Integer feedbackId);
    List<FeedbackResponse> listAll();
    String delete(Integer feedbackId);
    String sendMail(SendEmail sendEmail);
}
