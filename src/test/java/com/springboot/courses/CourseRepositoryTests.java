package com.springboot.courses;

import com.springboot.courses.entity.CourseInfo;
import com.springboot.courses.entity.Courses;
import com.springboot.courses.entity.InformationType;
import com.springboot.courses.repository.CoursesRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(value = false)
public class CourseRepositoryTests {

    @Autowired private CoursesRepository coursesRepository;

    @Test
    public void addCourse(){
        Courses courses = coursesRepository.findById(4).get();

        CourseInfo info1 = new CourseInfo("aaaaaaa", InformationType.DETAIL, courses);
        CourseInfo info2 = new CourseInfo("bbbbbbb", InformationType.TARGET, courses);
        CourseInfo info3 = new CourseInfo("ccccccc", InformationType.REQUIREMENT, courses);

        courses.getInfoList().add(info1);
        courses.getInfoList().add(info2);
        courses.getInfoList().add(info3);

        coursesRepository.save(courses);
    }
}
