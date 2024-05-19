package com.frcDev.NoInflation.cart;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/carts")
public class CartController {
    CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @GetMapping
    public ResponseEntity<List<Cart>> findAll() {
        return ResponseEntity.ok(cartService.findAll());
    }

    @PostMapping
    public ResponseEntity<String> createCart(@RequestBody Cart cart) {
        cartService.createCart(cart);
        return new ResponseEntity<>("Product added successfully", HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Cart> getCartById(@PathVariable Long id) {
        Cart cart = cartService.getCartById(id);
        if (cart != null)
            return new ResponseEntity<>(cart, HttpStatus.OK);
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateCart(@PathVariable Long id, @RequestBody Cart updatedCart) {
        boolean updated = cartService.updateCart(id, updatedCart);
        if (updated)
            return new ResponseEntity<>("Cart updated successfully", HttpStatus.OK);
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCart(@PathVariable Long id) {
        boolean deleted = cartService.deleteCart(id);
        if (deleted)
            return new ResponseEntity<>("Cart deleted successfully", HttpStatus.OK);
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
