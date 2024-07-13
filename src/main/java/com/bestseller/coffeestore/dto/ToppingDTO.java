package com.bestseller.coffeestore.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ToppingDTO {
    private Long toppingId;
    private String toppingName;
    private Integer price;

    public ToppingDTO(Long toppingId) {
        this.toppingId = toppingId;
    }
}
