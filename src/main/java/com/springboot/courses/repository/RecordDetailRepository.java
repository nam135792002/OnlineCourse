package com.springboot.courses.repository;

import com.springboot.courses.entity.Quiz;
import com.springboot.courses.entity.Record;
import com.springboot.courses.entity.RecordDetail;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecordDetailRepository extends JpaRepository<RecordDetail, Integer> {
    RecordDetail findRecordDetailByRecordAndQuiz(Record record, Quiz quiz);
}
