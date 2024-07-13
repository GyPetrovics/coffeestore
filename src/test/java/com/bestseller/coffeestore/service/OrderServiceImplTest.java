package com.bestseller.coffeestore.service;

import com.bestseller.coffeestore.controller.bean.OrderCreation;
import com.bestseller.coffeestore.dto.DrinkDTO;
import com.bestseller.coffeestore.dto.OrderItemDTO;
import com.bestseller.coffeestore.dto.ToppingDTO;
import com.bestseller.coffeestore.entity.Cart;
import com.bestseller.coffeestore.mock.MockCartDAO;
import com.bestseller.coffeestore.mock.MockDrinksDAO;
import com.bestseller.coffeestore.mock.MockOrdersDAO;
import com.bestseller.coffeestore.mock.MockToppingsDAO;
import com.bestseller.coffeestore.model.OrderSummary;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class OrderServiceImplTest {

    private final OrderServiceImpl testedOrderServiceImpl = new OrderServiceImpl(
            new MockOrdersDAO(),
            new MockDrinksDAO(),
            new MockToppingsDAO(),
            new MockCartDAO()
    );

    @Test
    void testCreateOrder() {
        OrderCreation orderCreation = new OrderCreation();
        DrinkDTO drinkDTO = new DrinkDTO(1L, "Coffee", 5);
        ToppingDTO toppingDTO1 = new ToppingDTO(1L, "Milk", 2);
        ToppingDTO toppingDTO2 = new ToppingDTO(2L, "Lemon", 2);
        List<ToppingDTO> toppingDTOList = new ArrayList<>();
        toppingDTOList.add(toppingDTO1);
        toppingDTOList.add(toppingDTO2);

        DrinkDTO drinkDTO2 = new DrinkDTO(2L, "Tea", 2);
        ToppingDTO toppingDTO3 = new ToppingDTO(3L, "Hazelnut syrup", 5);
        ToppingDTO toppingDTO4 = new ToppingDTO(4L, "Chocolate sauce", 6);
        List<ToppingDTO> toppingDTOList2 = new ArrayList<>();
        toppingDTOList.add(toppingDTO3);
        toppingDTOList.add(toppingDTO4);

        OrderItemDTO orderItemDTO1 = new OrderItemDTO(drinkDTO, toppingDTOList);
        OrderItemDTO orderItemDTO2 = new OrderItemDTO(drinkDTO2, toppingDTOList2);

        List<OrderItemDTO> orderItemDTOList = new ArrayList<>();
        orderItemDTOList.add(orderItemDTO1);
        orderItemDTOList.add(orderItemDTO2);
        orderCreation.setUserId("user1");
        orderCreation.setOrderItems(orderItemDTOList);

        Cart cart = new Cart();

        Assertions.assertNotNull(testedOrderServiceImpl.createOrder(orderCreation, cart));
    }
}