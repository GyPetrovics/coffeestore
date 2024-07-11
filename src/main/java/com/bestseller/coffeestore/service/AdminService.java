package com.bestseller.coffeestore.service;

import com.bestseller.coffeestore.controller.bean.DrinkCreation;
import com.bestseller.coffeestore.dto.DrinkDTO;
import lombok.NonNull;

import java.util.List;

public interface AdminService {

    DrinkDTO createDrink(@NonNull DrinkCreation drinkCreation);

    Boolean deleteDrink(@NonNull Long drinkId);

    List<DrinkDTO> getAllDrinks();

    Boolean updateDrink(@NonNull Long drinkId, @NonNull DrinkCreation drinkCreation);

}
