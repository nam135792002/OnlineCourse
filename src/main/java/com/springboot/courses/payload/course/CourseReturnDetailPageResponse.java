package com.springboot.courses.payload.course;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.springboot.courses.payload.chapter.ChapterReturnDetailResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CourseReturnDetailPageResponse {
    private Integer id;

    private String title;

    private String slug;

    private String thumbnail;

    private String description;

    @JsonProperty("published_at")
    private Date publishedAt;

    private int price;

    private float discount;

    @JsonProperty("info_list")
    private List<CourseInfoResponse> infoList;

    @JsonProperty("chapter_list")
    private List<ChapterReturnDetailResponse> chapterList;

    @JsonProperty("total_chapter")
    private int totalChapter;

    @JsonProperty("total_lesson")
    private int totalLesson;

    @JsonProperty("total_time")
    private LocalTime totalTime;
}
