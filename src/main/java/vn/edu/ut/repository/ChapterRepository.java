package vn.edu.ut.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import vn.edu.ut.entity.Chapter;
import vn.edu.ut.entity.Courses;

public interface ChapterRepository extends JpaRepository<Chapter, Integer> {

    @Query(value = """
            SELECT CASE
            	WHEN NOT EXISTS (
            		SELECT 1
            		FROM chapters c
            		WHERE c.chapter_name = :chapterName AND c.chapter_id != :chapterId AND c.course_id = :courseId
            	) THEN 'true' ELSE 'false' END
            """, nativeQuery = true)
    boolean existsNameInChapterAndCourse(String chapterName, Integer chapterId, Integer courseId);

    boolean existsChapterByNameAndCourse(String name, Courses course);

    @Query(value = """
            SELECT MAX(c.chapter_orders)
            FROM chapters c
            WHERE c.course_id = :courseId
            """, nativeQuery = true)
    Integer findMaxOrderByCourse(Integer courseId);

    @Modifying
    @Query(value = """
            UPDATE chapters c
            SET c.chapter_orders = c.chapter_orders + 1
            WHERE c.course_id = :courseId AND c.chapter_orders >= :chapterOrder
            """, nativeQuery = true)
    void updateChapterOrderByCourseIdAfterCreateNewChapter(Integer courseId, Integer chapterOrder);

    @Modifying
    @Query(value = """
            UPDATE chapters c
            SET c.chapter_orders = c.chapter_orders - 1
            WHERE c.course_id = :courseId
              AND c.chapter_orders > :chapterOrderCurrent AND c.chapter_orders <= :chapterOrderRequest
            """, nativeQuery = true)
    void updateChapterOrderByCourseIdAfterUpdateChapterPushBottom(Integer courseId, Integer chapterOrderCurrent,
                                                                  Integer chapterOrderRequest);

    @Modifying
    @Query(value = """
            UPDATE chapters c
            SET c.chapter_orders = c.chapter_orders + 1
            WHERE c.course_id = :courseId
                          AND c.chapter_orders >= :chapterOrderRequest AND c.chapter_orders < :chapterOrderCurrent
            """, nativeQuery = true)
    void updateChapterOrderByCourseIdAfterUpdateChapterPushUp(Integer courseId, Integer chapterOrderCurrent,
                                                              Integer chapterOrderRequest);

    @Modifying
    @Query(value = """
            UPDATE chapters c
            SET c.chapter_orders = c.chapter_orders - 1
            WHERE c.course_id = :courseId AND c.chapter_orders >= :chapterOrder
            """, nativeQuery = true)
    void updateChapterOrderByCourseIdAfterDeleteChapter(Integer courseId, Integer chapterOrder);
}
