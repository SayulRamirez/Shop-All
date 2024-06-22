package com.metaphorce.shop_all.entities;

import jakarta.persistence.*;
import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "carts")
public class Cart {

    @Id
    @SequenceGenerator(name = "cart_sequence", sequenceName = "cart_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "cart_sequence")
    private Long id;

    private Integer numberProducts;

    private Double amount;

    @PrePersist
    public void prePersist() {
        numberProducts = 0;
        amount = 0.0;
    }
}