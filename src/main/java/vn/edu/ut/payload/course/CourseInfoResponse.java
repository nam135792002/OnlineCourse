package vn.edu.ut.payload.course;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import vn.edu.ut.enums.InformationType;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CourseInfoResponse {
    private Integer id;

    private String value;

    private InformationType type;
}
