package com.example.springrecap.model;

import com.example.springrecap.todoEnum.Status;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("Todos")
public record Todo (String id, String description, Status status) {
}
