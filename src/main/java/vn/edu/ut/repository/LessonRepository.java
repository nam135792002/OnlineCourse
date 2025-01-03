package vn.edu.ut.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import vn.edu.ut.entity.Chapter;
import vn.edu.ut.entity.Lesson;

public interface LessonRepository extends JpaRepository<Lesson, Integer> {

    boolean existsLessonByNameAndChapter(String name, Chapter chapter);

    Lesson findLessonByNameAndChapter(String name, Chapter chapter);
}
