package com.bestseller.coffeestore.dao;

import com.bestseller.coffeestore.dto.MostUsedToppingDTO;
import com.bestseller.coffeestore.dto.ToppingDTO;
import com.bestseller.coffeestore.entity.Orders;
import com.bestseller.coffeestore.entity.Topping;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Repository;

import java.util.List;

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

    @Override
    public List<MostUsedToppingDTO> getMostUsedToppings() {
        TypedQuery<MostUsedToppingDTO> mostUsedToppingsQuery = entityManager
                .createQuery("SELECT t.id, t.name, count(o.toppingId) as mostUsedToppingId FROM Topping t LEFT JOIN OrderItems o ON (t.id = o.toppingId) GROUP BY t.id, t.name ORDER BY mostUsedToppingId DESC", MostUsedToppingDTO.class);

        List<MostUsedToppingDTO> resultList = mostUsedToppingsQuery.getResultList();

        return resultList;
    }
}
