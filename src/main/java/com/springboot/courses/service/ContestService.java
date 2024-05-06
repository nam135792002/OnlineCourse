package com.springboot.courses.service;

import com.springboot.courses.payload.contest.ContestRequest;
import com.springboot.courses.payload.contest.ContestResponse;

import java.util.List;

public interface ContestService {
    ContestResponse saveContest(ContestRequest contestRequest);
    ContestResponse getInAdministration(Integer contestId);
    ContestResponse updateContest(Integer contestId, ContestRequest contestRequest);
    List<ContestResponse> listAll();
    String deleteContest(Integer contestId);
    String switchEnabled(Integer contestId, boolean enabled);
}
