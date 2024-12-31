package vn.edu.ut.payload.course;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CourseReturnSearch {

    private Integer id;

    private String title;

    private String slug;

    private String thumbnail;

    private String description;

    @JsonProperty("is_published")
    private boolean isPublished;

    @JsonProperty("published_at")
    @Temporal(TemporalType.DATE)
    private Date publishedAt;

    @JsonProperty("average_review")
    private Double averageReview;
}
