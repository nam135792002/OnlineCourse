package com.springboot.courses.payload.note;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalTime;
import java.util.Date;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class NoteResponse {

    private Integer id;

    private String content;

    @JsonProperty("current_time")
    private LocalTime currentTime;

    @JsonProperty("created_at")
    private Date createdAt;

    @JsonProperty("lesson_id")
    private Integer lessonId;

    @JsonProperty("title_lesson")
    private String titleLesson;

    @JsonProperty("title_chapter")
    private String titleChapter;
}
