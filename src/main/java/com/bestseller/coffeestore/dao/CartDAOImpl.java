package com.bestseller.coffeestore.dao;

import com.bestseller.coffeestore.entity.Cart;
import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Repository;

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
        return entityManager.find(Cart.class, userId);
    }


}
