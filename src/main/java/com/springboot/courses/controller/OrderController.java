package com.springboot.courses.controller;

import com.springboot.courses.payload.order.OrderRequest;
import com.springboot.courses.payload.order.OrderResponse;
import com.springboot.courses.service.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
@Tag(
        name = "Order Controller",
        description = "REST APIs related to Order Entity"
)
public class OrderController {
    @Autowired private OrderService orderService;

    @Operation(
            summary = "Create Order REST API",
            description = "This REST API is used to create a new order"
    )
    @ApiResponse(
            responseCode = "201",
            description = "Http Status 201 CREATED - Order created successfully"
    )
    @SecurityRequirement(
            name = "Bear Authentication"
    )
    @PostMapping("/create")
    public ResponseEntity<OrderResponse> createOrder(@RequestPart(value = "order") @Valid OrderRequest order,
                                                     @RequestParam(value = "email") String email){
        return ResponseEntity.ok(orderService.createOrder(order, email));
    }

    @Operation(
            summary = "Get All Orders REST API",
            description = "This REST API is used to get all orders"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Http Status 200 OK - Orders retrieved successfully"
    )
    @ApiResponse(
            responseCode = "204",
            description = "Http Status 204 No Content - No orders found"
    )
    @SecurityRequirement(
            name = "Bear Authentication"
    )
    @GetMapping("/list-all")
    public ResponseEntity<?> listAll(){
        List<OrderResponse> listOrder = orderService.getAll();
        if (listOrder.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(listOrder);
    }

    @Operation(
            summary = "Delete Order REST API",
            description = "This REST API is used to delete a specific order"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Http Status 200 OK - Order deleted successfully"
    )
    @ApiResponse(
            responseCode = "404",
            description = "Http Status 404 Not Found - Order not found"
    )
    @SecurityRequirement(
            name = "Bear Authentication"
    )
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable(value = "id") Integer orderId){
        return ResponseEntity.ok(orderService.deleteOrder(orderId));
    }

    @Operation(
            summary = "Get All Orders By User REST API",
            description = "This REST API is used to get all orders for a specific user"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Http Status 200 OK - Orders retrieved successfully"
    )
    @ApiResponse(
            responseCode = "204",
            description = "Http Status 204 No Content - No orders found"
    )
    @SecurityRequirement(
            name = "Bear Authentication"
    )
    @GetMapping("/get-all/user/{id}")
    public ResponseEntity<?> getAllByUser(@PathVariable(value = "id") Integer userId){
        List<OrderResponse> listOrder = orderService.getAllByUser(userId);
        if (listOrder.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(listOrder);
    }
}
