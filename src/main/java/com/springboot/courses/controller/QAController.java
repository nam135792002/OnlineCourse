package com.springboot.courses.controller;

import com.springboot.courses.payload.qa.QARequest;
import com.springboot.courses.service.QAService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/qa")
public class QAController {

    @Autowired private QAService qaService;

    @PostMapping("/create")
    public ResponseEntity<?> create(@RequestBody @Valid QARequest qaRequest){
        return new ResponseEntity<>(qaService.createQA(qaRequest), HttpStatus.CREATED);
    }

    @GetMapping("/get-all")
    public ResponseEntity<?> listAll(@RequestParam(value = "lesson") Integer lessonId){
        return ResponseEntity.ok(qaService.listAll(lessonId));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> update(@PathVariable(value = "id") Integer qaId,
                                    @RequestParam(value = "content") String content){
        return ResponseEntity.ok(qaService.updateQA(qaId, content));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable(value = "id") Integer qaId){
        return ResponseEntity.ok(qaService.deleteQA(qaId));
    }
}
