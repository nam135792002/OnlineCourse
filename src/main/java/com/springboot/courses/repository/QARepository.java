package com.springboot.courses.repository;

import com.springboot.courses.entity.Lesson;
import com.springboot.courses.entity.QA;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface QARepository extends JpaRepository<QA, Integer> {
    @Query("select qa from QA qa where qa.lesson.id =?1 and qa.parent.id is null")
    List<QA> findAllByLesson(Integer lessonId);
}
