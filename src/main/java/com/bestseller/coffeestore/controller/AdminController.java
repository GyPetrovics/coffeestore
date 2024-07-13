package com.bestseller.coffeestore.controller;

import com.bestseller.coffeestore.controller.bean.DrinkCreation;
import com.bestseller.coffeestore.controller.bean.ToppingCreation;
import com.bestseller.coffeestore.dao.UsersDAO;
import com.bestseller.coffeestore.dto.DrinkDTO;
import com.bestseller.coffeestore.dto.MostUsedToppingDTO;
import com.bestseller.coffeestore.dto.ToppingDTO;
import com.bestseller.coffeestore.service.AdminService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/admin")
public class AdminController {

    AdminService adminService;
    UsersDAO usersDAO;

    public AdminController(AdminService adminService, UsersDAO usersDAO) {
        this.adminService = adminService;
        this.usersDAO = usersDAO;
    }

//    CRUD endpoints for Drinks -----------------------------------------------------------

    @PostMapping(path = "/createdrink", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> createDrink(
            @RequestBody DrinkCreation drinkCreation
    ) {
        if (drinkCreation == null) {
            // throw Exception (a custom exception could be created here...)
        }
        if (usersDAO.isAdmin(drinkCreation.getUserId())) {
            return ResponseEntity.ok(adminService.createDrink(drinkCreation));
        } else {
            log.error("You are not authorized to create new drinks in the database!");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("You are not authorized to create new drinks in the database!");
        }
    }

    @DeleteMapping(path = "/deletedrink/{drinkId}/{userId}")
    public ResponseEntity<?> deleteDrink(
            @PathVariable Long drinkId,
            @PathVariable String userId
    ) {
        if (drinkId == null) {
            // throw Exception (a custom exception could be created here...)
        }

        if (usersDAO.isAdmin(userId)) {
            boolean isDeleted = adminService.deleteDrink(drinkId);

            if (isDeleted) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } else {
            log.error("You are not authorized to delete drinks from the database!");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("You are not authorized to delete drinks from the database!");
        }

    }

    @GetMapping("/getAllDrinks/{userId}")
    public ResponseEntity<?> getAllDrinks(
            @PathVariable String userId
    ) {
        if (usersDAO.isAdmin(userId)) {
            return ResponseEntity.ok(adminService.getAllDrinks());
        } else {
            log.error("You are not authorized to retrieve the list of drinks!");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("You are not authorized to retrieve the list of drinks!");
        }
    }

    @PutMapping(value = "/updateDrink/{drinkId}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> updateDrink(
            @PathVariable Long drinkId,
            @RequestBody DrinkCreation drinkCreation
    ) {

        if (drinkId == null || drinkCreation == null) {
            // throw Exception (a custom exception could be created here...)
        }

        if (usersDAO.isAdmin(drinkCreation.getUserId())) {
            boolean isUpdated = adminService.updateDrink(drinkId, drinkCreation);

            if (isUpdated) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } else {
            log.error("You are not authorized to update drinks in the database!");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("You are not authorized to update drinks in the database!");
        }
    }

//    CRUD endpoints for Toppings -----------------------------------------------------------

    @PostMapping(path = "/createtopping", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> createTopping(
            @RequestBody ToppingCreation toppingCreation
    ) {
        if (toppingCreation == null) {
            // throw Exception (a custom exception could be created here...)
        }

        if (usersDAO.isAdmin(toppingCreation.getUserId())) {
            return ResponseEntity.ok(adminService.createTopping(toppingCreation));
        } else {
            log.error("You are not authorized to create new topping in the database!");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("You are not authorized to create new topping in the database!");
        }
    }

    @DeleteMapping(path = "/deletetopping/{toppingId}/{userId}")
    public ResponseEntity<?> deleteTopping(
            @PathVariable Long toppingId,
            @PathVariable String userId
    ) {
        if (toppingId == null) {
            // throw Exception (a custom exception could be created here...)
        }

        if (usersDAO.isAdmin(userId)) {
            boolean isDeleted = adminService.deleteTopping(toppingId);

            if (isDeleted) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } else {
            log.error("You are not authorized to delete toppings from the database!");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("You are not authorized to delete toppings from the database!");
        }
    }

    @GetMapping("/getAllToppings/{userId}")
    public ResponseEntity<?> getAllToppings(
            @PathVariable String userId
    ) {
        if (usersDAO.isAdmin(userId)) {
            return ResponseEntity.ok(adminService.getAllToppings());
        } else {
            log.error("You are not authorized to retrieve the list of toppings!");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("You are not authorized to retrieve the list of toppings!");
        }
    }

    @PutMapping(value = "/updateTopping/{toppingId}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> updateTopping(
            @PathVariable Long toppingId,
            @RequestBody ToppingCreation toppingCreation
    ) {

        if (toppingId == null || toppingCreation == null) {
            // throw Exception (a custom exception could be created here...)
        }

        if (usersDAO.isAdmin(toppingCreation.getUserId())) {
            boolean isUpdated = adminService.updateTopping(toppingId, toppingCreation);

            if (isUpdated) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } else {
            log.error("You are not authorized to update toppings!");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("You are not authorized to update toppings!");
        }
    }

    @GetMapping("/mostUsedToppings/{userId}")
    public ResponseEntity<?> getMostUsedToppings(
            @PathVariable String userId
    ) {
        if (usersDAO.isAdmin(userId)) {
            return ResponseEntity.ok(adminService.getMostUsedToppings());
        } else {
            log.error("You are not authorized to get the list of the most used toppings!");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("You are not authorized to get the list of the most used toppings!");
        }
    }

}
