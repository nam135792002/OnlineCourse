package com.springboot.courses.payload.dashboard;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CountSummaryResponse {

    @JsonProperty("total_users")
    private int totalUsers;

    @JsonProperty("total_categories")
    private int totalCategories;

    @JsonProperty("total_courses")
    private int totalCourses;

    @JsonProperty("total_quizzes")
    private int totalQuizzes;

    @JsonProperty("total_blogs")
    private int totalBlogs;

    @JsonProperty("total_orders")
    private int totalOrders;

    @JsonProperty("total_reviews")
    private int totalReviews;

    @JsonProperty("total_incomes")
    private int totalIncomes;
}
