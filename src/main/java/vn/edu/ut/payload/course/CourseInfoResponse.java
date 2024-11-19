package vn.edu.ut.payload.course;

import vn.edu.ut.enums.InformationType;
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
