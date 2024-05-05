package com.springboot.courses.payload.contest;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.springboot.courses.payload.quiz.QuizResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ContestResponse {

    private Integer id;

    private String title;

    private int period;

    @JsonProperty("created_at")
    private Date createdAt;

    private boolean enabled;

    private List<QuizResponse> listQuizzes = new ArrayList<>();
}
