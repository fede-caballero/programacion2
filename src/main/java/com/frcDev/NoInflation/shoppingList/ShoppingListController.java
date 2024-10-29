package com.frcDev.NoInflation.shoppingList;

import com.frcDev.NoInflation.ShoppingListItem.ShoppingListItem;
import com.frcDev.NoInflation.dto.ShoppingListDTO;
import com.frcDev.NoInflation.product.Product;
import com.frcDev.NoInflation.product.ProductService;
import com.frcDev.NoInflation.user.User;
import com.frcDev.NoInflation.user.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@CrossOrigin(origins = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})
@RequestMapping("/api/users/{userId}/shopping-lists")
public class ShoppingListController {
    private final ShoppingListService shoppingListService;
    private final UserService userService;
    private final ProductService productService;

    public ShoppingListController(ShoppingListService shoppingListService,
                                  UserService userService,
                                  ProductService productService) {
        this.shoppingListService = shoppingListService;
        this.userService = userService;
        this.productService = productService;
    }

    @GetMapping
    public ResponseEntity<List<ShoppingListDTO>> getAllShoppingLists(@PathVariable Long userId) {
        try {
            List<ShoppingList> lists = shoppingListService.getAllShoppingListsByUser(userId);
            List<ShoppingListDTO> dtoList = lists.stream()
                    .map(ShoppingListDTO::new)
                    .collect(Collectors.toList());
            return ResponseEntity.ok(dtoList);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/{shoppingListId}")
    public ResponseEntity<?> getShoppingListById(@PathVariable Long userId, @PathVariable Long shoppingListId) {
        try {
            ShoppingList shoppingList = shoppingListService.getShoppingListByIdAndUser(shoppingListId, userId);
            if (shoppingList == null) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok(new ShoppingListDTO(shoppingList));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Error al obtener la lista de compras: " + e.getMessage()));
        }
    }

    @PostMapping(
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<?> createShoppingList(
            @PathVariable Long userId,
            @RequestBody ShoppingList shoppingList
    ) {
        try {
            User user = userService.getUserById(userId);
            if (user == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body("{\"error\": \"Usuario no encontrado\"}");
            }

            shoppingList.setUser(user);
            ShoppingList created = shoppingListService.createShoppingList(shoppingList);

            return ResponseEntity.status(HttpStatus.CREATED)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(created);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body("{\"error\": \"" + e.getMessage() + "\"}");
        }
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

    @PostMapping("/{listId}/items")
    public ResponseEntity<?> addItemToList(
            @PathVariable Long userId,
            @PathVariable Long listId,
            @RequestBody Map<String, Object> request) {
        try {
            ShoppingListItem newItem = new ShoppingListItem();
            newItem.setItemName(request.get("itemName").toString());
            newItem.setQuantity(Integer.parseInt(request.get("quantity").toString()));
            newItem.setNotes(request.get("notes") != null ? request.get("notes").toString() : null);

            if (request.get("productId") != null) {
                Product product = new Product();
                product.setProductId(Long.parseLong(request.get("productId").toString()));
                newItem.setProduct(product);
            }

            ShoppingListItem savedItem = shoppingListService.addItemToList(userId, listId, newItem);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedItem);

        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(Map.of("error", "Error al agregar item: " + e.getMessage()));
        }
    }

    @DeleteMapping("/{listId}/items/{itemId}")
    public ResponseEntity<?> removeItemFromList(
            @PathVariable Long userId,
            @PathVariable Long listId,
            @PathVariable Long itemId) {
        try {
            boolean removed = shoppingListService.removeItemFromList(userId, listId, itemId);
            if (removed) {
                return ResponseEntity.ok(Map.of("message", "Item eliminado correctamente"));
            }
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("error", "Item no encontrado"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Error al eliminar item: " + e.getMessage()));
        }
    }

    @PatchMapping("/{listId}/items/{itemId}")
    public ResponseEntity<?> updateItemQuantity(
            @PathVariable Long userId,
            @PathVariable Long listId,
            @PathVariable Long itemId,
            @RequestBody Map<String, Integer> request) {
        try {
            Integer quantity = request.get("quantity");
            if (quantity == null || quantity < 1) {
                return ResponseEntity.badRequest()
                        .body(Map.of("error", "Cantidad inválida"));
            }

            boolean updated = shoppingListService.updateItemQuantity(userId, listId, itemId, quantity);
            if (updated) {
                return ResponseEntity.ok(Map.of("message", "Cantidad actualizada correctamente"));
            }
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("error", "Item no encontrado"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Error al actualizar cantidad: " + e.getMessage()));
        }
    }

}
