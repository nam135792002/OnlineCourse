package com.springboot.courses.controller;

import com.springboot.courses.payload.review.ListReviewResponse;
import com.springboot.courses.payload.review.ReviewRequest;
import com.springboot.courses.service.ReviewService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/reviews")
public class ReviewController {

    @Autowired private ReviewService reviewService;

    @PostMapping("/create")
    public ResponseEntity<?> create(@RequestBody @Valid ReviewRequest reviewRequest){
        return ResponseEntity.ok(reviewService.createReview(reviewRequest));
    }

    @GetMapping("/get-all")
    public ResponseEntity<?> list(){
        ListReviewResponse listReviewResponse = reviewService.listAll();
        if(listReviewResponse.getListResponses().isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(listReviewResponse);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> update(@PathVariable(value = "id") Integer reviewId,
                                    @RequestParam(value = "comment") String comment){
        return ResponseEntity.ok(reviewService.updateReview(reviewId, comment));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable(value = "id") Integer reviewId){
        return ResponseEntity.ok(reviewService.deleteReview(reviewId));
    }
}
