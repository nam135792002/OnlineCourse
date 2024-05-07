package com.springboot.courses.payload.contest;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.springboot.courses.payload.quiz.QuizRequest;
import jakarta.persistence.Column;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ContestRequest {

    private Integer id;

    @NotEmpty(message = "Title can not be empty")
    @Length(min = 4, max = 150, message = "Title must have 4-150 characters.")
    private String title;

    private int period;

    private boolean enabled;

    @Min(value = 1, message = "Minimal times is one")
    private int times;

    @NotNull(message = "List quiz can not be null")
    @Valid
    @JsonProperty("quiz_list")
    private List<QuizRequest> quizList = new ArrayList<>();
}
