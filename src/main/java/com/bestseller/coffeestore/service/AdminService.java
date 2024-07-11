package com.bestseller.coffeestore.service;

import com.bestseller.coffeestore.controller.bean.DrinkCreation;
import com.bestseller.coffeestore.controller.bean.ToppingCreation;
import com.bestseller.coffeestore.dto.DrinkDTO;
import com.bestseller.coffeestore.dto.ToppingDTO;
import lombok.NonNull;

import java.util.List;

public interface AdminService {

    DrinkDTO createDrink(@NonNull DrinkCreation drinkCreation);
    Boolean deleteDrink(@NonNull Long drinkId);
    List<DrinkDTO> getAllDrinks();
    Boolean updateDrink(@NonNull Long drinkId, @NonNull DrinkCreation drinkCreation);

    ToppingDTO createTopping(@NonNull ToppingCreation toppingCreation);
    Boolean deleteTopping(@NonNull Long toppingId);
    List<ToppingDTO> getAllToppings();
    Boolean updateTopping(@NonNull Long toppingId, @NonNull ToppingCreation toppingCreation);

}
