package com.bestseller.coffeestore.model;

import com.bestseller.coffeestore.dto.DrinkDTO;
import com.bestseller.coffeestore.dto.ToppingDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Order {
    private Long orderId;
    private Long id_customer;
    private DrinkDTO drinkDTO;
    private List<ToppingDTO> toppingDTOList;

}
