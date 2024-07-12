package com.bestseller.coffeestore.mock;

import com.bestseller.coffeestore.dao.OrdersDAO;
import com.bestseller.coffeestore.dto.MostUsedToppingDTO;
import com.bestseller.coffeestore.entity.Orders;

import java.util.ArrayList;
import java.util.List;

public class MockOrdersDAO implements OrdersDAO {
    @Override
    public void save(Orders orders) {

    }

    @Override
    public List<MostUsedToppingDTO> getMostUsedToppings() {
        List<MostUsedToppingDTO> mostUsedToppingDTOList = new ArrayList<>();
        MostUsedToppingDTO mostUsedToppingDTO1 = new MostUsedToppingDTO(1L, "Milk", 112L);
        MostUsedToppingDTO mostUsedToppingDTO2 = new MostUsedToppingDTO(2L, "Hazelnut syrup", 87L);
        MostUsedToppingDTO mostUsedToppingDTO3 = new MostUsedToppingDTO(3L, "Lemon", 24L);
        mostUsedToppingDTOList.add(mostUsedToppingDTO1);
        mostUsedToppingDTOList.add(mostUsedToppingDTO2);
        mostUsedToppingDTOList.add(mostUsedToppingDTO3);
        return mostUsedToppingDTOList;
    }
}
