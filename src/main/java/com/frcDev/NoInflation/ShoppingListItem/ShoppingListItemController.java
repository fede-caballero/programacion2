package com.frcDev.NoInflation.ShoppingListItem;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/shoppinglists/{shoppingListId}/items")
public class ShoppingListItemController {
    private final ShoppingListItemService shoppingListItemService;

    public ShoppingListItemController(ShoppingListItemService shoppingListItemService) {
        this.shoppingListItemService = shoppingListItemService;
    }

    @GetMapping
    public List<ShoppingListItem> getAllItems(@PathVariable Long shoppingListId) {
        return shoppingListItemService.getAllItemsByShoppingListId(shoppingListId);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ShoppingListItem> getShoppingListItemById(@PathVariable Long id) {
        ShoppingListItem item = shoppingListItemService.getShoppingListItemById(id);
        if (item != null)
            return new ResponseEntity<>(item, HttpStatus.OK);
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping
    public ResponseEntity<String> createShoppingListItem(@PathVariable Long shoppingListId, @RequestBody ShoppingListItem shoppingListItem) {
        shoppingListItemService.createShoppingListItem(shoppingListItem);
        return new ResponseEntity<>("Shopping List Item added",HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateShoppingListItem(@PathVariable Long id, @RequestBody ShoppingListItem updatedItem) {
        boolean updated = shoppingListItemService.updateShoppingListItem(updatedItem, id);
        if (updated)
            return new ResponseEntity<>("Shopping List Item updated", HttpStatus.OK);
        return new ResponseEntity<>("Shopping List Item not found", HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteShoppingListItem(@PathVariable Long id) {
        boolean deleted = shoppingListItemService.deleteShoppingListItemById(id);
        if (deleted)
            return new ResponseEntity<>("Shopping List Item deleted", HttpStatus.OK);
        return new ResponseEntity<>("Shopping List Item not found", HttpStatus.NOT_FOUND);
    }

}
