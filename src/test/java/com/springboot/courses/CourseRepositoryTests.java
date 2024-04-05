package com.springboot.courses;

import com.springboot.courses.entity.CourseInfo;
import com.springboot.courses.entity.Courses;
import com.springboot.courses.entity.InformationType;
import com.springboot.courses.entity.User;
import com.springboot.courses.repository.CoursesRepository;
import com.springboot.courses.repository.UserRepository;
import com.springboot.courses.utils.UploadFile;
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
    @Autowired private UserRepository userRepository;
    @Autowired private UploadFile uploadFile;

    @Test
    public void addCourse(){
        Courses courses = coursesRepository.findById(4).get();

        CourseInfo info2 = new CourseInfo("bbbbbbb", InformationType.TARGET, courses);
        CourseInfo info3 = new CourseInfo("ccccccc", InformationType.REQUIREMENT, courses);

        courses.getInfoList().add(info2);
        courses.getInfoList().add(info3);

        coursesRepository.save(courses);
    }

    @Test
    public void testDeleteUser(){
        User user = userRepository.findById(2).get();
        uploadFile.deleteImageInCloudinary(user.getPhoto());
        userRepository.delete(user);

    }
}
