package vn.edu.ut.service;

import vn.edu.ut.entity.Courses;
import vn.edu.ut.entity.User;
import vn.edu.ut.payload.order.OrderRequest;
import vn.edu.ut.payload.order.OrderResponse;

import java.util.List;

public interface OrderService {
    OrderResponse createOrder(OrderRequest orderRequest, String email);
    void createOrder(User user, Courses courses, int totalPrice);
    List<OrderResponse> getAll();
    String deleteOrder(Integer orderId);
    List<OrderResponse> getAllByUser(Integer userId);
}
