package com.bestseller.coffeestore.dao;

import com.bestseller.coffeestore.entity.CartOrderItems;
import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Repository;

@Repository
public class CartOrderItemDAOImpl implements CartOrderItemDAO{

    private final EntityManager entityManager;

    public CartOrderItemDAOImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public void save(CartOrderItems cartOrderItems) {
        entityManager.persist(cartOrderItems);
    }
}
