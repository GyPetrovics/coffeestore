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

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Slf4j
@Service
public class CartServiceImpl implements CartService {

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

        Map<String, Double> origAndFinalOrderPrice = calculateCartTotalPrice(orderCreation);

        // if there is no cart with the incoming userId, then save cart with its items
        Cart cartByUserId = cartDAO.findByUserId(cartOrderCreation.getUserId());
        if (cartByUserId.getUserId() == null) {
            Cart cart = new Cart();
            cart.setUserId(cartOrderCreation.getUserId());

            Long drinkId = cartOrderCreation.getCartOrderItem().get(0).getDrinkDTO().getDrinkId();
            List<ToppingDTO> toppingDTOList = cartOrderCreation.getCartOrderItem().get(0).getToppingDTOList();
            List<CartOrderItems> cartOrderItems = toppingDTOList
                    .stream()
                    .map(toppingDTO -> new CartOrderItems(drinkId, toppingDTO.getToppingId()))
                    .peek(cartOrderItem -> cartOrderItem.setCartOrderId(cart))
                    .collect(Collectors.toList());

            cart.setCartOrderItem(cartOrderItems);
            cartDAO.save(cart);

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
            cartSummary.setOriginalPrice(origAndFinalOrderPrice.get("OrigPrice"));
            cartSummary.setFinalOrderPrice(origAndFinalOrderPrice.get("FinalPrice"));

            log.info("A new item has been added to the cart");
            return cartSummary;
        } else {
            // if there is already a cart with the incoming userId, then just save the orderItems to the same cart id
            String userId = cartOrderCreation.getUserId();
            Cart cart = cartDAO.findByUserId(userId);

            Long drinkId = cartOrderCreation.getCartOrderItem().get(0).getDrinkDTO().getDrinkId();
            List<ToppingDTO> toppingDTOList = cartOrderCreation.getCartOrderItem().get(0).getToppingDTOList();
            List<CartOrderItems> cartOrderItems = toppingDTOList
                    .stream()
                    .map(toppingDTO -> new CartOrderItems(drinkId, toppingDTO.getToppingId()))
                    .peek(cartOrderItem -> cartOrderItem.setCartOrderId(cart))
                    .collect(Collectors.toList());

            for (CartOrderItems actCartOrderItem : cartOrderItems) {
                cartOrderItemDAO.save(actCartOrderItem);
            }

            // Create and return response body ---------------------------------------------------


        }

        return null;
    }

    public Map<String, Double> calculateCartTotalPrice(OrderCreation orderCreation) {
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

        // Counting the number of the drinks for discount calculation
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


        // Calculationg order full price
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

        // Collecting prices by Order items for discount calculation
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

        // Calculation final price considering promotions
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

        // returning original full price and discounted price
        Map<String, Double> origAndFinalPriceMap = new HashMap<>();
        origAndFinalPriceMap.put("OrigPrice", Double.valueOf(fullPrice));
        origAndFinalPriceMap.put("FinalPrice", finalPrice);

        log.info("Final order price has been calculated... ");
        return origAndFinalPriceMap;
    }


}
