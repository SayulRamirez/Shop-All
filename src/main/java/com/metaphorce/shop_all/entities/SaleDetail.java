package com.metaphorce.shop_all.entities;

import jakarta.persistence.*;
import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "sales_details")
public class SaleDetail {

    @Id
    @SequenceGenerator(name = "sale_details_sequence", sequenceName = "sale_details_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sale_details_sequence")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "sale_id", foreignKey = @ForeignKey(name = "fk_sale_details_sale"))
    private Sale sale;

    @ManyToOne
    @JoinColumn(name = "product_id", foreignKey = @ForeignKey(name = "fk_sale_details_product"))
    private Product product;

    private Integer numberProducts;

    private Double amount;



}
