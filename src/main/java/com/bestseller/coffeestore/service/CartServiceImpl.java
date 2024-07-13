package com.bestseller.coffeestore.service;

import com.bestseller.coffeestore.controller.bean.CartOrderCreation;
import com.bestseller.coffeestore.dao.CartDAO;
import com.bestseller.coffeestore.dao.CartOrderItemDAO;
import com.bestseller.coffeestore.dao.DrinksDAO;
import com.bestseller.coffeestore.dao.ToppingsDAO;
import com.bestseller.coffeestore.dto.ToppingDTO;
import com.bestseller.coffeestore.entity.Cart;
import com.bestseller.coffeestore.entity.CartOrderItems;
import com.bestseller.coffeestore.entity.OrderItems;
import com.bestseller.coffeestore.model.CartSummary;
import com.bestseller.coffeestore.model.OrderSummary;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

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

        // if there is no cart with the incoming userId, then save cart with its items
        Cart cartByUserId = cartDAO.findByUserId(cartOrderCreation.getUserId());
        if (cartByUserId == null || cartByUserId.getUserId().isEmpty() || cartByUserId.getUserId().isBlank()) {
            Cart cart = new Cart();
            cart.setUserId(cartOrderCreation.getUserId());

            Long drinkId = cartOrderCreation.getCartOrderItem().get(0).getDrinkDTO().getDrinkId();
            List<ToppingDTO> toppingDTOList = cartOrderCreation.getCartOrderItem().get(0).getToppingDTOList();
            List<CartOrderItems> cartOrderItems = toppingDTOList
                    .stream()
                    .map(toppingDTO -> new CartOrderItems(drinkId, toppingDTO.getToppingId()))
                    .peek(cartOrderItem -> cartOrderItem.setCartOrderId(cart))
                    .collect(Collectors.toList());

//            List<CartOrderItems> cartOrderItems = cartOrderCreation.getCartOrderItems().stream()
//                    .flatMap(cartOrderItemDTO -> {
//                        Long drinkId = cartOrderItemDTO.getDrinkDTO().getDrinkId();
//                        return cartOrderItemDTO.getToppingDTOList().stream()
//                                .map(toppingDTO -> new CartOrderItems(drinkId, toppingDTO.getToppingId()));
//                    })
//                    .peek(cartOrderItem -> cartOrderItem.setCartOrderId(cart))
//                    .collect(Collectors.toList());

            cart.setCartOrderItem(cartOrderItems);
            cartDAO.save(cart);

//      Creating response body ---------------------------------------------------

            CartSummary cartSummary = new CartSummary();
            cartSummary.setId(cart.getId());
            cartSummary.setUserId(cart.getUserId());
            cartSummary.setCartOrderItemDTO(cartOrderCreation.getCartOrderItem().get(0));
//            cartSummary.setOriginalPrice();
//            cartSummary.setFinalOrderPrice();

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
        }

        // a parameter and/or endpoint is needed to trigger the finalization of the order
        // and then the Cart with it''s items should be removed based on the userId


        return null;
    }


}
