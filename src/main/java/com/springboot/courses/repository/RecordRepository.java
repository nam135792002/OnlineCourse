package com.springboot.courses.repository;

import com.springboot.courses.entity.Contest;
import com.springboot.courses.entity.Record;
import com.springboot.courses.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RecordRepository extends JpaRepository<Record, Integer> {
    List<Record> findAllByUser(User user);
    List<Record> findAllByUserAndContest(User user, Contest contest);
    int countAllByContest(Contest contest);
    List<Record> findAllByContest(Contest contest);
    void deleteAllByContest(Contest contest);
}
