package com.springboot.courses.service;

import com.springboot.courses.payload.note.NoteRequest;
import com.springboot.courses.payload.note.NoteResponse;

import java.util.List;

public interface NoteService {
    NoteResponse createNote(NoteRequest noteRequest);
    List<NoteResponse> getAll(Integer userId, Integer lessonId);
    NoteResponse updateNote(Integer noteId, String content);
    String deleteNote(Integer noteId);
}
