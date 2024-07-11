package com.bestseller.coffeestore.dao;

import com.bestseller.coffeestore.dto.DrinkDTO;
import com.bestseller.coffeestore.dto.ToppingDTO;
import com.bestseller.coffeestore.entity.Drink;
import com.bestseller.coffeestore.entity.Topping;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Repository
public class ToppingsDAOImpl implements ToppingsDAO{

    private final EntityManager entityManager;

    public ToppingsDAOImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public void save(Topping topping) {
        entityManager.persist(topping);
    }

    @Override
    public void delete(Topping topping) {
        entityManager.remove(topping);
    }

    @Override
    public Topping findById(Long toppingId) {
        return entityManager.find(Topping.class, toppingId);
    }

    @Override
    public List<ToppingDTO> getAllToppings() {
        TypedQuery<Topping> findAllQuery = entityManager.createQuery("SELECT e FROM Topping e", Topping.class);
        List<Topping> toppingList = findAllQuery.getResultList();

        List<ToppingDTO> toppingDTOList = toppingList.stream()
                .collect(Collectors.groupingBy(Topping::getId))
                .values()
                .stream()
                .map(topping -> {
                    ToppingDTO toppingDTO = new ToppingDTO(
                            topping.get(0).getId(),
                            topping.get(0).getName(),
                            topping.get(0).getPrice()
                    );
                    return toppingDTO;
                }).collect(Collectors.toList());

        return toppingDTOList;
    }

    @Override
    public void update(Topping topping) {
        entityManager.merge(topping);
    }

    @Override
    public List<ToppingDTO> getOrderedToppings(Set toppingIds) {
        TypedQuery<Topping> orderedToppingsQuery = entityManager.createQuery("SELECT t FROM Topping t WHERE t.id IN :toppingIds", Topping.class);
        orderedToppingsQuery.setParameter("toppingIds", toppingIds);

        List<Topping> resultList = orderedToppingsQuery.getResultList();

        List<ToppingDTO> orderedToppingsList = new ArrayList<>();

        for (Topping topping : resultList) {
            ToppingDTO currentTopping = new ToppingDTO(topping.getId(), topping.getName(), topping.getPrice());
            orderedToppingsList.add(currentTopping);
        }


        return orderedToppingsList;
    }
}
