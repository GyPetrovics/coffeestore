package com.bestseller.coffeestore.dao;

import com.bestseller.coffeestore.controller.bean.CartOrderCreation;
import com.bestseller.coffeestore.entity.Cart;
import com.bestseller.coffeestore.entity.CartOrderItems;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public class CartDAOImpl implements CartDAO{

    private final EntityManager entityManager;

    public CartDAOImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public void save(Cart cart) {
        entityManager.persist(cart);
    }

    @Override
    public Cart findByUserId(String userId) {

        TypedQuery<Cart> findCartByUserIdQuery = entityManager.createQuery("SELECT c FROM Cart c" +
                " WHERE c.userId = :userId", Cart.class);
        findCartByUserIdQuery.setParameter("userId", userId);

        Cart retrievedCart = findCartByUserIdQuery.getResultList().stream().findFirst().orElse(new Cart());

        return retrievedCart;
    }

    @Override
    public Boolean cartContainsOrderItem(String userId, CartOrderCreation cartOrderCreation) {

        TypedQuery<CartOrderItems> retrieveCartOrderItemsQuery = entityManager.createQuery(
                "SELECT coi FROM CartOrderItems coi JOIN coi.cart c" +
                        " WHERE c.userId = :userId", CartOrderItems.class);
        retrieveCartOrderItemsQuery.setParameter("userId", userId);

        List<CartOrderItems> resultList = retrieveCartOrderItemsQuery.getResultList();



        return null;
    }

    @Override
    @Transactional
    public void clearUserCart(Cart cart) {
        entityManager.remove(cart);
    }

    @Override
    public List<CartOrderItems> getAllCartOrderItems() {
        TypedQuery<CartOrderItems> getAllCartOrderItems = entityManager
                .createQuery("SELECT coi FROM CartOrderItems coi", CartOrderItems.class);
        List<CartOrderItems> resultList = getAllCartOrderItems.getResultList();
        return resultList;
    }
}
