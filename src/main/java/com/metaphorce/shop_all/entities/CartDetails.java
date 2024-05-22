package com.metaphorce.shop_all.entities;

import jakarta.persistence.*;
import lombok.*;


@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "cart_details")
public class CartDetails {

    @Id
    @SequenceGenerator(name = "cart_details_sequence", sequenceName = "cart_details_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "cart_details_sequence")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "cart_id", foreignKey = @ForeignKey(name = "fk_cart_details_cart"))
    private Cart cart;

    @ManyToOne
    @JoinColumn(name = "product_id", foreignKey = @ForeignKey(name = "fk_cart_details_product"))
    private Product product;

    private Integer numberPieces;

    private Double amount;
}
