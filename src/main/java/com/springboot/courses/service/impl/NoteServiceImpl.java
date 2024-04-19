package com.springboot.courses.service.impl;

import com.springboot.courses.entity.Courses;
import com.springboot.courses.entity.Lesson;
import com.springboot.courses.entity.Note;
import com.springboot.courses.entity.User;
import com.springboot.courses.exception.ResourceNotFoundException;
import com.springboot.courses.payload.note.NoteRequest;
import com.springboot.courses.payload.note.NoteResponse;
import com.springboot.courses.repository.CoursesRepository;
import com.springboot.courses.repository.LessonRepository;
import com.springboot.courses.repository.NoteRepository;
import com.springboot.courses.repository.UserRepository;
import com.springboot.courses.service.NoteService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class NoteServiceImpl implements NoteService {

    @Autowired private NoteRepository noteRepository;
    @Autowired private LessonRepository lessonRepository;
    @Autowired private UserRepository userRepository;
    @Autowired private ModelMapper modelMapper;
    @Autowired private CoursesRepository coursesRepository;

    @Override
    public NoteResponse createNote(NoteRequest noteRequest) {
        Lesson lesson = lessonRepository.findById(noteRequest.getLessonId())
                .orElseThrow(() -> new ResourceNotFoundException("Lesson", "id", noteRequest.getLessonId()));

        User user = userRepository.findById(noteRequest.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", noteRequest.getUserId()));

        Note note = new Note();
        note.setContent(noteRequest.getContent());
        note.setUser(user);
        note.setLesson(lesson);
        note.setCreatedAt(new Date());
        note.setCurrentTime(noteRequest.getCurrentTime());

        Note savedNote = noteRepository.save(note);
        return convertToResponse(savedNote);
    }

    @Override
    public List<NoteResponse> getAll(Integer userId, Integer courseId) {
        Courses courses = coursesRepository.findById(courseId)
                .orElseThrow(() -> new ResourceNotFoundException("Courses", "id", courseId));

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));

        List<Note> listNotes = noteRepository.listNoteByCoursesAndUser(courses.getId(), user.getId());
        return listNotes.stream().map(this::convertToResponse).toList();
    }

    @Override
    public NoteResponse updateNote(Integer noteId, String content) {
        Note noteInDB = noteRepository.findById(noteId)
                .orElseThrow(() -> new ResourceNotFoundException("Note", "id", noteId));
        noteInDB.setContent(content);
        return convertToResponse(noteRepository.save(noteInDB));
    }

    @Override
    public String deleteNote(Integer noteId) {
        Note noteInDB = noteRepository.findById(noteId)
                .orElseThrow(() -> new ResourceNotFoundException("Note", "id", noteId));

        noteRepository.delete(noteInDB);
        return "Xóa ghi chú thành công";
    }

    private NoteResponse convertToResponse(Note note){
        NoteResponse noteResponse = modelMapper.map(note, NoteResponse.class);
        noteResponse.setLessonId(note.getLesson().getId());
        noteResponse.setTitleLesson(note.getLesson().getName());
        noteResponse.setTitleChapter(note.getLesson().getChapter().getName());

        return noteResponse;
    }
}
