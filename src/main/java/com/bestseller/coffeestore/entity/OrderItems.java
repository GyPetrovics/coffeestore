package com.bestseller.coffeestore.entity;

import jakarta.persistence.*;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "orderitems")
@NoArgsConstructor
public class OrderItems {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", nullable = false)
    private Orders orderId;
    @Column(name = "drink_id")
    private Long drinkId;
    @Column(name = "topping_id")
    private Long toppingId;

    public OrderItems(Long drinkId, Long toppingId) {
        this.drinkId = drinkId;
        this.toppingId = toppingId;
    }

    public void setDrinkId(Long drinkId) {
        this.drinkId = drinkId;
    }

    public void setToppingId(Long toppingId) {
        this.toppingId = toppingId;
    }

    public void setOrderId(Orders orderId) {
        this.orderId = orderId;
    }

    public Long getId() {
        return id;
    }

    public Orders getOrderId() {
        return orderId;
    }

    public Long getDrinkId() {
        return drinkId;
    }

    public Long getToppingId() {
        return toppingId;
    }

    @Override
    public String toString() {
        return "OrderItems{" +
                "id=" + id +
                ", orderId=" + orderId +
                ", drinkId=" + drinkId +
                ", toppingId=" + toppingId +
                '}';
    }
}
