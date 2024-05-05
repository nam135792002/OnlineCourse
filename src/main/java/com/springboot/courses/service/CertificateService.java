package com.springboot.courses.service;

import com.springboot.courses.entity.Courses;
import com.springboot.courses.payload.certificate.CertificateResponse;

public interface CertificateService {
    CertificateResponse save(String email, Courses courses);
}
