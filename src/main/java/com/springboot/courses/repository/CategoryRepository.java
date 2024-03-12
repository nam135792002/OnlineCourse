package com.springboot.courses.repository;

import com.springboot.courses.entity.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CategoryRepository extends JpaRepository<Category, Integer> {

    boolean existsCategoriesByName(String name);

    boolean existsCategoriesBySlug(String slug);

    @Query("select c from Category c where c.name like %?1%")
    Page<Category> search(String keyword, Pageable pageable);

    Category findByNameOrSlug(String name, String slug);
}
