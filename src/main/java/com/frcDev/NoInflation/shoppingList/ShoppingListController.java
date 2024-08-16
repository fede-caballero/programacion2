package com.frcDev.NoInflation.shoppingList;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/shopping-lists")
public class ShoppingListController {
    private final ShoppingListService shoppingListService;

    public ShoppingListController(ShoppingListService shoppingListService) {
        this.shoppingListService = shoppingListService;
    }

    @GetMapping
    public List<ShoppingList> getAllShoppingLists() {
        return shoppingListService.getAllShoppingLists();
    }

    @GetMapping("/{shoppingListId}")
    public ResponseEntity<ShoppingList> getShoppingListById(@PathVariable Long shoppingListId) {
        ShoppingList shoppingList = shoppingListService.getShoppingListById(shoppingListId);
        if (shoppingList != null)
            return new ResponseEntity<>(shoppingList, HttpStatus.OK);
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping
    public ResponseEntity<String> createShoppingList(@RequestBody ShoppingList shoppingList) {
        shoppingListService.createShoppingList(shoppingList);
        return new ResponseEntity<>("Shopping list created", HttpStatus.CREATED);
    }

    @PutMapping("/{shoppingListId}")
    public ResponseEntity<String> updateShoppingList(@PathVariable Long shoppingListId, @RequestBody ShoppingList updatedShoppingList) {
        boolean updated = shoppingListService.updateShoppingList(updatedShoppingList, shoppingListId);
        if (updated)
            return new ResponseEntity<>("Shopping list updated", HttpStatus.OK);
        return new ResponseEntity<>("Shopping list not found", HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/{shoppingListId}")
    public ResponseEntity<String> deleteShoppingList(@PathVariable Long shoppingListId) {
        boolean deleted = shoppingListService.deleteShoppingListById(shoppingListId);
        if (deleted)
            return new ResponseEntity<>("Shopping list deleted", HttpStatus.OK);
        return new ResponseEntity<>("Shopping list not found", HttpStatus.NOT_FOUND);

    }

}

