package com.springboot.courses.controller;

import com.springboot.courses.payload.contest.ContestRequest;
import com.springboot.courses.payload.contest.ContestResponse;
import com.springboot.courses.payload.record.RecordReturnInRank;
import com.springboot.courses.service.ContestService;
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
@RequestMapping("/api/contest")
@Tag(
        name = "Contest Controller",
        description = "REST APIs related to Contest Entity"
)
public class ContestController {

    @Autowired private ContestService contestService;
    @Autowired private RecordService recordService;

    @Operation(
            summary = "Create Contest REST API",
            description = "This REST API is used to create a new contest"
    )
    @ApiResponse(
            responseCode = "201",
            description = "Http Status 201 CREATED - Contest created successfully"
    )
    @SecurityRequirement(
            name = "Bear Authentication"
    )
    @PostMapping("/create")
    public ResponseEntity<?> save(@RequestBody @Valid ContestRequest contestRequest){
        return new ResponseEntity<>(contestService.saveContest(contestRequest), HttpStatus.CREATED);
    }

    @Operation(
            summary = "Delete Contest REST API",
            description = "This REST API is used to delete a contest by its ID"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Http Status 200 OK - Contest deleted successfully"
    )
    @SecurityRequirement(
            name = "Bear Authentication"
    )
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable(value = "id") Integer contestId){
        return ResponseEntity.ok(contestService.deleteContest(contestId));
    }

    @Operation(
            summary = "Switch Enabled Status of Contest REST API",
            description = "This REST API is used to enable or disable a contest"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Http Status 200 OK - Contest status updated successfully"
    )
    @SecurityRequirement(
            name = "Bear Authentication"
    )
    @PostMapping("/switch-enabled")
    public ResponseEntity<?> switchEnabledOfContest(@RequestParam(value = "id") Integer contestId,
                                                    @RequestParam(value = "enabled") boolean enabled){
        return ResponseEntity.ok(contestService.switchEnabled(contestId, enabled));
    }

    @Operation(
            summary = "Get Contest by ID REST API",
            description = "This REST API is used to get a contest by its ID"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Http Status 200 OK - Contest retrieved successfully"
    )
    @ApiResponse(
            responseCode = "404",
            description = "Http Status 404 Not Found - Contest not found"
    )
    @SecurityRequirement(
            name = "Bear Authentication"
    )
    @GetMapping("/get/{id}")
    public ResponseEntity<?> getInAdministration(@PathVariable(value = "id") Integer contestId){
        return ResponseEntity.ok(contestService.getInAdministration(contestId));
    }

    @Operation(
            summary = "List All Contests REST API",
            description = "This REST API is used to list all contests"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Http Status 200 OK - Contests retrieved successfully"
    )
    @ApiResponse(
            responseCode = "204",
            description = "Http Status 204 No Content - No contests found"
    )
    //api này dùng cho admin vs học viên.
    @GetMapping("/list-all")
    public ResponseEntity<?> listAll(){
        List<ContestResponse> listContest = contestService.listAll();
        if(listContest.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(listContest);
    }

    @Operation(
            summary = "Update Contest REST API",
            description = "This REST API is used to update an existing contest by its ID"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Http Status 200 OK - Contest updated successfully"
    )
    @ApiResponse(
            responseCode = "404",
            description = "Http Status 404 Not Found - Contest not found"
    )
    @SecurityRequirement(
            name = "Bear Authentication"
    )
    @PutMapping("/update/{id}")
    public ResponseEntity<?> update(@PathVariable(value = "id") Integer contestId,
                                    @RequestBody @Valid ContestRequest contestRequest){
        return ResponseEntity.ok(contestService.updateContest(contestId, contestRequest));
    }

    @Operation(
            summary = "Search Contests REST API",
            description = "This REST API is used to search for contests by a keyword"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Http Status 200 OK - Contests retrieved successfully"
    )
    @ApiResponse(
            responseCode = "204",
            description = "Http Status 204 No Content - No contests found"
    )
    @GetMapping("/search")
    public ResponseEntity<?> search(@RequestParam(value = "keyword") String keyword){
        List<ContestResponse> listContest = contestService.search(keyword);
        if(listContest.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(listContest);
    }

    @Operation(
            summary = "Join Contest REST API",
            description = "This REST API is used to join a contest by its ID"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Http Status 200 OK - Joined contest successfully"
    )
    @ApiResponse(
            responseCode = "404",
            description = "Http Status 404 Not Found - Contest not found"
    )
    @SecurityRequirement(
            name = "Bear Authentication"
    )
    @GetMapping("/join/{contest_id}")
    public ResponseEntity<?> join(@PathVariable(value = "contest_id") Integer contestId){
        return ResponseEntity.ok(contestService.joinTest(contestId));
    }

    @Operation(
            summary = "Get Contest Ranking REST API",
            description = "This REST API is used to get the ranking of participants in a contest by its ID"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Http Status 200 OK - Ranking retrieved successfully"
    )
    @ApiResponse(
            responseCode = "204",
            description = "Http Status 204 No Content - No rankings found"
    )
    @GetMapping("/ranking/contest/{id}")
    public ResponseEntity<?> rank(@PathVariable(value = "id") Integer contestId){
        List<RecordReturnInRank> listRanks = recordService.ranking(contestId);
        if(listRanks.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(listRanks);
    }

    @Operation(
            summary = "Reset Contest Ranking REST API",
            description = "This REST API is used to reset the ranking of a contest by its ID"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Http Status 200 OK - Ranking reset successfully"
    )
    @SecurityRequirement(
            name = "Bear Authentication"
    )
    @DeleteMapping("/ranking/reset/{id}")
    public ResponseEntity<?> resetRanking(@PathVariable(value = "id") Integer contestId){
        return ResponseEntity.ok(contestService.resetRanking(contestId));
    }
}
