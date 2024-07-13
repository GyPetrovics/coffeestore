package com.bestseller.coffeestore.controller.bean;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FinalizeOrder {
    private String userId;
    private Boolean orderFinalized;
}
