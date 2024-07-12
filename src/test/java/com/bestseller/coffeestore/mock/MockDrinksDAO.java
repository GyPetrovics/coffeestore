package com.bestseller.coffeestore.mock;

import com.bestseller.coffeestore.dao.DrinksDAO;
import com.bestseller.coffeestore.dto.DrinkDTO;
import com.bestseller.coffeestore.entity.Drink;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class MockDrinksDAO implements DrinksDAO {
    @Override
    public void save(Drink drink) {

    }

    @Override
    public void delete(Drink drinkId) {

    }

    @Override
    public Drink findById(Long drinkId) {
        return new Drink("Coffee", 4);
    }

    @Override
    public List<DrinkDTO> getAllDrinks() {
        DrinkDTO drinkDTO1 = new DrinkDTO(1L, "Coffee", 5);
        DrinkDTO drinkDTO2 = new DrinkDTO(2L, "Latte", 6);
        DrinkDTO drinkDTO3 = new DrinkDTO(3L, "Tea", 2);
        List<DrinkDTO> drinkDTOList = new ArrayList<>();
        drinkDTOList.add(drinkDTO1);
        drinkDTOList.add(drinkDTO2);
        drinkDTOList.add(drinkDTO3);
        return drinkDTOList;
    }

    @Override
    public void update(Drink drink) {

    }

    @Override
    public List<DrinkDTO> getOrderedDrinks(Set drinkIds) {
        return List.of();
    }
}
