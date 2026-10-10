package com.example.orderservice.controller;

import com.example.orderservice.dto.BaseResponse;
import com.example.orderservice.dto.OrderDTO;
import com.example.orderservice.dto.OrderRequestDTO;
import com.example.orderservice.service.OrderService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/orders")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping("/health-check")
    public String healthCheck() {
        return orderService.getHealthCheck();
    }

    @PostMapping({"", "/create"})
    public ResponseEntity<BaseResponse<OrderDTO>> createOrder(@Valid @RequestBody OrderRequestDTO request) {
        BaseResponse<OrderDTO> response = orderService.createOrder(request);
        return ResponseEntity.ok(response);
    }
}
