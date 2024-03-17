package com.springboot.courses.repository;

import com.springboot.courses.entity.Chapter;
import com.springboot.courses.entity.Courses;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChapterRepository extends JpaRepository<Chapter, Integer> {

    Chapter findChapterByNameAndCourse(String name, Courses course);

}
