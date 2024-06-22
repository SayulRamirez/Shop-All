package com.metaphorce.shop_all.entities;

import jakarta.persistence.*;
import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
public class User {

    @SequenceGenerator(name = "user_sequence", sequenceName = "user_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_sequence")
    @Id
    private Long id;

    private String name;

    @Column(unique = true)
    private String email;

    private boolean active;

    @OneToOne(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    @JoinColumn(name = "cart_id", foreignKey = @ForeignKey(name = "fk_cart_user"))
    private Cart cart;

    @PrePersist
    public void prePersist() {
        this.active = true;
    }
}
