package com.springboot.courses.repository;

import com.springboot.courses.entity.Courses;
import com.springboot.courses.entity.Lesson;
import com.springboot.courses.entity.Note;
import com.springboot.courses.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface NoteRepository extends JpaRepository<Note, Integer> {
    @Query("select n from Note n where n.lesson.chapter.course.id =?1 and n.user.id =?2")
    List<Note> listNoteByCoursesAndUser(Integer courseId, Integer userId);
}
