package com.springboot.courses.controller;

import com.springboot.courses.payload.ChapterDto;
import com.springboot.courses.service.ChapterService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/api/courses")
public class ChapterController {

    @Autowired private ChapterService chapterService;

    @PostMapping("/{courseId}/chapters/create")
    public ResponseEntity<ChapterDto> addChapter(@RequestBody @Valid ChapterDto chapterDto,
                                                 @PathVariable(value = "courseId") Integer courseId){
        ChapterDto response = chapterService.createChapter(courseId, chapterDto);

        URI uri = URI.create("/api/chapters/" + response.getId());
        return ResponseEntity.created(uri).body(response);
    }

    @PutMapping("/{courseId}/chapters/{chapterId}/update")
    public ResponseEntity<ChapterDto> updateChapter(@PathVariable(value = "courseId") Integer courseId,
                                                    @PathVariable(value = "chapterId") Integer chapterId,
                                                    @RequestBody @Valid ChapterDto chapterDto){
        return ResponseEntity.ok(chapterService.updateChapter(courseId, chapterId, chapterDto));
    }

    @DeleteMapping("/{courseId}/chapters/{chapterId}/delete")
    public ResponseEntity<String> deleteChapter(@PathVariable(value = "courseId") Integer courseId,
                                                @PathVariable(value = "chapterId") Integer chapterId){
        return ResponseEntity.ok(chapterService.deleteChapter(courseId, chapterId));
    }
}
