package vn.edu.ut.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import vn.edu.ut.entity.Category;

import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Integer> {
    @Query(value = """
            SELECT c.*
            FROM categories c
            WHERE c.category_name LIKE CONCAT('%', :keyword, '%')
            """, nativeQuery = true)
    Page<Category> search(String keyword, Pageable pageable);

    @Query(value = """
            SELECT c.*
            FROM categories c
            WHERE c.category_name = :name OR c.category_slug = :slug
            """, nativeQuery = true)
    Optional<Category> findByNameOrSlug(String name, String slug);
}
