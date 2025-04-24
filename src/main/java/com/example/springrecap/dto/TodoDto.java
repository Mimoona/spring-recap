package com.example.springrecap.dto;

import com.example.springrecap.todoEnum.Status;
import lombok.AllArgsConstructor;
import lombok.Data;

public record TodoDto(String description, Status status) {
}
