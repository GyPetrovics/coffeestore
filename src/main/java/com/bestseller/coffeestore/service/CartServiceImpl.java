package com.bestseller.coffeestore.service;

import com.bestseller.coffeestore.controller.bean.CartOrderCreation;
import com.bestseller.coffeestore.controller.bean.OrderCreation;
import com.bestseller.coffeestore.dao.CartDAO;
import com.bestseller.coffeestore.dao.CartOrderItemDAO;
import com.bestseller.coffeestore.dao.DrinksDAO;
import com.bestseller.coffeestore.dao.ToppingsDAO;
import com.bestseller.coffeestore.dto.CartOrderItemDTO;
import com.bestseller.coffeestore.dto.DrinkDTO;
import com.bestseller.coffeestore.dto.OrderItemDTO;
import com.bestseller.coffeestore.dto.ToppingDTO;
import com.bestseller.coffeestore.entity.Cart;
import com.bestseller.coffeestore.entity.CartOrderItems;
import com.bestseller.coffeestore.model.CartSummary;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
public class CartServiceImpl implements CartService {

    private double originalSumPrice = 0.0;
    private double finalSumPrice = 0.0;

    CartDAO cartDAO;
    DrinksDAO drinksDAO;
    ToppingsDAO toppingsDAO;
    CartOrderItemDAO cartOrderItemDAO;

    public CartServiceImpl(CartDAO cartDAO, DrinksDAO drinksDAO, ToppingsDAO toppingsDAO, CartOrderItemDAO cartOrderItemDAO) {
        this.cartDAO = cartDAO;
        this.drinksDAO = drinksDAO;
        this.toppingsDAO = toppingsDAO;
        this.cartOrderItemDAO = cartOrderItemDAO;
    }

    @Override
    @Transactional
    public CartSummary addCartOrderItem(@NonNull CartOrderCreation cartOrderCreation) {

        // Transforming cartOrderCreation into orderCreation to get the total prices for the response,
        // using -> orderService.calculateOrderTotalPrice(orderCreation)
        OrderCreation orderCreation = new OrderCreation();
        orderCreation.setUserId(cartOrderCreation.getUserId());
        List<CartOrderItemDTO> incomingCartOrderItems = cartOrderCreation.getCartOrderItem();

        List<OrderItemDTO> orderItemDTOList = incomingCartOrderItems.stream().map(incomingCartOrderItem -> {
            OrderItemDTO orderItemDTO = new OrderItemDTO();
            orderItemDTO.setToppingDTOList(incomingCartOrderItem.getToppingDTOList());
            orderItemDTO.setDrinkDTO(incomingCartOrderItem.getDrinkDTO());
            return orderItemDTO;
        }).collect(Collectors.toList());

        orderCreation.setOrderItems(orderItemDTOList);

        // if there is no cart with the incoming userId, then save cart with its items
        Cart cartByUserId = cartDAO.findByUserId(cartOrderCreation.getUserId());
        int transactionId = 1;
        if (cartByUserId.getUserId() == null) {

            Cart cart = new Cart();
            cart.setUserId(cartOrderCreation.getUserId());

            Long drinkId = cartOrderCreation.getCartOrderItem().get(0).getDrinkDTO().getDrinkId();
            List<ToppingDTO> toppingDTOList = cartOrderCreation.getCartOrderItem().get(0).getToppingDTOList();
            List<CartOrderItems> cartOrderItems = toppingDTOList
                    .stream()
                    .map(toppingDTO -> new CartOrderItems(drinkId, toppingDTO.getToppingId()))
                    .peek(cartOrderItem -> cartOrderItem.setCartOrderId(cart))
                    .peek(cartOrderItem -> cartOrderItem.setTransactionId(transactionId))
                    .collect(Collectors.toList());

            cart.setCartOrderItem(cartOrderItems);
            cartDAO.save(cart);

            orderCreation.getOrderItems().stream()
                    .forEach(orderItem -> orderItem.setTransactionId(cart.getCartOrderItems().get(0).getTransactionId()));

            Map<String, Double> origAndFinalOrderPrice = calculateCartTotalPrice(orderCreation);

//      Creating response body ---------------------------------------------------

            CartSummary cartSummary = new CartSummary();
            cartSummary.setId(cart.getId());
            cartSummary.setUserId(cart.getUserId());

            List<CartOrderItems> cartOrderItemsList = cart.getCartOrderItems();

            List<CartOrderItemDTO> cartOrderItemDTOList = cartOrderItemsList.stream()
                    .map(cartOrderItem -> {
                        DrinkDTO drinkDTO = new DrinkDTO();
                        drinkDTO.setDrinkId(cartOrderItem.getDrinkId());
                        List<ToppingDTO> toppingDTO_List = List.of(new ToppingDTO(cartOrderItem.getToppingId()));
                        CartOrderItemDTO cartOrderItemDTO = new CartOrderItemDTO();
                        cartOrderItemDTO.setDrinkDTO(drinkDTO);
                        cartOrderItemDTO.setToppingDTOList(toppingDTO_List);
                        return cartOrderItemDTO;
                    })
                    .collect(Collectors.toList());

            cartSummary.setOrderItemDTOList(orderCreation.getOrderItems());
            originalSumPrice = origAndFinalOrderPrice.get("OrigPrice");
            finalSumPrice = origAndFinalOrderPrice.get("FinalPrice");
            cartSummary.setOriginalPrice(originalSumPrice);
            cartSummary.setFinalOrderPrice(finalSumPrice);

            log.info("A cart has been created and a new item has been added to the cart");
            return cartSummary;
        } else {
            // if there is already a cart with the incoming userId, then just save the orderItems to the same cart id
//            Map<String, Double> origAndFinalOrderPrice = calculateCartTotalPrice(orderCreation);
            // Transforming cartOrderCreation into orderCreation to get the total prices for the response,
            // using -> orderService.calculateOrderTotalPrice(orderCreation)
            orderCreation.setUserId(cartOrderCreation.getUserId());

            // retrieveing all existing CartOrderItems for calculation purposes
            List<CartOrderItems> allCartOrderItems = cartDAO.getAllCartOrderItems();
            List<OrderItemDTO> allCartOrderItemsList = allCartOrderItems.stream().map(orderItem -> {
                OrderItemDTO orderItemDTO = new OrderItemDTO();
                orderItemDTO.setTransactionId(orderItem.getTransactionId());
                DrinkDTO drinkDTO = new DrinkDTO();
                drinkDTO.setDrinkId(orderItem.getDrinkId());
                orderItemDTO.setDrinkDTO(drinkDTO);
                List<ToppingDTO> toppingDTO_List = List.of(new ToppingDTO(orderItem.getToppingId()));
                orderItemDTO.setToppingDTOList(toppingDTO_List);
                return orderItemDTO;
            }).collect(Collectors.toList());

            for (OrderItemDTO orderItemDTO : allCartOrderItemsList) {
                orderCreation.getOrderItems().add(orderItemDTO);
            }
            orderCreation.setOrderItems(allCartOrderItemsList);



            String userId = cartOrderCreation.getUserId();
            Cart cart = cartDAO.findByUserId(userId);



            Long drinkId = cartOrderCreation.getCartOrderItem().get(0).getDrinkDTO().getDrinkId();
            List<ToppingDTO> toppingDTOList = cartOrderCreation.getCartOrderItem().get(0).getToppingDTOList();

            // Getting the max of the transaction id
            int maxOfTransactionId = cart.getCartOrderItems().stream().mapToInt(CartOrderItems::getTransactionId).max().orElse(0);

            List<CartOrderItems> cartOrderItems = toppingDTOList
                    .stream()
                    .map(toppingDTO -> new CartOrderItems(drinkId, toppingDTO.getToppingId()))
                    .peek(cartOrderItem -> cartOrderItem.setCartOrderId(cart))
                    .peek(cartOrderItem -> {
                        cartOrderItem.setTransactionId(maxOfTransactionId + 1);
                    } )
                    .collect(Collectors.toList());

            for (CartOrderItems actCartOrderItem : cartOrderItems) {
                cartOrderItemDAO.save(actCartOrderItem);
            }

            int maxOfTransId = cart.getCartOrderItems().stream().mapToInt(CartOrderItems::getTransactionId).max().orElse(0);
            orderCreation.getOrderItems().stream()
                    .forEach(orderItem -> orderItem.setTransactionId(maxOfTransId + 1));


//            // retrieveing all existing CartOrderItems for calculation purposes
//            List<CartOrderItems> allCartOrderItems = cartDAO.getAllCartOrderItems();
//            List<OrderItemDTO> allCartOrderItemsList = allCartOrderItems.stream().map(orderItem -> {
//                OrderItemDTO orderItemDTO = new OrderItemDTO();
//                orderItemDTO.setTransactionId(orderItem.getTransactionId());
//                DrinkDTO drinkDTO = new DrinkDTO();
//                drinkDTO.setDrinkId(orderItem.getDrinkId());
//                orderItemDTO.setDrinkDTO(drinkDTO);
//                List<ToppingDTO> toppingDTO_List = List.of(new ToppingDTO(orderItem.getToppingId()));
//                orderItemDTO.setToppingDTOList(toppingDTO_List);
//                return orderItemDTO;
//            }).collect(Collectors.toList());
//
//            orderCreation.setOrderItems(allCartOrderItemsList);


            Map<String, Double> origAndFinalOrderPrice = calculateCartTotalPrice(orderCreation);


            // Create and return response body ---------------------------------------------------

            CartSummary cartSummary = new CartSummary();
            cartSummary.setId(cart.getId());
            cartSummary.setUserId(cart.getUserId());

            originalSumPrice = originalSumPrice + origAndFinalOrderPrice.get("OrigPrice");
            finalSumPrice = finalSumPrice + origAndFinalOrderPrice.get("FinalPrice");
            cartSummary.setOrderItemDTOList(orderCreation.getOrderItems());
            cartSummary.setOriginalPrice(originalSumPrice);
            cartSummary.setFinalOrderPrice(finalSumPrice);

            log.info("A new item has been added to the cart");
            return cartSummary;
        }
    }

    public Map<String, Double> calculateCartTotalPrice(OrderCreation orderCreation) {

        // Map of Drinks
        Map<Long, Long> drinksMap = orderCreation.getOrderItems().stream()
                .collect(Collectors.groupingBy(drink -> drink.getDrinkDTO().getDrinkId(), Collectors.counting()));

        // Map for collecting the topping by their id
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

        Map<Integer, List<OrderItemDTO>> drinks = orderCreation.getOrderItems().stream().collect(Collectors.groupingBy(OrderItemDTO::getTransactionId));
        drinks.entrySet().stream().mapToInt(drink -> drink.getValue().stream().mapToInt(order -> {
            int drinkPrice = order.getDrinkDTO().getPrice();
            int toppingPrice = order.getToppingDTOList().stream().mapToInt(topping -> topping.getPrice()).sum();
            int totalPrice = drinkPrice + toppingPrice;
            return totalPrice;
        }).sum());

        // Calculation prices by each transaction
        Map<Integer, Integer> transactionPrices = orderCreation.getOrderItems().stream()
                .collect(Collectors.groupingBy(
                        OrderItemDTO::getTransactionId,
                        Collectors.collectingAndThen(
                                Collectors.toList(),
                                items -> {
                                    Set<Long> uniqueDrinkIds = new HashSet<>();
                                    Set<Long> uniqueToppingIds = new HashSet<>();
                                    return items.stream().mapToInt(item -> {
                                        int drinkPrice = 0;
                                        if (uniqueDrinkIds.add(item.getDrinkDTO().getDrinkId())) {
                                            drinkPrice = item.getDrinkDTO().getPrice();
                                        }
                                        int toppingPrice = item.getToppingDTOList().stream()
                                                .filter(topping -> uniqueToppingIds.add(topping.getToppingId()))
                                                .mapToInt(ToppingDTO::getPrice)
                                                .sum();
                                        return drinkPrice + toppingPrice;
                                    }).sum();
                                }
                        )
                ));

        int fullPrice = transactionPrices.values().stream().mapToInt(Integer::intValue).sum();

        int maxOfTransactionId = orderCreation.getOrderItems().stream().mapToInt(OrderItemDTO::getTransactionId).max().orElse(0);

        // Calculation final price considering promotions
        double finalPrice = 0.0;
        if (fullPrice > 12 && transactionPrices.size() >= 3) {
            Map.Entry<Integer, Integer> integerIntegerEntry = transactionPrices.entrySet().stream().min(Map.Entry.comparingByValue()).orElse(null);
            Integer value = integerIntegerEntry.getValue();
            finalPrice = fullPrice - value;
        } else if (fullPrice > 12) {
            finalPrice = fullPrice * 0.75;
        } else if (transactionPrices.size() >= 3) {
            Map.Entry<Integer, Integer> integerIntegerEntry = transactionPrices.entrySet().stream().min(Map.Entry.comparingByValue()).orElse(null);
            Integer value = integerIntegerEntry.getValue();
            finalPrice = fullPrice - value;
        } else {
            finalPrice = fullPrice;
        }

        // returning original full price and discounted price
        Map<String, Double> origAndFinalPriceMap = new HashMap<>();
        origAndFinalPriceMap.put("OrigPrice", Double.valueOf(fullPrice));
        origAndFinalPriceMap.put("FinalPrice", finalPrice);

        log.info("Final order price has been calculated... ");
        return origAndFinalPriceMap;
    }


}
