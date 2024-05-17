package com.frcDev.NoInflation.product;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.frcDev.NoInflation.shop.Shop;
import jakarta.persistence.*;

@Entity
//@Table(name = "product_table")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String productName;
    private String description;
    private Double price;
    private String location;


    @ManyToOne
    private Shop shop;
    public Product() {
    }

    public Product(Long id, String productName, String description, Double price, String location) {
        this.id = id;
        this.productName = productName;
        this.description = description;
        this.price = price;
        this.location = location;

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
