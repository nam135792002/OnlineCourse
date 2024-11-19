package vn.edu.ut.repository;

import vn.edu.ut.entity.Chapter;
import vn.edu.ut.entity.Lesson;
import vn.edu.ut.entity.TextLesson;
import vn.edu.ut.entity.Video;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LessonRepository extends JpaRepository<Lesson, Integer> {

    boolean existsLessonByNameAndChapter(String name, Chapter chapter);
    boolean existsLessonByVideo(Video video);
    boolean existsLessonByText(TextLesson textLesson);
    Lesson findLessonByNameAndChapter(String name, Chapter chapter);
}
