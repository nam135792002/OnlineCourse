package com.springboot.courses.controller;

import com.springboot.courses.payload.record.RecordRequest;
import com.springboot.courses.payload.record.RecordResponse;
import com.springboot.courses.service.RecordService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/record")
public class RecordController {

    @Autowired private RecordService recordService;

    @PostMapping("/save")
    public ResponseEntity<?> save(@RequestBody @Valid RecordRequest recordRequest){
        return new ResponseEntity<>(recordService.saveRecord(recordRequest), HttpStatus.CREATED);
    }

    @GetMapping("/list-all/user")
    public ResponseEntity<?> listAllByUser(@RequestParam(value = "id") Integer userId){
        List<RecordResponse> listRecords = recordService.listAllRecord(userId);
        if(listRecords.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(listRecords);
    }

}
