package com.springboot.courses.controller;

import com.springboot.courses.payload.note.NoteRequest;
import com.springboot.courses.service.NoteService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/notes")
public class NoteController {

    @Autowired private NoteService noteService;

    @PostMapping("/create")
    public ResponseEntity<?> create(@RequestBody @Valid NoteRequest noteRequest){
        return new ResponseEntity<>(noteService.createNote(noteRequest), HttpStatus.CREATED);
    }

    @GetMapping ("/get-all")
    public ResponseEntity<?> list(@RequestParam(value = "lesson") Integer lessonId,
                                  @RequestParam(value = "user") Integer userId){
        return ResponseEntity.ok(noteService.getAll(userId, lessonId));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> update(@PathVariable(value = "id") Integer noteId,
                                    @RequestParam(value = "content") String content){
        return ResponseEntity.ok(noteService.updateNote(noteId, content));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable(value = "id") Integer noteId){
        return ResponseEntity.ok(noteService.deleteNote(noteId));
    }
}
