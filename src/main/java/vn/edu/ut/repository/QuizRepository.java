package vn.edu.ut.repository;

import vn.edu.ut.entity.Contest;
import vn.edu.ut.entity.Lesson;
import vn.edu.ut.entity.Quiz;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuizRepository extends JpaRepository<Quiz, Integer> {
    Quiz findQuizByQuestionAndLesson(String question, Lesson lesson);
    Quiz findQuizByQuestionAndContest(String question, Contest contest);
}
