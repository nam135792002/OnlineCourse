package com.springboot.courses.repository;

import com.springboot.courses.entity.Contest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ContestRepository extends JpaRepository<Contest, Integer> {
    @Query("update Contest c set c.enabled = ?2 where c.id = ?1")
    @Modifying
    void switchEnabled(Integer contestId, boolean enabled);
    boolean existsContestByTitle(String title);
    Contest findContestByTitle(String title);
    @Query("select c from Contest c where c.title like %?1% and c.enabled = true")
    List<Contest> search(String keyword);

}
