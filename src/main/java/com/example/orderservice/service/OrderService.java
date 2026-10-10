package com.example.orderservice.service;

import com.example.orderservice.dto.BaseResponse;
import com.example.orderservice.dto.OrderDTO;
import com.example.orderservice.dto.OrderRequestDTO;
import com.example.orderservice.entity.Course;
import com.example.orderservice.entity.Order;
import com.example.orderservice.exception.BadRequestException;
import com.example.orderservice.exception.ResourceNotFoundException;
import com.example.orderservice.repository.CourseRepository;
import com.example.orderservice.repository.OrderRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final CourseRepository courseRepository;

    public OrderService(OrderRepository orderRepository, CourseRepository courseRepository) {
        this.orderRepository = orderRepository;
        this.courseRepository = courseRepository;
    }

    public String getHealthCheck() {
        return "Order Service is Up";
    }

    @Transactional
    public BaseResponse<OrderDTO> createOrder(OrderRequestDTO request) {
        if (request == null) {
            throw new BadRequestException("Request body is required");
        }

        if (request.getStudentId() == null || request.getStudentId() <= 0) {
            throw new BadRequestException("studentId is required and must be greater than 0");
        }

        if (request.getCourseId() == null || request.getCourseId() <= 0) {
            throw new BadRequestException("courseId is required and must be greater than 0");
        }

        Course course = courseRepository.findById(request.getCourseId())
                .orElseThrow(() -> new ResourceNotFoundException("Course not found with id: " + request.getCourseId()));

        if (course.getPrice() == null || course.getPrice() <= 0) {
            throw new BadRequestException("Course price must be greater than 0");
        }

        String orderNumber = "ORD-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();

        Order order = Order.builder()
                .orderNumber(orderNumber)
                .studentId(request.getStudentId())
                .courseId(request.getCourseId())
                .totalPrice(course.getPrice())
                .status("PENDING")
                .build();

        Order savedOrder = orderRepository.save(order);

        OrderDTO orderDTO = OrderDTO.builder()
                .id(savedOrder.getId())
                .orderNumber(savedOrder.getOrderNumber())
                .studentId(savedOrder.getStudentId())
                .courseId(savedOrder.getCourseId())
                .totalPrice(savedOrder.getTotalPrice())
                .status(savedOrder.getStatus())
                .build();

        return BaseResponse.success("Create order successfully", orderDTO);
    }
}
