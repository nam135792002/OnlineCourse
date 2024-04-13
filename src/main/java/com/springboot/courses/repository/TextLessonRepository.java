package com.springboot.courses.repository;

import com.springboot.courses.entity.TextLesson;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TextLessonRepository extends JpaRepository<TextLesson, Integer> {

}
