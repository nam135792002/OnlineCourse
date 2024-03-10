package com.springboot.courses.payload;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CategoryDto {

    private Integer id;

    @NotEmpty(message = "Category name can not be empty")
    @Length(min = 10, max = 45, message = "Category name must have 10-45 characters")
    private String name;

    private String slug;
}
