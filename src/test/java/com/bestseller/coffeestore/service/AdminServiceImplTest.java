package com.bestseller.coffeestore.service;

import com.bestseller.coffeestore.controller.bean.DrinkCreation;
import com.bestseller.coffeestore.controller.bean.ToppingCreation;
import com.bestseller.coffeestore.mock.MockDrinksDAO;
import com.bestseller.coffeestore.mock.MockOrdersDAO;
import com.bestseller.coffeestore.mock.MockToppingsDAO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class AdminServiceImplTest {

    private final AdminServiceImpl testedAdminService = new AdminServiceImpl(
            new MockDrinksDAO(),
            new MockToppingsDAO(),
            new MockOrdersDAO()
    );

    @Test
    void testCreateDrink() {
        DrinkCreation drinkCreation = new DrinkCreation();
        drinkCreation.setId(1L);
        drinkCreation.setName("Coffee");
        drinkCreation.setPrice(4);
        Assertions.assertNotNull(testedAdminService.createDrink(drinkCreation));
    }

    @Test
    void testDeleteDrink() {
        Assertions.assertTrue(testedAdminService.deleteDrink(1L));
    }

    @Test
    void testGetAllDrinks() {
        Assertions.assertNotNull(testedAdminService.getAllDrinks());
        Assertions.assertTrue(testedAdminService.getAllDrinks().size() > 0);
    }

    @Test
    void testUpdateDrink() {
        DrinkCreation drinkCreation = new DrinkCreation();
        drinkCreation.setId(1L);
        drinkCreation.setName("Coffee");
        drinkCreation.setPrice(4);
        Assertions.assertTrue(testedAdminService.updateDrink(1L, drinkCreation));
    }

    @Test
    void testCreateTopping() {
        ToppingCreation toppingCreation = new ToppingCreation();
        toppingCreation.setId(1L);
        toppingCreation.setName("Milk");
        toppingCreation.setPrice(2);
        Assertions.assertNotNull(testedAdminService.createTopping(toppingCreation));
    }

    @Test
    void testDeleteTopping() {
        Assertions.assertTrue(testedAdminService.deleteTopping(1L));
    }

    @Test
    void testGetAllToppings() {
        Assertions.assertNotNull(testedAdminService.getAllToppings());
        Assertions.assertTrue(testedAdminService.getAllToppings().size() > 0);
    }

    @Test
    void testUpdateTopping() {
        ToppingCreation toppingCreation = new ToppingCreation();
        toppingCreation.setId(1L);
        toppingCreation.setName("Milk");
        toppingCreation.setPrice(2);
        Assertions.assertTrue(testedAdminService.updateTopping(1L, toppingCreation));
    }

    @Test
    void testGetMostUsedToppings() {
        Assertions.assertNotNull(testedAdminService.getMostUsedToppings());
        Assertions.assertTrue(testedAdminService.getMostUsedToppings().size() > 0);
    }
}