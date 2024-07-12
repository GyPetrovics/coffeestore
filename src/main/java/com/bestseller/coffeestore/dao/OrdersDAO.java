package com.bestseller.coffeestore.dao;

import com.bestseller.coffeestore.dto.MostUsedToppingDTO;
import com.bestseller.coffeestore.dto.ToppingDTO;
import com.bestseller.coffeestore.entity.Orders;

import java.util.List;

public interface OrdersDAO {
    void save(Orders orders);
    List<MostUsedToppingDTO> getMostUsedToppings();
}
