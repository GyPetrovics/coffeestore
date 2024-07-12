package com.bestseller.coffeestore.model;

import com.bestseller.coffeestore.dto.CartOrderItemDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CartSummary {
    private Long id;
    private String userId;
    private List<CartOrderItemDTO> cartOrderItemDTOList;
    private Double originalPrice;
    private Double finalOrderPrice;
}
