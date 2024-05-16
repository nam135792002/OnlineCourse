package com.springboot.courses.controller;

import com.springboot.courses.payload.feedback.FeedbackRequest;
import com.springboot.courses.service.FeedbackService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/feedback")
public class FeedbackController {

    @Autowired private FeedbackService feedbackService;

    @PostMapping("/send")
    public ResponseEntity<?> sendFeedback(@RequestBody @Valid FeedbackRequest feedbackRequest){
        return new ResponseEntity<>(feedbackService.save(feedbackRequest), HttpStatus.CREATED);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<?> get(@PathVariable(value = "id") Integer feedbackId){
        return ResponseEntity.ok(feedbackService.get(feedbackId));
    }

    @GetMapping("/list-all")
    public ResponseEntity<?> listALl(){
        return ResponseEntity.ok(feedbackService.listAll());
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable(value = "id") Integer feedbackId){
        return ResponseEntity.ok(feedbackService.delete(feedbackId));
    }
}
