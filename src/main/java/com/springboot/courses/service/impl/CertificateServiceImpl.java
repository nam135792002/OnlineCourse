package com.springboot.courses.service.impl;

import com.springboot.courses.entity.Certificate;
import com.springboot.courses.entity.Courses;
import com.springboot.courses.entity.User;
import com.springboot.courses.exception.BlogApiException;
import com.springboot.courses.exception.ResourceNotFoundException;
import com.springboot.courses.payload.certificate.CertificateResponse;
import com.springboot.courses.repository.CertificateRepository;
import com.springboot.courses.repository.UserRepository;
import com.springboot.courses.service.CertificateService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class CertificateServiceImpl implements CertificateService {

    @Autowired private CertificateRepository certificateRepository;
    @Autowired private UserRepository userRepository;
    @Autowired private ModelMapper modelMapper;

    @Override
    public CertificateResponse save(String email, Courses courses) {
        User user = userRepository.findByEmail(email).get();
        Certificate certificate = null;
        Certificate savedCertificate;
        if (courses.isFinished()) {
            certificate = new Certificate();
            certificate.setCourses(courses);
            certificate.setAchievedTime(new Date());
            certificate.setUser(user);

            savedCertificate = certificateRepository.save(certificate);
        } else {
            throw new BlogApiException(HttpStatus.NO_CONTENT, "Khóa học nay chưa kết thúc. Vui lòng chờ cập nhật thêm bài học mới");
        }
        CertificateResponse response = modelMapper.map(savedCertificate, CertificateResponse.class);
        response.setStudentName(savedCertificate.getUser().getFullName());
        response.setTitleCourse(savedCertificate.getCourses().getTitle());
        return response;
    }

    @Override
    public CertificateResponse getById(Integer certificateId) {
        Certificate certificate = certificateRepository.findById(certificateId)
                .orElseThrow(() -> new ResourceNotFoundException("Certificate", "id", certificateId));

        CertificateResponse response = modelMapper.map(certificate, CertificateResponse.class);
        response.setStudentName(certificate.getUser().getFullName());
        response.setTitleCourse(certificate.getCourses().getTitle());
        return response;
    }
}
