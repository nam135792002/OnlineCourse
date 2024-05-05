package com.springboot.courses.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table(name = "contests")
public class Contest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(length = 150, nullable = false, unique = true)
    private String title;

    @Column(nullable = false)
    private int period;

    private boolean enabled;

    @Column(name = "created_at", nullable = false)
    private Date createdAt;

    @OneToMany(mappedBy = "contest", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Quiz> listQuizzes = new ArrayList<>();

    public void addQuiz(Quiz quiz){
        this.listQuizzes.add(new Quiz(quiz, this));
    }

    public void setQuizList(List<Quiz> quizList) {
        if(quizList != null && !quizList.isEmpty()){
            this.listQuizzes.clear();
            this.listQuizzes.addAll(quizList);
        }
    }
}
