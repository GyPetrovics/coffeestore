package com.bestseller.coffeestore.service;

import com.bestseller.coffeestore.controller.bean.OrderCreation;
import com.bestseller.coffeestore.dao.DrinksDAO;
import com.bestseller.coffeestore.dao.OrdersDAO;
import com.bestseller.coffeestore.dao.ToppingsDAO;
import com.bestseller.coffeestore.dto.DrinkDTO;
import com.bestseller.coffeestore.dto.OrderItemDTO;
import com.bestseller.coffeestore.dto.ToppingDTO;
import com.bestseller.coffeestore.entity.OrderItems;
import com.bestseller.coffeestore.entity.Orders;
import com.bestseller.coffeestore.model.OrderSummary;
import lombok.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

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
    public OrderSummary createOrder(@NonNull OrderCreation orderCreation) {

        Map<String, Double> origAndFinalOrderPrice = calculateOrderTotalPrice(orderCreation);

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
        OrderSummary orderSummary = new OrderSummary();
        orderSummary.setId(order.getId());
        orderSummary.setUserId(order.getUserId());
        orderSummary.setOrderItemList(orderCreation.getOrderItems());
        orderSummary.setOriginalPrice(origAndFinalOrderPrice.get("OrigPrice"));
        orderSummary.setFinalOrderPrice(origAndFinalOrderPrice.get("FinalPrice"));

        return orderSummary;
    }

    private Map<String, Double> calculateOrderTotalPrice(OrderCreation orderCreation) {
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

        // Assigning the prices of the ordered drinks
        orderCreation.getOrderItems().forEach(orderItem -> {
            orderedDrinks.stream()
                    .filter(drink -> drink.getDrinkId().equals(orderItem.getDrinkDTO().getDrinkId()))
                    .findFirst()
                    .ifPresent(drink -> {
                        orderItem.getDrinkDTO().setDrinkName(drink.getDrinkName());
                        orderItem.getDrinkDTO().setPrice(drink.getPrice());
                    });
        });


        // Assigning the prices of the ordered toppings
        orderCreation.getOrderItems().forEach(orderItem -> {
            orderItem.getToppingDTOList().forEach(topping -> {
                orderedToppings.stream()
                        .filter(orderedTopping -> orderedTopping.getToppingId().equals(topping.getToppingId()))
                        .findFirst()
                        .ifPresent(orderedTopping -> {
                            topping.setToppingName(orderedTopping.getToppingName());
                            topping.setPrice(orderedTopping.getPrice());
                        });
            });
        });


        int fullPrice = orderCreation.getOrderItems().stream()
                .mapToInt(orderItem -> {
                    int drinkPrice = orderItem.getDrinkDTO().getPrice();
                    int toppingsPrice = orderItem.getToppingDTOList().stream()
                            .mapToInt(topping -> topping.getPrice())
                            .sum();
                    int totalPrice = drinkPrice + toppingsPrice;
                    return totalPrice;
                })
                .sum();

        Map<Integer, Integer> pricesByOrderitem = IntStream.range(0, orderCreation.getOrderItems().size())
                .boxed()
                .collect(Collectors.toMap(
                        i -> i,
                        actualOrderPrice -> {
                            OrderItemDTO orderItem = orderCreation.getOrderItems().get(actualOrderPrice);
                            int drinkPrice = orderItem.getDrinkDTO().getPrice();
                            int toppingsPrice = orderItem.getToppingDTOList().stream()
                                    .mapToInt(ToppingDTO::getPrice)
                                    .sum();
                            return drinkPrice + toppingsPrice;
                        }
                ));

        double finalPrice = 0.0;
        if (fullPrice > 12 && pricesByOrderitem.size() >= 3) {
            Map.Entry<Integer, Integer> integerIntegerEntry = pricesByOrderitem.entrySet().stream().min(Map.Entry.comparingByValue()).orElse(null);
            Integer value = integerIntegerEntry.getValue();
            finalPrice = fullPrice - value;
        } else if (fullPrice > 12) {
            finalPrice = fullPrice * 0.75;
        } else if (pricesByOrderitem.size() >= 3) {
            Map.Entry<Integer, Integer> integerIntegerEntry = pricesByOrderitem.entrySet().stream().min(Map.Entry.comparingByValue()).orElse(null);
            Integer value = integerIntegerEntry.getValue();
            finalPrice = fullPrice - value;
        } else {
            finalPrice = fullPrice;
        }

        Map<String, Double> origAndFinalPriceMap = new HashMap<>();
        origAndFinalPriceMap.put("OrigPrice", Double.valueOf(fullPrice));
        origAndFinalPriceMap.put("FinalPrice", finalPrice);

        return origAndFinalPriceMap;
    }
}
