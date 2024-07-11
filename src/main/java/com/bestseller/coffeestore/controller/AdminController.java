package com.bestseller.coffeestore.controller;

import com.bestseller.coffeestore.controller.bean.DrinkCreation;
import com.bestseller.coffeestore.dto.DrinkDTO;
import com.bestseller.coffeestore.service.AdminService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {

    AdminService adminService;

    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @PostMapping(path = "/createdrink", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DrinkDTO> createDrink(
            @RequestBody DrinkCreation drinkCreation
    ) {
        if (drinkCreation == null) {
            // throw Exception (a custom exception could be created here...)
        }

        return ResponseEntity.ok(adminService.createDrink(drinkCreation));
    }

    @DeleteMapping(path = "/deletedrink/{drinkId}")
    public ResponseEntity<Void> deleteDrink(
            @PathVariable Long drinkId
    ) {
        if (drinkId == null) {
            // throw Exception (a custom exception could be created here...)
        }

        boolean isDeleted = adminService.deleteDrink(drinkId);

        if (isDeleted) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/getAllDrinks")
    public ResponseEntity<List<DrinkDTO>> getAllDrinks() {
        return ResponseEntity.ok(adminService.getAllDrinks());
    }

    @PutMapping(value = "/updateDrink/{drinkId}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> updateDrink(
            @PathVariable Long drinkId,
            @RequestBody DrinkCreation drinkCreation
    ) {

        if (drinkId == null || drinkCreation == null) {
            // throw Exception (a custom exception could be created here...)
        }

        boolean isUpdated = adminService.updateDrink(drinkId, drinkCreation);

        if (isUpdated) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }

}
