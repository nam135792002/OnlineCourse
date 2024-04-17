package com.springboot.courses.payload.course;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CourseReturnHomePageResponse {

    private Integer id;

    private String title;

    private String slug;

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
}
