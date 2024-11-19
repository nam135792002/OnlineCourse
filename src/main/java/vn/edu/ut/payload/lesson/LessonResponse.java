package vn.edu.ut.payload.lesson;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import vn.edu.ut.enums.LessonType;
import vn.edu.ut.payload.TextLessonDto;
import vn.edu.ut.payload.video.VideoDto;
import vn.edu.ut.payload.quiz.QuizResponse;
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
@JsonPropertyOrder({"id", "name", "lesson_type", "created_at", "chapter_id", "video"})
public class LessonResponse {

    private Integer id;

    private String name;

    @JsonProperty("lesson_type")
    private LessonType lessonType;

    @JsonProperty("created_at")
    private Date createdAt;

    @JsonProperty("chapter_id")
    private Integer chapterId;

    private VideoDto video;

    private TextLessonDto text;

    private List<QuizResponse> quizList = new ArrayList<>();

    private int orders;
}
