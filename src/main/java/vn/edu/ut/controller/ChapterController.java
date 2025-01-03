package vn.edu.ut.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vn.edu.ut.payload.chapter.ChapterDto;
import vn.edu.ut.service.IChapterService;

import java.net.URI;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/courses")
@Tag(
        name = "Create, Update and Delete REST APIs for Chapter Resource"
)
public class ChapterController {
    private final IChapterService iChapterService;

    @Operation(
            summary = "Create chapter in a course REST API",
            description = "Create chapter REST API is used to save chapter into database"
    )
    @ApiResponse(
            responseCode = "201",
            description = "Http Status 201 CREATED"
    )
    @SecurityRequirement(
            name = "Bear Authentication"
    )
    @PostMapping("/{courseId}/chapters/create")
    public ResponseEntity<ChapterDto> addChapter(@RequestBody @Valid ChapterDto chapterDto,
                                                 @PathVariable(value = "courseId") Integer courseId) {
        ChapterDto response = iChapterService.createChapter(courseId, chapterDto);

        URI uri = URI.create("/api/chapters/" + response.getId());
        return ResponseEntity.created(uri).body(response);
    }

    @Operation(
            summary = "Update chapter in a course by ID REST API",
            description = "Update chapter REST API is used to update a particular chapter into the database"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Http Status 200 SUCCESS"
    )
    @ApiResponse(
            responseCode = "404",
            description = "Http Status 404 NOT FOUND"
    )
    @SecurityRequirement(
            name = "Bear Authentication"
    )
    @PutMapping("/{courseId}/chapters/{chapterId}/update")
    public ResponseEntity<ChapterDto> updateChapter(@PathVariable(value = "courseId") Integer courseId,
                                                    @PathVariable(value = "chapterId") Integer chapterId,
                                                    @RequestBody @Valid ChapterDto chapterDto) {
        return ResponseEntity.ok(iChapterService.updateChapter(courseId, chapterId, chapterDto));
    }

    @Operation(
            summary = "Delete chapter in a course REST API",
            description = "Delete chapter REST API is used to delete a particular chapter from the database"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Http Status 200 SUCCESS"
    )
    @ApiResponse(
            responseCode = "404",
            description = "Http Status 404 NOT FOUND"
    )
    @SecurityRequirement(
            name = "Bear Authentication"
    )
    @DeleteMapping("/{courseId}/chapters/{chapterId}/delete")
    public ResponseEntity<Void> deleteChapter(@PathVariable(value = "courseId") Integer courseId,
                                              @PathVariable(value = "chapterId") Integer chapterId) {
        iChapterService.deleteChapter(courseId, chapterId);
        return ResponseEntity.ok().build();
    }
}
