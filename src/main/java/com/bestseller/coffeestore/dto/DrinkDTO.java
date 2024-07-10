package com.bestseller.coffeestore.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DrinkDTO {
    private Long drinkId;
    private String drinkName;
    private Integer price;
}
