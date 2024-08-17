package com.frcDev.NoInflation.shoppingList;

import com.frcDev.NoInflation.user.User;
import com.frcDev.NoInflation.user.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users/{userId}/shopping-lists")
public class ShoppingListController {
    private final ShoppingListService shoppingListService;
    private final UserService userService;

    public ShoppingListController(ShoppingListService shoppingListService, UserService userService) {
        this.shoppingListService = shoppingListService;
        this.userService = userService;
    }

    @GetMapping
    public List<ShoppingList> getAllShoppingLists(@PathVariable Long userId) {
        return shoppingListService.getAllShoppingListsByUser(userId);
    }

    @GetMapping("/{shoppingListId}")
    public ResponseEntity<ShoppingList> getShoppingListById(@PathVariable Long userId, @PathVariable Long shoppingListId) {
        ShoppingList shoppingList = shoppingListService.getShoppingListByIdAndUser(shoppingListId, userId);
        if (shoppingList != null)
            return new ResponseEntity<>(shoppingList, HttpStatus.OK);
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping
    public ResponseEntity<String> createShoppingList(@PathVariable Long userId, @RequestBody ShoppingList shoppingList) {
        User user = userService.getUserById(userId);
        if (user == null) {
            return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
        }
        shoppingList.setUser(user); // Asociar el usuario a la lista de compras
        shoppingListService.createShoppingList(shoppingList);
        return new ResponseEntity<>("Shopping list created", HttpStatus.CREATED);
    }

    @PutMapping("/{shoppingListId}")
    public ResponseEntity<String> updateShoppingList(@PathVariable Long userId, @PathVariable Long shoppingListId, @RequestBody ShoppingList updatedShoppingList) {
        boolean updated = shoppingListService.updateShoppingList(updatedShoppingList, shoppingListId, userId);
        if (updated)
            return new ResponseEntity<>("Shopping list updated", HttpStatus.OK);
        return new ResponseEntity<>("Shopping list not found", HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/{shoppingListId}")
    public ResponseEntity<String> deleteShoppingList(@PathVariable Long userId, @PathVariable Long shoppingListId) {
        boolean deleted = shoppingListService.deleteShoppingListByIdAndUser(shoppingListId, userId);
        if (deleted)
            return new ResponseEntity<>("Shopping list deleted", HttpStatus.OK);
        return new ResponseEntity<>("Shopping list not found", HttpStatus.NOT_FOUND);
    }
}
