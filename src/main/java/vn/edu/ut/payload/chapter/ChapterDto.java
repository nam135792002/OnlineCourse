package vn.edu.ut.payload.chapter;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class ChapterDto {
    private Integer id;

    @NotBlank(message = "Chapter name can not be empty")
    @Length(min = 5, max = 100, message = "Chapter name must have 5 - 190 characters")
    private String name;

    private int orders;

    @JsonProperty("total_lesson")
    private Integer totalLesson;

//    private List<LessonResponse> lessonList;

}
