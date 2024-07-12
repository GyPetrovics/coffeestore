package com.bestseller.coffeestore.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CartOrderItemDTO {
    private DrinkDTO drinkDTO;
    private List<ToppingDTO> toppingDTOList;
}
