package com.bestseller.coffeestore.dao;

import com.bestseller.coffeestore.dto.DrinkDTO;
import com.bestseller.coffeestore.dto.ToppingDTO;
import com.bestseller.coffeestore.entity.Topping;

import java.util.List;
import java.util.Set;

public interface ToppingsDAO {
    void save(Topping topping);
    void delete(Topping topping);
    Topping findById(Long toppingId);
    List<ToppingDTO> getAllToppings();
    void update(Topping topping);
    List<ToppingDTO> getOrderedToppings(Set toppingIds);
}
