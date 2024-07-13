package com.bestseller.coffeestore.entity;

import jakarta.persistence.*;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "cart")
@NoArgsConstructor
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "user_id")
    private String userId;
    @OneToMany(mappedBy = "cartOrderId", cascade = CascadeType.ALL)
    private List<CartOrderItems> cartOrderItem;

    public Long getId() {
        return id;
    }

    public String getUserId() {
        return userId;
    }

    public List<CartOrderItems> getCartOrderItem() {
        return cartOrderItem;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setCartOrderItem(List<CartOrderItems> cartOrderItem) {
        this.cartOrderItem = cartOrderItem;
    }

    @Override
    public String toString() {
        return "Cart{" +
                "id=" + id +
                ", userId='" + userId + '\'' +
                '}';
    }
}
