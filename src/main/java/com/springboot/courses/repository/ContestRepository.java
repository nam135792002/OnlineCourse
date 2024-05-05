package com.springboot.courses.repository;

import com.springboot.courses.entity.Contest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContestRepository extends JpaRepository<Contest, Integer> {

}
