package com.springboot.courses.service.impl;

import com.springboot.courses.entity.Lesson;
import com.springboot.courses.entity.QA;
import com.springboot.courses.entity.User;
import com.springboot.courses.exception.ResourceNotFoundException;
import com.springboot.courses.payload.qa.QARequest;
import com.springboot.courses.payload.qa.QAResponse;
import com.springboot.courses.repository.LessonRepository;
import com.springboot.courses.repository.QARepository;
import com.springboot.courses.repository.UserRepository;
import com.springboot.courses.service.QAService;
import com.springboot.courses.utils.Utils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class QAServiceImpl implements QAService {

    @Autowired private QARepository qaRepository;
    @Autowired private LessonRepository lessonRepository;
    @Autowired private UserRepository userRepository;
    @Autowired private ModelMapper modelMapper;

    @Override
    public QAResponse createQA(QARequest qaRequest) {
        User user = userRepository.findById(qaRequest.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", qaRequest.getUserId()));

        Lesson lesson = lessonRepository.findById(qaRequest.getLessonId())
                .orElseThrow(() -> new ResourceNotFoundException("Lesson", "id", qaRequest.getLessonId()));

        QA qa = new QA();
        if(qaRequest.getParentId() != null){
            QA qaInDB = qaRepository.findById(qaRequest.getParentId())
                    .orElseThrow(() -> new ResourceNotFoundException("QA", "id", qaRequest.getParentId()));
            qa.setParent(qaInDB);
        }
        qa.setContent(qaRequest.getContent());
        qa.setLesson(lesson);
        qa.setUser(user);
        qa.setCreatedAt(new Date());

        QA savedQA = qaRepository.save(qa);
        return convertToResponse(savedQA);
    }

    @Override
    public List<QAResponse> listAll(Integer lessonId) {
        Lesson lesson = lessonRepository.findById(lessonId)
                .orElseThrow(() -> new ResourceNotFoundException("Lesson", "id", lessonId));
        List<QA> listQAs = qaRepository.findAllByLesson(lesson.getId());

        return listQAs.stream()
                .sorted(Comparator.comparing(QA::getCreatedAt).reversed())
                .map(this::convertToResponse)
                .toList();
    }

    @Override
    public QAResponse updateQA(Integer qaId, String content) {
        QA qaInDB = qaRepository.findById(qaId)
                .orElseThrow(() -> new ResourceNotFoundException("QA", "id", qaId));
        qaInDB.setContent(content);
        return convertToResponse(qaRepository.save(qaInDB));
    }

    @Override
    public String deleteQA(Integer qaId) {
        QA qaInDB = qaRepository.findById(qaId)
                .orElseThrow(() -> new ResourceNotFoundException("QA", "id", qaId));
        qaRepository.delete(qaInDB);
        return "Xóa QA thành công!";
    }

    private QAResponse convertToResponse(QA qa){
        QAResponse response = modelMapper.map(qa, QAResponse.class);
        Instant now = Instant.now();
        response.setLessonId(qa.getLesson().getId());
        response.setUserId(qa.getUser().getId());
        response.setUsername(qa.getUser().getUsername());
        response.setPhotoUser(qa.getUser().getPhoto());
        response.setCreatedAtFormatted(Utils.formatDuration(Duration.between(qa.getCreatedAt().toInstant(), now)));
        if(qa.getParent() != null){
            response.setParentId(qa.getParent().getId());
        }

        List<QAResponse> childrenResponse = qa.getChildren().stream()
                .sorted(Comparator.comparing(QA::getCreatedAt).reversed())
                .map(this::convertToResponse)
                .toList();
        response.setChildren(childrenResponse);
        return response;
    }
}
