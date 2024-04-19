package com.springboot.courses.payload.qa;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@JsonPropertyOrder({"id", "content", "lessonId", "user_id", "username", "created_at_formatted", "parent_id", "children"})
public class QAResponse {

    private Integer id;

    private String content;

    @JsonProperty("lesson_id")
    private Integer lessonId;

    @JsonProperty("user_id")
    private Integer userId;

    private String username;

    @JsonProperty("created_at_formatted")
    private String createdAtFormatted;

    @JsonProperty("parent_id")
    private Integer parentId;

    private List<QAResponse> children = new ArrayList<>();
}
