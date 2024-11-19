package vn.edu.ut.service;

import vn.edu.ut.payload.feedback.FeedbackRequest;
import vn.edu.ut.payload.feedback.FeedbackResponse;
import vn.edu.ut.payload.feedback.SendEmail;

import java.util.List;

public interface FeedbackService {
    FeedbackResponse save(FeedbackRequest feedbackRequest);
    FeedbackResponse get(Integer feedbackId);
    List<FeedbackResponse> listAll();
    String delete(Integer feedbackId);
    String sendMail(SendEmail sendEmail);
}
