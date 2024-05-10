package com.springboot.courses.payload.course;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.springboot.courses.payload.chapter.ChapterReturnDetailResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalTime;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CourseReturnLearningPageResponse {
    private Integer id;

    private String title;

    @JsonProperty("chapter_list")
    private List<ChapterReturnDetailResponse> chapterList;

    @JsonProperty("total_lesson")
    private int totalLesson;

    @JsonProperty("is_finished")
    private boolean isFinished;
}
