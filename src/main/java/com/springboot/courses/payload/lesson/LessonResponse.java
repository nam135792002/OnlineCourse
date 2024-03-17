package com.springboot.courses.payload.lesson;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.springboot.courses.entity.LessonType;
import com.springboot.courses.payload.VideoDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@JsonPropertyOrder({"id", "name", "lesson_type", "created_at", "chapter_id", "video"})
public class LessonResponse {

    private Integer id;

    private String name;

    @JsonProperty("lesson_type")
    private LessonType lessonType;

    @JsonProperty("created_at")
    private Date createdAt;

    @JsonProperty("chapter_id")
    private Integer chapterId;

    private VideoDto video;
}
