package com.springboot.courses.payload.course;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.springboot.courses.payload.CategoryDto;
import com.springboot.courses.payload.chapter.ChapterDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@JsonPropertyOrder({"id", "title", "slug", "description", "thumbnail", "price", "discount",
        "student_count", "published_at", "is_enabled", "is_published", "category"})
public class CourseResponse {

    private Integer id;

    private String title;

    private String slug;

    private String description;

    private String thumbnail;

    private int price;

    private float discount;

    @JsonProperty("student_count")
    private int studentCount;

    @JsonProperty("published_at")
    private Date publishedAt;

    @JsonProperty("is_enabled")
    private boolean isEnabled;

    @JsonProperty("is_published")
    private boolean isPublished;

    @JsonProperty("is_finished")
    private boolean isFinished;

    @JsonProperty("total_review")
    private int totalReview;

    @JsonProperty("average_review")
    private double averageReview;

    private CategoryDto category;

    @JsonProperty("info_list")
    private List<CourseInfoResponse> infoList;

    @JsonProperty("chapter_list")
    private List<ChapterDto> chapterList;

    @JsonProperty("total_chapter")
    private int totalChapter;

    @JsonProperty("total_lesson")
    private int totalLesson;
}
