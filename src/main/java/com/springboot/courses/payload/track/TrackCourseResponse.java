package com.springboot.courses.payload.track;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalTime;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class TrackCourseResponse {

    private Integer id;

    @JsonProperty("lesson_id")
    private Integer lessonId;

    @JsonProperty("duration_video")
    private LocalTime durationVideo;

    @JsonProperty("is_completed")
    private boolean isCompleted;

    @JsonProperty("is_unlock")
    private boolean isUnlock;
}
