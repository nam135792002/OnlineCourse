package com.springboot.courses.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor

@Entity
@Table(name = "record_detail")
public class RecordDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "record_id")
    private Record record;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "quiz_id")
    private Quiz quiz;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "records_answers",
                joinColumns = @JoinColumn(name = "record_detail_id", referencedColumnName = "id"),
                inverseJoinColumns = @JoinColumn(name = "answer_id", referencedColumnName = "id"))
    private Set<Answer> answer;

    @Column(name = "content_perforate")
    private String contentPerforate;

    public RecordDetail(Record record, Quiz quiz, Set<Answer> answer, String contentPerforate) {
        this.record = record;
        this.quiz = quiz;
        this.answer = answer;
        this.contentPerforate = contentPerforate;
    }
}
