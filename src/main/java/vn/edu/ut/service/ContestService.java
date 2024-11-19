package vn.edu.ut.service;

import vn.edu.ut.payload.contest.ContestRequest;
import vn.edu.ut.payload.contest.ContestResponse;
import vn.edu.ut.payload.contest.ContestReturnInTest;

import java.util.List;

public interface ContestService {
    ContestResponse saveContest(ContestRequest contestRequest);
    ContestResponse getInAdministration(Integer contestId);
    ContestResponse updateContest(Integer contestId, ContestRequest contestRequest);
    List<ContestResponse> listAll();
    String deleteContest(Integer contestId);
    String switchEnabled(Integer contestId, boolean enabled);
    List<ContestResponse> search(String keyword);
    ContestReturnInTest joinTest(Integer contestId);
    String resetRanking(Integer contestId);
}
