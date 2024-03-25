package com.springboot.courses.repository;

import com.springboot.courses.entity.Lesson;
import com.springboot.courses.entity.Quiz;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuizRepository extends JpaRepository<Quiz, Integer> {
    boolean existsQuizByQuestionAndLesson(String question, Lesson lesson);

    Quiz findQuizByQuestionAndLesson(String question, Lesson lesson);
}
