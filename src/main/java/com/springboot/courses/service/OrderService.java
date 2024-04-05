package com.springboot.courses.service;

import com.springboot.courses.payload.order.OrderRequest;
import com.springboot.courses.payload.order.OrderResponse;
import jakarta.servlet.http.HttpServletRequest;

import java.util.List;

public interface OrderService {
    OrderResponse createOrder(OrderRequest orderRequest, HttpServletRequest servletRequest);
    List<OrderResponse> getAll();
    String deleteOrder(Integer orderId);
}
