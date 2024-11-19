package vn.edu.ut.service;

import vn.edu.ut.entity.Courses;
import vn.edu.ut.payload.certificate.CertificateResponse;

public interface CertificateService {
    CertificateResponse save(String email, Courses courses);
    CertificateResponse getById(Integer certificateId);
}
