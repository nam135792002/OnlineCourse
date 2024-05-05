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
public class CourseReturnSearch {

    private Integer id;

    private String title;

    private String slug;

    private String thumbnail;

    private String description;

    @JsonProperty("is_published")
    private boolean isPublished;

    @JsonProperty("published_at")
    private Date publishedAt;

    @JsonProperty("average_review")
    private double averageReview;
}
