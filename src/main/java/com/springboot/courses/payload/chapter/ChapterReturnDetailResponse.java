package com.springboot.courses.payload.chapter;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.springboot.courses.payload.lesson.LessonResponse;
import com.springboot.courses.payload.lesson.LessonReturnDetailResponse;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ChapterReturnDetailResponse {

    private Integer id;

    private String name;

    @JsonProperty("total_lesson")
    private int totalLesson;

    @JsonProperty("lesson_list")
    private List<LessonReturnDetailResponse> lessonList;

    private int orders;
}
