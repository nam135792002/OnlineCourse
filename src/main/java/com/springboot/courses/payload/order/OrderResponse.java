package com.springboot.courses.payload.order;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class OrderResponse {

    private Integer id;

    @JsonProperty("created_time")
    private Date createdTime;

    @JsonProperty("total_price")
    private int totalPrice;

    @JsonProperty("customer_name")
    private String customerName;

    @JsonProperty("course_name")
    private String courseName;
}
