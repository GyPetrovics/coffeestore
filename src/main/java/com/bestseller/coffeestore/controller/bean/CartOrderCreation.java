package com.bestseller.coffeestore.controller.bean;

import com.bestseller.coffeestore.dto.CartOrderItemDTO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class CartOrderCreation {
    private String userId;
    private List<CartOrderItemDTO> cartOrderItem;
}
