package vn.edu.ut.payload.quiz;

import com.fasterxml.jackson.annotation.JsonProperty;
import vn.edu.ut.enums.QuizType;
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
public class QuizResponse {

    private Integer id;

    private String question;

    @JsonProperty("quiz_type")
    private QuizType quizType;

    @JsonProperty("answer_list")
    private List<AnswerDto> answerList = new ArrayList<>();
}
