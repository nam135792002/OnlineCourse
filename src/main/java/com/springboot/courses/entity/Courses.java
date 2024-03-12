package com.springboot.courses.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table(name = "courses")
public class Courses {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(length = 60, nullable = false, unique = true)
    private String title;

    @Column(length = 70, nullable = false, unique = true)
    private String slug;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String description;

    @Column(length = 100, nullable = false)
    private String thumbnail;

    @Column(nullable = false)
    private int price;

    private float discount;

    @Column(name = "student_count")
    private int studentCount;

    @Column(name = "published_at")
    private Date publishedAt;

    @Column(name = "is_coming_soon")
    private boolean isComingSoon;

    @Column(name = "is_published")
    private boolean isPublished;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;
}
