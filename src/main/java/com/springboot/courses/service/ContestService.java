package com.springboot.courses.service;

import com.springboot.courses.payload.contest.ContestRequest;
import com.springboot.courses.payload.contest.ContestResponse;

public interface ContestService {
    ContestResponse saveContest(ContestRequest contestRequest);
    String deleteContest(Integer contestId);
}
