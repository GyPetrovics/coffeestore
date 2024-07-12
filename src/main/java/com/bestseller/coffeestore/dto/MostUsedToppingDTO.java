package com.bestseller.coffeestore.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MostUsedToppingDTO {
    private Long id;
    private String name;
    private Long mostUsedToppingId;
}
