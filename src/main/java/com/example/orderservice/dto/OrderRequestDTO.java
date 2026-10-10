package com.example.orderservice.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderRequestDTO {
    @NotNull(message = "studentId is required")
    @Positive(message = "studentId must be greater than 0")
    private Long studentId;

    @NotNull(message = "courseId is required")
    @Positive(message = "courseId must be greater than 0")
    private Long courseId;
}
