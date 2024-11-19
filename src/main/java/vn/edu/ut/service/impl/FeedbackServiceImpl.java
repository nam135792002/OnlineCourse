package vn.edu.ut.service.impl;

import vn.edu.ut.entity.Feedback;
import vn.edu.ut.enums.FeedbackStatus;
import vn.edu.ut.exception.ResourceNotFoundException;
import vn.edu.ut.payload.feedback.FeedbackRequest;
import vn.edu.ut.payload.feedback.FeedbackResponse;
import vn.edu.ut.payload.feedback.SendEmail;
import vn.edu.ut.repository.FeedbackRepository;
import vn.edu.ut.service.FeedbackService;
import vn.edu.ut.utils.Utils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FeedbackServiceImpl implements FeedbackService {

    @Autowired private FeedbackRepository feedbackRepository;
    @Autowired private ModelMapper modelMapper;

    @Override
    public FeedbackResponse save(FeedbackRequest feedbackRequest) {
        Feedback feedback = modelMapper.map(feedbackRequest, Feedback.class);
        feedback.setStatus(FeedbackStatus.NEW);

        Feedback savedFeedback = feedbackRepository.save(feedback);

        return modelMapper.map(savedFeedback, FeedbackResponse.class);
    }

    @Override
    public FeedbackResponse get(Integer feedbackId) {
        Feedback feedbackInDB = feedbackRepository.findById(feedbackId)
                .orElseThrow(() -> new ResourceNotFoundException("Feedback", "id", feedbackId));
        return modelMapper.map(feedbackInDB, FeedbackResponse.class);
    }

    @Override
    public List<FeedbackResponse> listAll() {
        List<Feedback> listFeedback = feedbackRepository.findAll();
        return listFeedback.stream().map(feedback -> modelMapper.map(feedback, FeedbackResponse.class)).toList();
    }

    @Override
    public String delete(Integer feedbackId) {
        Feedback feedbackInDB = feedbackRepository.findById(feedbackId)
                .orElseThrow(() -> new ResourceNotFoundException("Feedback", "id", feedbackId));
        feedbackRepository.delete(feedbackInDB);
        return "SUCCESS";
    }

    @Override
    public String sendMail(SendEmail sendEmail) {
        Utils.sendEmailForFeedback(sendEmail);

        Feedback feedbackInDB = feedbackRepository.findById(sendEmail.getFeedbackId())
                .orElseThrow(() -> new ResourceNotFoundException("Feedback", "id", sendEmail.getFeedbackId()));

        feedbackInDB.setStatus(FeedbackStatus.SENT);
        feedbackRepository.save(feedbackInDB);
        return "Send email successfully!";
    }
}
