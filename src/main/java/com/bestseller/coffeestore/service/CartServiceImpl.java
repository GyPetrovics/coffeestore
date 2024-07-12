package com.bestseller.coffeestore.service;

import com.bestseller.coffeestore.controller.bean.CartOrderCreation;
import com.bestseller.coffeestore.dao.CartDAO;
import com.bestseller.coffeestore.dao.DrinksDAO;
import com.bestseller.coffeestore.dao.ToppingsDAO;
import com.bestseller.coffeestore.model.CartSummary;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
public class CartServiceImpl implements CartService {

    CartDAO cartDAO;
    DrinksDAO drinksDAO;
    ToppingsDAO toppingsDAO;

    public CartServiceImpl(CartDAO cartDAO, DrinksDAO drinksDAO, ToppingsDAO toppingsDAO) {
        this.cartDAO = cartDAO;
        this.drinksDAO = drinksDAO;
        this.toppingsDAO = toppingsDAO;
    }

    @Override
    @Transactional
    public CartSummary addCartOrderItem(@NonNull CartOrderCreation cartOrderCreation) {

        if (cartDAO.findByUserId(cartOrderCreation.getUserId()) == null) {
            // save cart
        } else {
            // check cart orderItem
            // if cart order item does not exist, then add it
            // if cart order item exists, then add one more cartOrderItem
        }

        // a parameter and/or endpoint is needed to trigger the finalization of the order
        // and then the Cart with it''s items should be removed based on the userId


        return null;
    }
}
