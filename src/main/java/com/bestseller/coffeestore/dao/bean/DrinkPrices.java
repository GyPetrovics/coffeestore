package com.bestseller.coffeestore.dao.bean;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DrinkPrices {
    private Long drinkId;
    private String drinkName;
    private Integer drinkPrice;
}
