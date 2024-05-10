package com.springboot.courses.controller;

import com.springboot.courses.payload.contest.ContestRequest;
import com.springboot.courses.payload.contest.ContestResponse;
import com.springboot.courses.service.ContestService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @PostMapping("/switch-enabled")
    public ResponseEntity<?> switchEnabledOfContest(@RequestParam(value = "id") Integer contestId,
                                                    @RequestParam(value = "enabled") boolean enabled){
        return ResponseEntity.ok(contestService.switchEnabled(contestId, enabled));
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<?> getInAdministration(@PathVariable(value = "id") Integer contestId){
        return ResponseEntity.ok(contestService.getInAdministration(contestId));
    }

    //api này dùng cho admin vs học viên.
    @GetMapping("/list-all")
    public ResponseEntity<?> listAll(){
        List<ContestResponse> listContest = contestService.listAll();
        if(listContest.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(listContest);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> update(@PathVariable(value = "id") Integer contestId,
                                    @RequestBody @Valid ContestRequest contestRequest){
        return ResponseEntity.ok(contestService.updateContest(contestId, contestRequest));
    }

    @GetMapping("/search")
    public ResponseEntity<?> search(@RequestParam(value = "keyword") String keyword){
        List<ContestResponse> listContest = contestService.search(keyword);
        if(listContest.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(listContest);
    }

    @GetMapping("/join/{contest_id}/user/{user_id}")
    public ResponseEntity<?> join(@PathVariable(value = "contest_id") Integer contestId,
                                  @PathVariable(value = "user_id") Integer userId){
        return ResponseEntity.ok(contestService.joinTest(contestId, userId));
    }
}
