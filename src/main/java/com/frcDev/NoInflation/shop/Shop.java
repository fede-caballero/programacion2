package com.frcDev.NoInflation.shop;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.frcDev.NoInflation.product.Product;
import jakarta.persistence.*;

import java.util.List;

@Entity
public class Shop {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String shopName;
    private String description;

    @JsonIgnore
    @OneToMany(mappedBy = "shop")
    private List<Product> products;

    //private List<Price> prices;

    public Shop() {

    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }
}


