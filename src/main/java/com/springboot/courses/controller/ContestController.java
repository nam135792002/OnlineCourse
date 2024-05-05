package com.springboot.courses.controller;

import com.springboot.courses.payload.contest.ContestRequest;
import com.springboot.courses.service.ContestService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/contest")
public class ContestController {

    @Autowired private ContestService contestService;

    @PostMapping("/create")
    public ResponseEntity<?> save(@RequestBody @Valid ContestRequest contestRequest){
        return new ResponseEntity<>(contestService.saveContest(contestRequest), HttpStatus.CREATED);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable(value = "id") Integer contestId){
        return ResponseEntity.ok(contestService.deleteContest(contestId));
    }
}
