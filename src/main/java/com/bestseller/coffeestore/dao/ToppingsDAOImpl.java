package com.bestseller.coffeestore.dao;

import com.bestseller.coffeestore.dto.ToppingDTO;
import com.bestseller.coffeestore.entity.Topping;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Repository;

import java.util.List;
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
}
