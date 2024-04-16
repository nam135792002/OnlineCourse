package com.springboot.courses.payload.note;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalTime;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class NoteRequest {

    private Integer id;

    @NotEmpty
    private String content;

    @NotNull
    @JsonProperty("lesson_id")
    private Integer lessonId;

    @NotNull
    @JsonProperty("user_id")
    private Integer userId;

    @JsonProperty("current_time")
    private LocalTime currentTime;
}
