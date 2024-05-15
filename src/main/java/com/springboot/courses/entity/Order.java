package com.springboot.courses.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "created_time", nullable = false)
    private Date createdTime;

    @Column(name = "total_price", nullable = false)
    private int totalPrice;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course_id")
    private Courses courses;

    public Order(Integer id, Date createdTime, int totalPrice) {
        this.id = id;
        this.createdTime = createdTime;
        this.totalPrice = totalPrice;
    }

    public Order(String categoryName, int total){
        this.courses = new Courses();
        this.courses.setCategory(new Category(categoryName));
        this.totalPrice = total;
    }

    public Order(int total, String productName){
        this.courses = new Courses(productName);
        this.totalPrice = total;
    }
}
