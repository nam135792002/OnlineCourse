package com.springboot.courses.service;

import com.springboot.courses.entity.Courses;
import com.springboot.courses.entity.User;
import com.springboot.courses.payload.order.OrderRequest;
import com.springboot.courses.payload.order.OrderResponse;

import java.util.List;

public interface OrderService {
    OrderResponse createOrder(OrderRequest orderRequest, String email);
    void createOrder(User user, Courses courses, int totalPrice);
    List<OrderResponse> getAll();
    String deleteOrder(Integer orderId);
    List<OrderResponse> getAllByUser(Integer userId);
}
