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
public class CourseReturnHomePageResponse {

    private Integer id;

    private String title;

    private String slug;

    private String thumbnail;

    private Integer price;

    private Float discount;

    @JsonProperty("student_count")
    private Integer studentCount;

    @JsonProperty("published_at")
    @Temporal(TemporalType.DATE)
    private Date publishedAt;

    @JsonProperty("is_enabled")
    private boolean isEnabled;

    @JsonProperty("is_published")
    private boolean isPublished;

    @JsonProperty("total_review")
    private Integer totalReview;

    @JsonProperty("average_review")
    private Double averageReview;
}
