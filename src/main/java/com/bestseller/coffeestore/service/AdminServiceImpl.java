package com.bestseller.coffeestore.service;

import com.bestseller.coffeestore.controller.bean.DrinkCreation;
import com.bestseller.coffeestore.controller.bean.ToppingCreation;
import com.bestseller.coffeestore.dao.DrinksDAO;
import com.bestseller.coffeestore.dao.OrdersDAO;
import com.bestseller.coffeestore.dao.ToppingsDAO;
import com.bestseller.coffeestore.dto.DrinkDTO;
import com.bestseller.coffeestore.dto.MostUsedToppingDTO;
import com.bestseller.coffeestore.dto.ToppingDTO;
import com.bestseller.coffeestore.entity.Drink;
import com.bestseller.coffeestore.entity.Topping;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import lombok.NonNull;

import java.util.List;
import java.util.Optional;

@Service
public class AdminServiceImpl implements AdminService{

    DrinksDAO drinksDAO;
    ToppingsDAO toppingsDAO;
    OrdersDAO ordersDAO;

    public AdminServiceImpl(DrinksDAO drinksDAO, ToppingsDAO toppingsDAO, OrdersDAO ordersDAO) {
        this.drinksDAO = drinksDAO;
        this.toppingsDAO = toppingsDAO;
        this.ordersDAO = ordersDAO;
    }

//    CRUD service methods for Drinks -------------------------------------------------------------------------

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

//    CRUD service methods for Toppings -------------------------------------------------------------------------

    @Override
    @Transactional
    public ToppingDTO createTopping(@NonNull ToppingCreation toppingCreation) {

        Topping topping = new Topping();
        topping.setName(toppingCreation.getName());
        topping.setPrice(toppingCreation.getPrice());

        toppingsDAO.save(topping);

        ToppingDTO toppingDTO = new ToppingDTO(topping.getId(), topping.getName(), topping.getPrice());

        return toppingDTO;
    }

    @Override
    @Transactional
    public Boolean deleteTopping(@NonNull Long toppingId) {

        Optional<Topping> toppingOptional = Optional.ofNullable(toppingsDAO.findById(toppingId));

        if (toppingOptional.isPresent()) {
            toppingsDAO.delete(toppingOptional.get());
            return true;
        } else {
            return false;
        }

    }

    @Override
    public List<ToppingDTO> getAllToppings() {
        return toppingsDAO.getAllToppings();
    }

    @Override
    @Transactional
    public Boolean updateTopping(@NonNull Long toppingId, @NonNull ToppingCreation toppingCreation) {
        Optional<Topping> toppingOptional = Optional.ofNullable(toppingsDAO.findById(toppingId));

        if (toppingOptional.isPresent()) {
            Topping existingTopping = toppingOptional.get();
            existingTopping.setName(toppingCreation.getName());
            existingTopping.setPrice(toppingCreation.getPrice());
            toppingsDAO.update(existingTopping);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public List<MostUsedToppingDTO> getMostUsedToppings() {
        return ordersDAO.getMostUsedToppings();
    }
}
