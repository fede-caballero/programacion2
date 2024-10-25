package com.frcDev.NoInflation.product;

import com.frcDev.NoInflation.shop.Shop;
import com.frcDev.NoInflation.shoppingList.ShoppingList;
import jakarta.persistence.*;

@Entity
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productId;
    private String productName;
    private String description;
    private Double price;
    private String location;

    @ManyToOne
    @JoinColumn(name = "shopping_list_id")
    private ShoppingList shoppingList;

    @ManyToOne
    @JoinColumn(name = "shop_id", nullable = false)
    private Shop shop;

    public void setShopById(Long shopId) {
        if (this.shop == null) {
            this.shop = new Shop();
        }
        this.shop.setId(shopId);
    }

    public boolean isValid() {
        return productName != null && !productName.trim().isEmpty() &&
                description != null && !description.trim().isEmpty() &&
                price != null && price > 0 &&
                shop != null && shop.getId() != null;
    }

    // Default constructor
    public Product() {
    }

    // Constructor with parameters
    public Product(Long productId, String productName, String description, Double price, String location, Shop shop) {
        this.productId = productId;
        this.productName = productName;
        this.description = description;
        this.price = price;
        this.location = location;
        this.shop = shop;
    }

    // Getters and Setters
    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Shop getShop() {
        return shop;
    }

    public void setShop(Shop shop) {
        this.shop = shop;
    }
}