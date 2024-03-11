package com.springboot.courses.payload;

import com.springboot.courses.entity.Category;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CoursesDto {

    private Integer id;
    private String title;
    private String slug;
    private String description;
    private String thumbnail;
    private int price;
    private float discount;
    private int studentCount;
    private Date publishedAt;
    private boolean isComingSoon;
    private boolean isPublished;
    private Category category;
}
