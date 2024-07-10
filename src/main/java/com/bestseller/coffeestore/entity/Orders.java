package com.bestseller.coffeestore.entity;

import com.bestseller.coffeestore.dto.OrderItemDTO;
import jakarta.persistence.*;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "orders")
@NoArgsConstructor
public class Orders {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "user_id")
    private String userId;
    @OneToMany(mappedBy = "orderId", cascade = CascadeType.ALL)
    private List<OrderItems> orderItemList;

    public Long getId() {
        return id;
    }

    public String getUserId() {
        return userId;
    }

    public List<OrderItems> getOrderItemList() {
        return orderItemList;
    }

    public void setUser_Id(String userId) {
        this.userId = userId;
    }

    public void setOrderItemList(List<OrderItems> orderItemList) {
        this.orderItemList = orderItemList;
    }

    @Override
    public String toString() {
        return "Orders{" +
                "id=" + id +
                ", customerId=" + userId +
                '}';
    }
}
