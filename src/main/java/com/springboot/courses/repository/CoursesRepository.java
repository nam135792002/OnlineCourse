package com.springboot.courses.repository;

import com.springboot.courses.entity.Courses;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface CoursesRepository extends JpaRepository<Courses, Integer> {

    boolean existsCoursesByTitle(String title);

    boolean existsCoursesBySlug(String slug);

    @Query("select c from Courses c where c.title like %?1% or c.slug like %?1%" +
            "or c.description like %?1% or c.category.name like %?1%")
    Page<Courses> search(String keyword, Pageable pageable);

    @Query("select c from Courses c where (c.category.id = ?2) and (c.title like %?1% or c.slug like %?1%" +
            "or c.description like %?1% or c.category.name like %?1%)")
    Page<Courses> searchInCategory(String keyword, Integer categoryId, Pageable pageable);

    @Query("select c from Courses c where c.category.id = ?1")
    Page<Courses> findAllInCategory(Integer categoryId, Pageable pageable);
    Courses findByTitleOrSlug(String title, String slug);

    @Query("select c from Courses c where c.category.id = ?1")
    List<Courses> findAllByCategoryId(Integer categoryId);

    Optional<Courses> findCoursesBySlug(String slug);

    @Query("update Courses c set c.isEnabled =?2 where c.id =?1")
    @Modifying
    void switchEnabled(Integer courseId, boolean enabled);

    @Query("update Courses c set c.isPublished =?2, c.publishedAt = case " +
            "when ?2 = true then current_timestamp() else null end where c.id =?1")
    @Modifying
    void switchPublished(Integer courseId, boolean isPublished);

    @Query("update Courses c set c.isFinished =?2 where c.id =?1")
    @Modifying
    void switchFinished(Integer courseId, boolean isFinished);

    @Query("select c from Courses c where c.isEnabled = true and concat(c.title, ' ', c.category.name) like %?1% ")
    List<Courses> search(String keyword);
}
