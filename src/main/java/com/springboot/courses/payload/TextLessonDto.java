package com.springboot.courses.payload;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class TextLessonDto {

    private Integer id;

    @NotEmpty(message = "Content's lesson can not be empty")
    private String content;
}
