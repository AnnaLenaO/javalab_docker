package com.example.javalab.entities;

import java.time.LocalDate;
import java.util.UUID;

public record Product(UUID id, String name, Category category, Double rating, LocalDate createdAt,
                      LocalDate updatedAt) {
}
