package com.bestseller.coffeestore.service;

import com.bestseller.coffeestore.controller.bean.OrderCreation;
import com.bestseller.coffeestore.dao.OrdersDAO;
import com.bestseller.coffeestore.dto.DrinkDTO;
import com.bestseller.coffeestore.dto.OrderItemDTO;
import com.bestseller.coffeestore.entity.OrderItems;
import com.bestseller.coffeestore.entity.Orders;
import com.bestseller.coffeestore.model.Order;
import lombok.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService{

    OrdersDAO ordersDAO;

    public OrderServiceImpl(OrdersDAO ordersDAO) {
        this.ordersDAO = ordersDAO;
    }

    @Override
    @Transactional
    public Order createOrder(@NonNull OrderCreation orderCreation) {

        Orders order = new Orders();
        order.setUser_Id(orderCreation.getUserId());

        List<OrderItems> orderItems = orderCreation.getOrderItems().stream()
                .flatMap(orderItemDTO -> {
                    Integer drinkId = orderItemDTO.getDrinkDTO().getDrinkId();
                    return orderItemDTO.getToppingDTOList().stream()
                            .map(toppingDTO -> new OrderItems(drinkId, toppingDTO.getToppingId()));
                })
                .peek(orderItem -> orderItem.setOrderId(order))
                .collect(Collectors.toList());

        order.setOrderItemList(orderItems);


        ordersDAO.save(order);

        // Loop through the ordersItems and the

//        OrderDrink orderDrink = new OrderDrink(orderCreation.getId_customer(), orderCreation.getDrinkDTO().getDrinkId());
//
//        OrderDrink orderDrinkSaved = orderDrinkDAO.save(orderDrink);
//
//        if (orderCreation.getToppingDTOList() != null) {
//            for (ToppingDTO toppingDTO : orderCreation.getToppingDTOList()) {
//                OrderTopping orderTopping = new OrderTopping();
//                orderTopping.setId_order_drink(orderCreation.getOrderId());
//                orderTopping.setId_topping(toppingDTO.getToppingId());
//
//                orderToppingDAO.save(orderTopping);
//            }
//        }
//
//        Order order = new Order();
//        order.setOrderId(orderDrinkSaved.getId());
//        order.setId_customer(orderDrinkSaved.getId_customer());
//        // collect and set the rest of the data like DrinkDTO values + ToppingDTO list values



        return null;
    }
}
