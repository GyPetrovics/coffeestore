package com.bestseller.coffeestore.service;

import com.bestseller.coffeestore.controller.bean.OrderCreation;
import com.bestseller.coffeestore.dao.DrinksDAO;
import com.bestseller.coffeestore.dao.OrdersDAO;
import com.bestseller.coffeestore.dao.ToppingsDAO;
import com.bestseller.coffeestore.dao.bean.DrinkPrices;
import com.bestseller.coffeestore.dto.DrinkDTO;
import com.bestseller.coffeestore.dto.OrderDTO;
import com.bestseller.coffeestore.dto.ToppingDTO;
import com.bestseller.coffeestore.entity.OrderItems;
import com.bestseller.coffeestore.entity.Orders;
import lombok.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Set;
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

        // Retrieving all drinks and toppings for discount calculations
//        List<DrinkDTO> allDrinks = drinksDAO.getAllDrinks();
//        List<ToppingDTO> allToppings = toppingsDAO.getAllToppings();

        Integer totalPrice = calculateOrderTotalPrice(orderCreation);


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

        Set<Long> drinkIdSet = drinksMap.entrySet().stream().map(drinkId -> drinkId.getKey()).collect(Collectors.toSet());
        Set<Long> toppingIdSet = toppingsMap.entrySet().stream().map(toppingId -> toppingId.getKey()).collect(Collectors.toSet());

        // Retrieving the prices from the DB for the ordered drinks (DrinkPrices also includes the names...)
        List<DrinkPrices> drinkPrices = drinksDAO.getDrinkPrices(drinkIdSet);

        return null;
    }
}
