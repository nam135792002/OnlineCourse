package com.springboot.courses.repository;

import com.springboot.courses.entity.Chapter;
import com.springboot.courses.entity.Lesson;
import com.springboot.courses.entity.Video;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LessonRepository extends JpaRepository<Lesson, Integer> {

    boolean existsLessonByNameAndChapter(String name, Chapter chapter);
    boolean existsLessonByVideo(Video video);
    Lesson findLessonByNameAndChapter(String name, Chapter chapter);
}
