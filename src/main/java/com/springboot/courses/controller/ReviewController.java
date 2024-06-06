package com.springboot.courses.controller;

import com.springboot.courses.payload.review.ListReviewResponse;
import com.springboot.courses.payload.review.ReviewRequest;
import com.springboot.courses.service.ReviewService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/reviews")
@Tag(
        name = "Review Controller",
        description = "APIs for managing reviews"
)
public class ReviewController {

    @Autowired private ReviewService reviewService;

    @Operation(
            summary = "Create a new review",
            description = "Create a new review for a course"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Review created successfully"
    )
    @SecurityRequirement(
            name = "Bear Authentication"
    )
    @PostMapping("/create")
    public ResponseEntity<?> create(@RequestBody @Valid ReviewRequest reviewRequest){
        return ResponseEntity.ok(reviewService.createReview(reviewRequest));
    }

    @Operation(
            summary = "List all reviews",
            description = "Get a list of all reviews"
    )
    @ApiResponse(
            responseCode = "200",
            description = "List of reviews retrieved successfully"
    )
    @ApiResponse(
            responseCode = "204",
            description = "List of reviews retrieved not contents"
    )
    @SecurityRequirement(
            name = "Bear Authentication"
    )
    @GetMapping("/get-all")
    public ResponseEntity<?> list(){
        ListReviewResponse listReviewResponse = reviewService.listAll();
        if(listReviewResponse.getListResponses().isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(listReviewResponse);
    }

    @Operation(
            summary = "Update a review",
            description = "Update an existing review by its ID"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Review updated successfully"
    )
    @SecurityRequirement(
            name = "Bear Authentication"
    )
    @PutMapping("/update/{id}")
    public ResponseEntity<?> update(@PathVariable(value = "id") Integer reviewId,
                                    @RequestParam(value = "comment") String comment){
        return ResponseEntity.ok(reviewService.updateReview(reviewId, comment));
    }

    @Operation(
            summary = "Delete a review",
            description = "Delete an existing review by its ID"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Review deleted successfully"
    )
    @SecurityRequirement(
            name = "Bear Authentication"
    )
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable(value = "id") Integer reviewId){
        return ResponseEntity.ok(reviewService.deleteReview(reviewId));
    }

    @Operation(
            summary = "List reviews by course",
            description = "Get a list of reviews for a specific course by its ID"
    )
    @ApiResponse(
            responseCode = "200",
            description = "List of reviews for the course retrieved successfully"
    )
    @ApiResponse(
            responseCode = "204",
            description = "List of reviews for the course retrieved not content"
    )
    @GetMapping("/get-all/course/{id}")
    public ResponseEntity<?> listByCourse(@PathVariable(value = "id") Integer courseId){
        ListReviewResponse listReviewResponse = reviewService.listAllByCourse(courseId);
        if(listReviewResponse.getListResponses().isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(listReviewResponse);
    }

    @Operation(
            summary = "Check if user reviewed a course",
            description = "Check if a user has reviewed a specific course"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Review check performed successfully"
    )
    @SecurityRequirement(
            name = "Bear Authentication"
    )
    @GetMapping("/check-reviewed/user/{user_id}/course/{course_id}")
    public ResponseEntity<?> checkReviewed(@PathVariable(value = "user_id") Integer userId,
                                           @PathVariable(value = "course_id") Integer courseId){
        return ResponseEntity.ok(reviewService.checkCustomerToReviewed(userId, courseId));
    }
}
