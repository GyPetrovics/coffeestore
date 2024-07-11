package com.bestseller.coffeestore.dao;

import com.bestseller.coffeestore.dto.DrinkDTO;
import com.bestseller.coffeestore.entity.Drink;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

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

    @Override
    public void delete(Drink drink) {
        entityManager.remove(drink);
    }

    @Override
    public Drink findById(Long drinkId) {
        return entityManager.find(Drink.class, drinkId);
    }

    @Override
    public List<DrinkDTO> getAllDrinks() {

        TypedQuery<Drink> findAllQuery = entityManager.createQuery("SELECT e FROM Drink e", Drink.class);
        List<Drink> drinkList = findAllQuery.getResultList();

        List<DrinkDTO> drinkDTOList = drinkList.stream()
                .collect(Collectors.groupingBy(Drink::getId))
                .values()
                .stream()
                .map(drink -> {
                    DrinkDTO drinkDTO = new DrinkDTO(
                            drink.get(0).getId(),
                            drink.get(0).getName(),
                            drink.get(0).getPrice()
                    );
                    return drinkDTO;
                }).collect(Collectors.toList());

        return drinkDTOList;
    }

    @Override
    public void update(Drink drink) {
        entityManager.merge(drink);
    }

    @Override
    public List<DrinkDTO> getOrderedDrinks(Set drinkIds) {

        TypedQuery<Drink> orderedDrinksQuery = entityManager.createQuery("SELECT d FROM Drink d WHERE d.id IN :drinkIds", Drink.class);
        orderedDrinksQuery.setParameter("drinkIds", drinkIds);

        List<Drink> resultList = orderedDrinksQuery.getResultList();

        List<DrinkDTO> drinkPricesList = new ArrayList<>();

        for (Drink drink : resultList) {
            DrinkDTO currentDrink = new DrinkDTO(drink.getId(), drink.getName(), drink.getPrice());
            drinkPricesList.add(currentDrink);
        }


        return drinkPricesList;
    }
}
