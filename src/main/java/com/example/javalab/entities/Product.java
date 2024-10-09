//package com.example.javalab.products;

//public record Product(String name, double rating) {
//}

package com.example.javalab.entities;

import java.time.LocalDate;
import java.util.UUID;

public record Product(UUID id, String name, Category category, double rating, LocalDate createdAt,
                      LocalDate updatedAt) {
}
