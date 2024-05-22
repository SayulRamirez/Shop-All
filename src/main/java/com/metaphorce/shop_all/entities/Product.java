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
    @SequenceGenerator(name = "products_sequence", sequenceName = "products_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "products_sequence")
    private Long id;

    private String description;

    private String code;

    private Category category;

    private Integer stock;

    private Double price;
}
