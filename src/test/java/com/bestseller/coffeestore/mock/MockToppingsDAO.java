package com.bestseller.coffeestore.mock;

import com.bestseller.coffeestore.dao.ToppingsDAO;
import com.bestseller.coffeestore.dto.DrinkDTO;
import com.bestseller.coffeestore.dto.ToppingDTO;
import com.bestseller.coffeestore.entity.Drink;
import com.bestseller.coffeestore.entity.Topping;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class MockToppingsDAO implements ToppingsDAO {
    @Override
    public void save(Topping topping) {

    }

    @Override
    public void delete(Topping topping) {

    }

    @Override
    public Topping findById(Long toppingId) {
        return new Topping("Milk", 2);
    }

    @Override
    public List<ToppingDTO> getAllToppings() {
        ToppingDTO toppingDTO1 = new ToppingDTO(1L, "Milk", 2);
        ToppingDTO toppingDTO2 = new ToppingDTO(2L, "Hazelnut syrup", 4);
        ToppingDTO toppingDTO3 = new ToppingDTO(3L, "Lemon", 3);
        List<ToppingDTO> toppingDTOList = new ArrayList<>();
        toppingDTOList.add(toppingDTO1);
        toppingDTOList.add(toppingDTO2);
        toppingDTOList.add(toppingDTO3);
        return toppingDTOList;
    }

    @Override
    public void update(Topping topping) {

    }

    @Override
    public List<ToppingDTO> getOrderedToppings(Set toppingIds) {
        return List.of();
    }
}
