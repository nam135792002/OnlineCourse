package vn.edu.ut.repository;

import vn.edu.ut.entity.Video;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VideoRepository extends JpaRepository<Video, Integer> {

}
