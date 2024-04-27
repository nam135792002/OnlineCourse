package com.springboot.courses.repository;

import com.springboot.courses.entity.Certificate;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CertificateRepository extends JpaRepository<Certificate, Integer> {

}
