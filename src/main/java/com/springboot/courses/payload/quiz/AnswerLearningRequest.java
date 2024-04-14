package com.springboot.courses.payload.quiz;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AnswerLearningRequest {

    @JsonProperty("answer_id")
    private Integer id;

    @JsonProperty("content_perforate")
    private String contentPerforate;
}
