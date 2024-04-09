package com.springboot.courses.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

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

    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CourseInfo> infoList = new ArrayList<>();

    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Chapter> chapterList = new ArrayList<>();

    @OneToMany(mappedBy = "courses", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Order> listOrders = new ArrayList<>();

    @OneToMany(mappedBy = "courses", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TrackCourse> listTrackCourses = new ArrayList<>();

    public void addInfoList(String value, InformationType type){
        this.infoList.add(new CourseInfo(value, type, this));
    }

    public void setInfoList(List<CourseInfo> infoList) {
        if(infoList != null && !infoList.isEmpty()){
            this.infoList.clear();
            this.infoList.addAll(infoList);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Courses courses)) return false;
        return Objects.equals(getId(), courses.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }
}
