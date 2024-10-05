package com.frcDev.NoInflation.shop;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/shops")
public class ShopController {

    private ShopService shopService;

    public ShopController(ShopService shopService) {
        this.shopService = shopService;
    }


    @GetMapping
    public List<Shop> getAllShops() {
        return shopService.getAllShops();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Shop> getShopById(@PathVariable Long id) {
        Shop shop = shopService.getShopById(id);
        if (shop != null)
            return new ResponseEntity<>(shop, HttpStatus.OK);
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping
    public ResponseEntity<String> createShop(@RequestBody Shop shop) {
        shopService.createShop(shop);
        return new ResponseEntity<>("Shop added successfully", HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateShop(@PathVariable Long id, @RequestBody Shop updatedShop) {
        boolean updated = shopService.updateShop(updatedShop, id);
        if (updated)
            return new ResponseEntity<>("Shop updated successfully", HttpStatus.OK);
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteShop(@PathVariable Long id) {
        boolean deleted = shopService.deleteShopById(id);
        if (deleted)
            return new ResponseEntity<>("Shop deleted successfully", HttpStatus.OK);
        return new ResponseEntity<>("Shop not found",HttpStatus.NOT_FOUND);
    }


}
