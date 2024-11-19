package vn.edu.ut.repository;

import vn.edu.ut.entity.Contest;
import vn.edu.ut.entity.Record;
import vn.edu.ut.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;

public interface RecordRepository extends JpaRepository<Record, Integer> {
    List<Record> findAllByUser(User user);
    List<Record> findAllByUserAndContest(User user, Contest contest);
    int countAllByContest(Contest contest);
    List<Record> findAllByContest(Contest contest);
    void deleteAllByContest(Contest contest);
    @Query("select new Record(r.contest.title, r.grade) from Record r where r.joinedAt between ?1 and ?2")
    List<Record> findWithRecordOfContestAndTimeBetween(Date startTime, Date endTime);
}
