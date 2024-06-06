package com.springboot.courses.controller;

import com.springboot.courses.payload.qa.QARequest;
import com.springboot.courses.service.QAService;
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
@RequestMapping("/api/qa")
@Tag(
        name = "QA Controller",
        description = "REST APIs related to Question and Answer operations"
)
public class QAController {

    @Autowired private QAService qaService;

    @Operation(
            summary = "Create QA",
            description = "Create a new Question and Answer entry"
    )
    @ApiResponse(
            responseCode = "201",
            description = "Question and Answer created successfully"
    )
    @SecurityRequirement(
            name = "Bear Authentication"
    )
    @PostMapping("/create")
    public ResponseEntity<?> create(@RequestBody @Valid QARequest qaRequest){
        return new ResponseEntity<>(qaService.createQA(qaRequest), HttpStatus.CREATED);
    }

    @Operation(
            summary = "List all QAs",
            description = "List all Questions and Answers for a specific lesson"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Questions and Answers retrieved successfully"
    )
    @SecurityRequirement(
            name = "Bear Authentication"
    )
    @GetMapping("/get-all")
    public ResponseEntity<?> listAll(@RequestParam(value = "lesson") Integer lessonId){
        return ResponseEntity.ok(qaService.listAll(lessonId));
    }

    @Operation(
            summary = "Update QA",
            description = "Update the content of a specific Question and Answer entry"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Question and Answer updated successfully"
    )
    @SecurityRequirement(
            name = "Bear Authentication"
    )
    @PutMapping("/update/{id}")
    public ResponseEntity<?> update(@PathVariable(value = "id") Integer qaId,
                                    @RequestParam(value = "content") String content){
        return ResponseEntity.ok(qaService.updateQA(qaId, content));
    }

    @Operation(
            summary = "Delete QA",
            description = "Delete a specific Question and Answer entry"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Question and Answer deleted successfully"
    )
    @SecurityRequirement(
            name = "Bear Authentication"
    )
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable(value = "id") Integer qaId){
        return ResponseEntity.ok(qaService.deleteQA(qaId));
    }
}
