package com.springboot.courses.controller;

import com.springboot.courses.payload.note.NoteRequest;
import com.springboot.courses.service.NoteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/notes")
@Tag(
        name = "Note Controller",
        description = "REST APIs related to Note Entity"
)
public class NoteController {

    @Autowired private NoteService noteService;

    @Operation(
            summary = "Create Note REST API",
            description = "This REST API is used to create a new note"
    )
    @ApiResponse(
            responseCode = "201",
            description = "Http Status 201 CREATED - Note created successfully"
    )
    @SecurityRequirement(
            name = "Bear Authentication"
    )
    @PostMapping("/create")
    public ResponseEntity<?> create(@RequestBody @Valid NoteRequest noteRequest){
        return new ResponseEntity<>(noteService.createNote(noteRequest), HttpStatus.CREATED);
    }

    @Operation(
            summary = "Get All Notes REST API",
            description = "This REST API is used to get all notes for a specific course and user"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Http Status 200 OK - Notes retrieved successfully"
    )
    @ApiResponse(
            responseCode = "204",
            description = "Http Status 204 No Content - No notes found"
    )
    @SecurityRequirement(
            name = "Bear Authentication"
    )
    @GetMapping ("/get-all")
    public ResponseEntity<?> list(@RequestParam(value = "course") Integer courseId,
                                  @RequestParam(value = "user") Integer userId){
        return ResponseEntity.ok(noteService.getAll(userId, courseId));
    }

    @Operation(
            summary = "Update Note REST API",
            description = "This REST API is used to update the content of a specific note"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Http Status 200 OK - Note updated successfully"
    )
    @ApiResponse(
            responseCode = "404",
            description = "Http Status 404 Not Found - Note not found"
    )
    @SecurityRequirement(
            name = "Bear Authentication"
    )
    @PutMapping("/update/{id}")
    public ResponseEntity<?> update(@PathVariable(value = "id") Integer noteId,
                                    @RequestParam(value = "content") String content){
        return ResponseEntity.ok(noteService.updateNote(noteId, content));
    }

    @Operation(
            summary = "Delete Note REST API",
            description = "This REST API is used to delete a specific note"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Http Status 200 OK - Note deleted successfully"
    )
    @ApiResponse(
            responseCode = "404",
            description = "Http Status 404 Not Found - Note not found"
    )
    @SecurityRequirement(
            name = "Bear Authentication"
    )
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable(value = "id") Integer noteId){
        return ResponseEntity.ok(noteService.deleteNote(noteId));
    }
}
