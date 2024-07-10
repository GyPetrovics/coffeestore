package com.bestseller.coffeestore.service;

import com.bestseller.coffeestore.controller.bean.DrinkCreation;
import com.bestseller.coffeestore.dto.DrinkDTO;
import lombok.NonNull;

public interface AdminService {

    DrinkDTO createDrink(@NonNull DrinkCreation drinkCreation);

}
