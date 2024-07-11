package com.bestseller.coffeestore.service;

import com.bestseller.coffeestore.controller.bean.OrderCreation;
import com.bestseller.coffeestore.dao.DrinksDAO;
import com.bestseller.coffeestore.dao.OrdersDAO;
import com.bestseller.coffeestore.dao.ToppingsDAO;
import com.bestseller.coffeestore.dto.DrinkDTO;
import com.bestseller.coffeestore.dto.OrderDTO;
import com.bestseller.coffeestore.dto.OrderItemDTO;
import com.bestseller.coffeestore.dto.ToppingDTO;
import com.bestseller.coffeestore.entity.OrderItems;
import com.bestseller.coffeestore.entity.Orders;
import lombok.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService{

    OrdersDAO ordersDAO;
    DrinksDAO drinksDAO;
    ToppingsDAO toppingsDAO;

    public OrderServiceImpl(OrdersDAO ordersDAO, DrinksDAO drinksDAO, ToppingsDAO toppingsDAO) {
        this.ordersDAO = ordersDAO;
        this.drinksDAO = drinksDAO;
        this.toppingsDAO = toppingsDAO;
    }

    @Override
    @Transactional
    public OrderDTO createOrder(@NonNull OrderCreation orderCreation) {

        Integer totalPrice = calculateOrderTotalPrice(orderCreation);

        // Transforming and preparing incoming order data into entity records and saving them in the DB.
        Orders order = new Orders();
        order.setUser_Id(orderCreation.getUserId());

        List<OrderItems> orderItems = orderCreation.getOrderItems().stream()
                .flatMap(orderItemDTO -> {
                    Long drinkId = orderItemDTO.getDrinkDTO().getDrinkId();
                    return orderItemDTO.getToppingDTOList().stream()
                            .map(toppingDTO -> new OrderItems(drinkId, toppingDTO.getToppingId()));
                })
                .peek(orderItem -> orderItem.setOrderId(order))
                .collect(Collectors.toList());

        order.setOrderItemList(orderItems);
        ordersDAO.save(order);

//      Creating response body ---------------------------------------------------

        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setId(order.getId());
        orderDTO.setUserId(order.getUserId());

        // need to fix the return values for orderitems!!!
        orderDTO.setOrderItemList(orderCreation.getOrderItems());

        return orderDTO;
    }

    private Integer calculateOrderTotalPrice(OrderCreation orderCreation) {
        // Maps for counting the drinks and toppings by their iDs
        Map<Long, Long> drinksMap = orderCreation.getOrderItems().stream()
                .collect(Collectors.groupingBy(drink -> drink.getDrinkDTO().getDrinkId(), Collectors.counting()));

        Map<Long, Long> toppingsMap = orderCreation.getOrderItems().stream()
                .flatMap(topping -> topping.getToppingDTOList().stream())
                .collect(Collectors.groupingBy(
                        ToppingDTO::getToppingId, Collectors.counting()
                ));

        // Collecting drinkIds and toppingIds for queries which retrieve the names and prices for the ordered drinks and toppings
        Set<Long> drinkIdSet = drinksMap.entrySet().stream().map(drinkId -> drinkId.getKey()).collect(Collectors.toSet());
        Set<Long> toppingIdSet = toppingsMap.entrySet().stream().map(toppingId -> toppingId.getKey()).collect(Collectors.toSet());

        // Retrieving the names and prices for the ordered drinks and toppings
        List<DrinkDTO> orderedDrinks = drinksDAO.getOrderedDrinks(drinkIdSet);
        List<ToppingDTO> orderedToppings = toppingsDAO.getOrderedToppings(toppingIdSet);

        Long numberOfDrinks = drinksMap.entrySet().stream().map(nrOfDrinks -> nrOfDrinks.getValue()).reduce(Long::sum).orElse(0L);

        // Assigning the prices of the drinks to the ordered drinks
        for (int i = 0; i < orderCreation.getOrderItems().size(); i++) {
            for (int j = 0; j < orderedDrinks.size(); j++) {
                if (orderCreation.getOrderItems().get(i).getDrinkDTO().getDrinkId().equals(orderedDrinks.get(j).getDrinkId())) {
                    orderCreation.getOrderItems().get(i).getDrinkDTO().setDrinkName(orderedDrinks.get(j).getDrinkName());
                    orderCreation.getOrderItems().get(i).getDrinkDTO().setPrice(orderedDrinks.get(j).getPrice());
                }
            }
        }

        for (int i = 0; i < orderCreation.getOrderItems().size(); i++) {
            for (int j = 0; j < orderCreation.getOrderItems().get(i).getToppingDTOList().size(); j++) {
                for (int k = 0; k < orderedToppings.size(); k++) {
                    if (orderCreation.getOrderItems().get(i).getToppingDTOList().get(j).getToppingId().equals(orderedToppings.get(k).getToppingId())) {
                        orderCreation.getOrderItems().get(i).getToppingDTOList().get(j).setToppingName(orderedToppings.get(k).getToppingName());
                        orderCreation.getOrderItems().get(i).getToppingDTOList().get(j).setPrice(orderedToppings.get(k).getPrice());
                    }
                }
            }
        }


        return null;
    }
}
