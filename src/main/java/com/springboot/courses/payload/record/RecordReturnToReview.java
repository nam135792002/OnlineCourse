package com.springboot.courses.payload.record;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.springboot.courses.payload.quiz.QuizReturnInRecord;
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
public class RecordReturnToReview {

    @JsonProperty("record_id")
    private Integer id;

    @JsonProperty("title_contest")
    private String titleContest;

    @JsonProperty("joined_at")
    private Date joinedAt;

    private float grade;

    private int period;

    @JsonProperty("total_quizzes")
    private float totalQuizzes;

    @JsonProperty("total_quiz_is_correct")
    private float totalQuizIsCorrect;

    @JsonProperty("list_quizzes")
    private List<QuizReturnInRecord> listQuizzes = new ArrayList<>();

}
