package vn.edu.ut.repository;

import vn.edu.ut.entity.Certificate;
import vn.edu.ut.entity.Courses;
import vn.edu.ut.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CertificateRepository extends JpaRepository<Certificate, Integer> {
    Certificate findCertificateByUserAndCourses(User user, Courses courses);
}
