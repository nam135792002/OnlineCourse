package vn.edu.ut.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "chapters")
public class Chapter extends AuditEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "chapter_id")
    private Integer id;

    @Column(name = "chapter_name", nullable = false, length = 100)
    private String name;

    @Column(name = "chapter_orders", nullable = false)
    private int orders;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course_id", nullable = false)
    private Courses course;

    /*TODO: update lesson list and track course list after update lesson entity*/
//    @OneToMany(mappedBy = "chapter", cascade = CascadeType.ALL, orphanRemoval = true)
//    private List<Lesson> lessonList = new ArrayList<>();

//    @OneToMany(mappedBy = "chapter", cascade = CascadeType.ALL, orphanRemoval = true)
//    private List<TrackCourse> trackCourseList = new ArrayList<>();

    public Chapter(String name, int orders, Courses course) {
        this.name = name;
        this.orders = orders;
        this.course = course;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Chapter chapter)) return false;
        return Objects.equals(getId(), chapter.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }
}
