package com.springboot.courses.controller;

import com.springboot.courses.payload.feedback.FeedbackRequest;
import com.springboot.courses.payload.feedback.SendEmail;
import com.springboot.courses.service.FeedbackService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/feedback")
@Tag(
        name = "Feedback Controller",
        description = "REST APIs related to Feedback Entity"
)
public class FeedbackController {

    @Autowired private FeedbackService feedbackService;

    @Operation(
            summary = "Send Feedback REST API",
            description = "This REST API is used to send feedback"
    )
    @ApiResponse(
            responseCode = "201",
            description = "Http Status 201 CREATED - Feedback sent successfully"
    )
    @PostMapping("/send")
    public ResponseEntity<?> sendFeedback(@RequestBody @Valid FeedbackRequest feedbackRequest){
        return new ResponseEntity<>(feedbackService.save(feedbackRequest), HttpStatus.CREATED);
    }

    @Operation(
            summary = "Get Feedback by ID REST API",
            description = "This REST API is used to get feedback by its ID"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Http Status 200 OK - Feedback retrieved successfully"
    )
    @ApiResponse(
            responseCode = "404",
            description = "Http Status 404 Not Found - Feedback not found"
    )
    @SecurityRequirement(
            name = "Bear Authentication"
    )
    @GetMapping("/get/{id}")
    public ResponseEntity<?> get(@PathVariable(value = "id") Integer feedbackId){
        return ResponseEntity.ok(feedbackService.get(feedbackId));
    }

    @Operation(
            summary = "List All Feedback REST API",
            description = "This REST API is used to list all feedback"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Http Status 200 OK - Feedback retrieved successfully"
    )
    @ApiResponse(
            responseCode = "204",
            description = "Http Status 204 No Content - No feedback found"
    )
    @SecurityRequirement(
            name = "Bear Authentication"
    )
    @GetMapping("/list-all")
    public ResponseEntity<?> listALl(){
        return ResponseEntity.ok(feedbackService.listAll());
    }

    @Operation(
            summary = "Delete Feedback REST API",
            description = "This REST API is used to delete feedback by its ID"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Http Status 200 OK - Feedback deleted successfully"
    )
    @ApiResponse(
            responseCode = "404",
            description = "Http Status 404 Not Found - Feedback not found"
    )
    @SecurityRequirement(
            name = "Bear Authentication"
    )
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable(value = "id") Integer feedbackId){
        return ResponseEntity.ok(feedbackService.delete(feedbackId));
    }

    @Operation(
            summary = "Send Email REST API",
            description = "This REST API is used to send an email"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Http Status 200 OK - Email sent successfully"
    )
    @SecurityRequirement(
            name = "Bear Authentication"
    )
    @PutMapping("/send-email")
    public ResponseEntity<?> sendEmail(@RequestBody @Valid SendEmail sendEmail){
        return ResponseEntity.ok(feedbackService.sendMail(sendEmail));
    }
}
