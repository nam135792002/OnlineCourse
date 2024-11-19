package vn.edu.ut;

import vn.edu.ut.entity.CourseInfo;
import vn.edu.ut.entity.Courses;
import vn.edu.ut.entity.User;
import vn.edu.ut.enums.InformationType;
import vn.edu.ut.repository.CoursesRepository;
import vn.edu.ut.repository.UserRepository;
import vn.edu.ut.utils.UploadFile;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(value = false)
class CourseRepositoryTests {

    @Autowired private CoursesRepository coursesRepository;
    @Autowired private UserRepository userRepository;
    @Autowired private UploadFile uploadFile;

    @Test
    void addCourse(){
        Courses courses = coursesRepository.findById(4).get();

        CourseInfo info2 = new CourseInfo("bbbbbbb", InformationType.TARGET, courses);
        CourseInfo info3 = new CourseInfo("ccccccc", InformationType.REQUIREMENT, courses);

        courses.getInfoList().add(info2);
        courses.getInfoList().add(info3);

        coursesRepository.save(courses);
    }

    @Test
    void testDeleteUser(){
        User user = userRepository.findById(2).get();
        uploadFile.deleteImageInCloudinary(user.getPhoto());
        userRepository.delete(user);

    }
}
