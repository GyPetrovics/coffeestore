package com.bestseller.coffeestore.entity;

import jakarta.persistence.*;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "cartorderitems")
@NoArgsConstructor
public class CartOrderItems {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cart_order_id", nullable = false)
    private Cart cartOrderId;
    @Column(name = "drink_id")
    private Long drinkId;
    @Column(name = "topping_id")
    private Long toppingId;
    @Column(name = "transaction_id")
    private Integer transactionId;

    public CartOrderItems(Long drinkId, Long toppingId) {
        this.drinkId = drinkId;
        this.toppingId = toppingId;
    }

    public Integer getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(Integer transactionId) {
        this.transactionId = transactionId;
    }

    public void setCartOrderId(Cart cartOrderId) {
        this.cartOrderId = cartOrderId;
    }

    public void setDrinkId(Long drinkId) {
        this.drinkId = drinkId;
    }

    public void setToppingId(Long toppingId) {
        this.toppingId = toppingId;
    }

    public Long getId() {
        return id;
    }

    public Cart getCartOrderId() {
        return cartOrderId;
    }

    public Long getDrinkId() {
        return drinkId;
    }

    public Long getToppingId() {
        return toppingId;
    }

    @Override
    public String toString() {
        return "CartOrderItems{" +
                "id=" + id +
                ", cartOrderId=" + cartOrderId +
                ", drinkId=" + drinkId +
                ", toppingId=" + toppingId +
                '}';
    }
}
