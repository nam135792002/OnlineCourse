package com.springboot.courses.controller;

import com.springboot.courses.payload.record.RecordRequest;
import com.springboot.courses.payload.record.RecordResponse;
import com.springboot.courses.service.RecordService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/record")
@Tag(
        name = "Record Controller",
        description = "APIs for managing contest records"
)
public class RecordController {

    @Autowired private RecordService recordService;

    @Operation(
            summary = "Save a new record",
            description = "Save the details of a new contest record of trainee"
    )
    @ApiResponse(
            responseCode = "201",
            description = "Record created successfully"
    )
    @SecurityRequirement(
            name = "Bear Authentication"
    )
    @PostMapping("/save")
    public ResponseEntity<?> save(@RequestBody @Valid RecordRequest recordRequest){
        return new ResponseEntity<>(recordService.saveRecord(recordRequest), HttpStatus.CREATED);
    }

    @Operation(
            summary = "List all records by trainee",
            description = "List all contest records for a specific trainee"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Records retrieved successfully"
    )
    @ApiResponse(
            responseCode = "204",
            description = "List all of contest is not content by trainee"
    )
    @SecurityRequirement(
            name = "Bear Authentication"
    )
    @GetMapping("/list-all/user")
    public ResponseEntity<?> listAllByUser(@RequestParam(value = "id") Integer userId){
        List<RecordResponse> listRecords = recordService.listAllRecord(userId);
        if(listRecords.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(listRecords);
    }

    @Operation(
            summary = "List all records by trainee and contest",
            description = "List all contest records for a specific trainee and contest")
    @ApiResponse(
            responseCode = "200",
            description = "Records retrieved successfully"
    )
    @ApiResponse(
            responseCode = "204",
            description = "Records retrieved not content"
    )
    @SecurityRequirement(
            name = "Bear Authentication"
    )
    @GetMapping("/list-all/user-contest")
    public ResponseEntity<?> listAllByUserAndContest(@RequestParam(value = "user") Integer userId,
                                                     @RequestParam(value = "contest") Integer contestId){
        List<RecordResponse> listRecords = recordService.listAllRecordByUserAndContest(userId, contestId);
        if(listRecords.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(listRecords);
    }

    @Operation(
            summary = "Review a specific record",
            description = "Review the details of a specific exam record"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Record reviewed successfully"
    )
    @SecurityRequirement(
            name = "Bear Authentication"
    )
    @GetMapping("/review/{id}")
    public ResponseEntity<?> review(@PathVariable(value = "id") Integer recordId){
        return ResponseEntity.ok(recordService.review(recordId));
    }
}
