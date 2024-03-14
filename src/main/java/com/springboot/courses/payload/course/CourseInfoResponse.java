package com.springboot.courses.payload.course;

import com.springboot.courses.entity.InformationType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CourseInfoResponse {
    private Integer id;
    private String value;
    private InformationType type;
}
