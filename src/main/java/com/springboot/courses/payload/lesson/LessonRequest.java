package com.springboot.courses.payload.lesson;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class LessonRequest {

    private Integer id;

    @NotEmpty(message = "Lesson name can not be empty")
    @Length(min = 10, max = 100, message = "Lesson name must have 10 - 100 characters")
    private String name;

    @NotEmpty(message = "Lesson type can not be empty")
    @Pattern(
        regexp = "^(VIDEO|QUIZ|CODE|BLOG)$",
            message = "Lesson info type must be one of: video, quiz, code, blog"
    )
    @JsonProperty("lesson_type")
    private String lessonType;

    @NotNull(message = "Chapter id can not be null")
    @JsonProperty("chapter_id")
    private Integer chapterId;

    @NotNull(message = "Video id can not be null")
    @JsonProperty("video_id")
    private Integer videoId;
}
