package com.metaphorce.shop_all.entities;

import com.metaphorce.shop_all.enums.PaymontMethod;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "sales")
public class Sale {

    @Id
    @SequenceGenerator(name = "sales_sequence", sequenceName = "sales_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sales_sequence")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", foreignKey = @ForeignKey(name = "fk_user_sale"))
    private User user;

    private Double amount;

    private Integer numberProducts;

    private LocalDateTime datePurchase;

    private PaymontMethod paymontMethod;
}
