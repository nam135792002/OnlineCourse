package com.springboot.courses.repository;

import com.springboot.courses.entity.Blog;
import com.springboot.courses.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BlogRepository extends JpaRepository<Blog, Integer> {
    List<Blog> findAllByUser(User user);
    @Query("select b from Blog b where b.title like %?1%")
    List<Blog> search(String keyword);
    boolean existsBlogByTitle(String title);
    Blog findBlogByTitle(String title);
    boolean existsBlogBySlug(String slug);
    Blog findBlogBySlug(String slug);
}
