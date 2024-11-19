package vn.edu.ut.repository;

import vn.edu.ut.entity.Chapter;
import vn.edu.ut.entity.Courses;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChapterRepository extends JpaRepository<Chapter, Integer> {

    Chapter findChapterByNameAndCourse(String name, Courses course);

}
