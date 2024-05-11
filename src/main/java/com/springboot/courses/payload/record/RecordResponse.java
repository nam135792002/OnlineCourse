package com.springboot.courses.payload.record;

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
public class RecordResponse {

    private Integer id;

    private String username;

    @JsonProperty("user_id")
    private Integer userId;

    @JsonProperty("title_contest")
    private String titleContest;

    @JsonProperty("contest_id")
    private Integer contestId;

    @JsonProperty("joined_at")
    private Date joinedAt;

    private float grade;

    private int period;

    @JsonProperty("total_quizzes")
    private int totalQuizzes;

    @JsonProperty("total_quiz_is_correct")
    private int totalQuizIsCorrect;
}
