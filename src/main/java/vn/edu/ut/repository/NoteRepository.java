package vn.edu.ut.repository;

import vn.edu.ut.entity.Note;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface NoteRepository extends JpaRepository<Note, Integer> {
    @Query("select n from Note n where n.lesson.chapter.course.id =?1 and n.user.id =?2")
    List<Note> listNoteByCoursesAndUser(Integer courseId, Integer userId);
}
