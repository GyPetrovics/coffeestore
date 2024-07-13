package com.bestseller.coffeestore.model;

import com.bestseller.coffeestore.dto.OrderItemDTO;
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
    private List<OrderItemDTO> orderItemDTOList;
    private Double originalPrice;
    private Double finalOrderPrice;
}
