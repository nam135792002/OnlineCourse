package com.springboot.courses.controller;

import com.springboot.courses.payload.order.OrderRequest;
import com.springboot.courses.payload.order.OrderResponse;
import com.springboot.courses.service.OrderService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {
    @Autowired private OrderService orderService;

    @PostMapping("/create")
    public ResponseEntity<OrderResponse> createOrder(@RequestPart(value = "order") @Valid OrderRequest order,
                                                     @RequestParam(value = "email") String email){
        return ResponseEntity.ok(orderService.createOrder(order, email));
    }

    @GetMapping("/list-all")
    public ResponseEntity<?> listAll(){
        List<OrderResponse> listOrder = orderService.getAll();
        if (listOrder.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(listOrder);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable(value = "id") Integer orderId){
        return ResponseEntity.ok(orderService.deleteOrder(orderId));
    }

    @GetMapping("/get-all/user/{id}")
    public ResponseEntity<?> getAllByUser(@PathVariable(value = "id") Integer userId){
        List<OrderResponse> listOrder = orderService.getAllByUser(userId);
        if (listOrder.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(listOrder);
    }
}
