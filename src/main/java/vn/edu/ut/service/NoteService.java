package vn.edu.ut.service;

import vn.edu.ut.payload.note.NoteRequest;
import vn.edu.ut.payload.note.NoteResponse;

import java.util.List;

public interface NoteService {
    NoteResponse createNote(NoteRequest noteRequest);
    List<NoteResponse> getAll(Integer userId, Integer courseId);
    NoteResponse updateNote(Integer noteId, String content);
    String deleteNote(Integer noteId);
}
