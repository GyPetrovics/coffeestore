package com.bestseller.coffeestore.dao;

import com.bestseller.coffeestore.dto.DrinkDTO;
import com.bestseller.coffeestore.entity.Drink;

import java.util.List;
import java.util.Optional;

public interface DrinksDAO {
    void save(Drink drink);
    void delete(Drink drinkId);
    Drink findById(Long drinkId);
    List<DrinkDTO> getAllDrinks();
    void update(Drink drink);
}
