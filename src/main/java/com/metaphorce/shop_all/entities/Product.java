package com.metaphorce.shop_all.entities;

import com.metaphorce.shop_all.enums.Category;
import jakarta.persistence.*;
import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String description;

    private String code;

    @Enumerated(EnumType.STRING)
    private Category category;

    private Integer stock;

    private Double price;
}
