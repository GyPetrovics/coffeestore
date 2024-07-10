package com.bestseller.coffeestore.dao;

import com.bestseller.coffeestore.entity.Orders;
import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Repository;

@Repository
public class OrdersDAOImpl implements OrdersDAO{

    private final EntityManager entityManager;

    public OrdersDAOImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public void save(Orders orders) {
        entityManager.persist(orders);
    }
}
