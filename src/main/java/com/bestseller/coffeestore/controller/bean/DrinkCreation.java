package com.bestseller.coffeestore.controller.bean;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DrinkCreation {
    private Long id;
    private String name;
    private Integer price;
    private String userId;
}
