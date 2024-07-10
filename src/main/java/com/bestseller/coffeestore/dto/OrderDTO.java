package com.bestseller.coffeestore.dto;

import com.bestseller.coffeestore.entity.OrderItems;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderDTO {
    private Long id;
    private String userId;
    private List<OrderItemDTO> orderItemList;
}
