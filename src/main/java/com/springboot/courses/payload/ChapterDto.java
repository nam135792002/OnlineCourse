package com.springboot.courses.payload;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ChapterDto {
    private Integer id;

    @NotEmpty
    @Length(min = 5, max = 190, message = "Chapter name must have 5 - 190 characters")
    private String name;

    @JsonProperty("total_lesson")
    private int totalLesson;
}
