package com.springboot.courses.payload.quiz;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.springboot.courses.entity.QuizType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class QuizReturnLearningPage {

    private Integer id;

    private String question;

    @JsonProperty("quiz_type")
    private QuizType quizType;

    private int order;

    @JsonProperty("answer_list")
    private List<AnswerReturnLearningPage> answerList = new ArrayList<>();
}
