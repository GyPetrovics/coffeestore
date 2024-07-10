package com.bestseller.coffeestore.dao;

import com.bestseller.coffeestore.entity.Drink;
import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Repository;

@Repository
public class DrinksDAOImpl implements DrinksDAO{

    private final EntityManager entityManager;

    public DrinksDAOImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public void save(Drink drink) {
        entityManager.persist(drink);
    }
}
