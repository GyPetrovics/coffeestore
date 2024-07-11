package com.bestseller.coffeestore.service;

import com.bestseller.coffeestore.controller.bean.DrinkCreation;
import com.bestseller.coffeestore.dao.DrinksDAO;
import com.bestseller.coffeestore.dto.DrinkDTO;
import com.bestseller.coffeestore.entity.Drink;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import lombok.NonNull;

import java.util.List;
import java.util.Optional;

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

    @Override
    @Transactional
    public Boolean deleteDrink(@NonNull Long drinkId) {

        Optional<Drink> drinkOptional = Optional.ofNullable(drinksDAO.findById(drinkId));

        if (drinkOptional.isPresent()) {
            drinksDAO.delete(drinkOptional.get());
            return true;
        } else {
            return false;
        }

    }

    @Override
    public List<DrinkDTO> getAllDrinks() {
        return drinksDAO.getAllDrinks();
    }

    @Override
    @Transactional
    public Boolean updateDrink(@NonNull Long drinkId, @NonNull DrinkCreation drinkCreation) {
        Optional<Drink> drinkOptional = Optional.ofNullable(drinksDAO.findById(drinkId));

        if (drinkOptional.isPresent()) {
            Drink existingDrink = drinkOptional.get();
            existingDrink.setName(drinkCreation.getName());
            existingDrink.setPrice(drinkCreation.getPrice());
            drinksDAO.update(existingDrink);
            return true;
        } else {
            return false;
        }
    }
}
