package com.springboot.courses.repository;

import com.springboot.courses.entity.Lesson;
import com.springboot.courses.entity.Note;
import com.springboot.courses.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NoteRepository extends JpaRepository<Note, Integer> {
    List<Note> findAllByLessonAndUser(Lesson lesson, User user);
}
