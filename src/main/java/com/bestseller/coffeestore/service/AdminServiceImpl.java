package com.bestseller.coffeestore.service;

import com.bestseller.coffeestore.controller.bean.DrinkCreation;
import com.bestseller.coffeestore.dao.DrinksDAO;
import com.bestseller.coffeestore.dto.DrinkDTO;
import com.bestseller.coffeestore.entity.Drink;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import lombok.NonNull;

@Service
public class AdminServiceImpl implements AdminService{

    DrinksDAO drinksDAO;

    public AdminServiceImpl(DrinksDAO drinksDAO) {
        this.drinksDAO = drinksDAO;
    }

    @Override
    @Transactional
    public DrinkDTO createDrink(@NonNull DrinkCreation drinkCreation) {

        Drink drink = new Drink();
        drink.setName(drinkCreation.getName());
        drink.setPrice(drinkCreation.getPrice());

        drinksDAO.save(drink);

        DrinkDTO drinkDTO = new DrinkDTO(drink.getId(), drink.getName(), drink.getPrice());

        return drinkDTO;
    }
}
