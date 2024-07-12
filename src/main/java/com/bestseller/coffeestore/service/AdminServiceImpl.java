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
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import lombok.NonNull;

import java.util.List;
import java.util.Optional;

@Slf4j
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

        log.info("New drink has been added...");
        return drinkDTO;
    }

    @Override
    @Transactional
    public Boolean deleteDrink(@NonNull Long drinkId) {

        Optional<Drink> drinkOptional = Optional.ofNullable(drinksDAO.findById(drinkId));

        if (drinkOptional.isPresent()) {
            drinksDAO.delete(drinkOptional.get());
            log.info("Drink with id: " + drinkId + " has been deleted from the database...");
            return true;
        } else {
            return false;
        }

    }

    @Override
    public List<DrinkDTO> getAllDrinks() {
        log.info("List of all drinks have been retrieved...");
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
            log.info("Drink with id: " + drinkId + " has been updated...");
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

        log.info("A new topping has been added...");
        return toppingDTO;
    }

    @Override
    @Transactional
    public Boolean deleteTopping(@NonNull Long toppingId) {

        Optional<Topping> toppingOptional = Optional.ofNullable(toppingsDAO.findById(toppingId));

        if (toppingOptional.isPresent()) {
            toppingsDAO.delete(toppingOptional.get());
            log.info("The topping with id: " + " has been deleted from the database...");
            return true;
        } else {
            return false;
        }

    }

    @Override
    public List<ToppingDTO> getAllToppings() {
        log.info("List of all toppings have been retrieved...");
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
            log.info("The topping with id: " + toppingId + " has been updated in the database...");
            return true;
        } else {
            return false;
        }
    }

    @Override
    public List<MostUsedToppingDTO> getMostUsedToppings() {
        log.info("The list of the most used toppings has been retrieved...");
        return ordersDAO.getMostUsedToppings();
    }
}
