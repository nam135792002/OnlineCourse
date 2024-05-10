package com.springboot.courses.repository;

import com.springboot.courses.entity.Contest;
import com.springboot.courses.entity.Record;
import com.springboot.courses.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RecordRepository extends JpaRepository<Record, Integer> {
    Long countRecordByContestAndUser(Contest contest, User user);
    List<Record> findAllByUser(User user);
}
