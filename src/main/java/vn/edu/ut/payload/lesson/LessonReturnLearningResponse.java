package vn.edu.ut.payload.lesson;

import com.fasterxml.jackson.annotation.JsonProperty;
import vn.edu.ut.enums.LessonType;
import vn.edu.ut.payload.TextLessonDto;
import vn.edu.ut.payload.quiz.QuizReturnLearningPage;
import vn.edu.ut.payload.video.VideoReturnResponse;
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
public class LessonReturnLearningResponse {
    private Integer id;

    private String name;

    @JsonProperty("lesson_type")
    private LessonType lessonType;

    @JsonProperty("created_at")
    private Date createdAt;

    @JsonProperty("chapter_id")
    private Integer chapterId;

    private VideoReturnResponse video;

    private TextLessonDto text;

    private List<QuizReturnLearningPage> quizList = new ArrayList<>();
}
