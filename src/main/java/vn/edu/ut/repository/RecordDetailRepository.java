package vn.edu.ut.repository;

import vn.edu.ut.entity.Quiz;
import vn.edu.ut.entity.Record;
import vn.edu.ut.entity.RecordDetail;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecordDetailRepository extends JpaRepository<RecordDetail, Integer> {
    RecordDetail findRecordDetailByRecordAndQuiz(Record record, Quiz quiz);
}
