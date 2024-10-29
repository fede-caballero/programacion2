package com.frcDev.NoInflation.shop;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.frcDev.NoInflation.product.Product;
import com.frcDev.NoInflation.user.User;
import jakarta.persistence.*;

import java.util.List;

@Entity
public class Shop {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String shopName;
    private String location;

    @OneToMany(mappedBy = "shop")
    @JsonIgnoreProperties("shop")
    private List<Product> products;

    //private List<Price> prices;

    //Constructor por defecto
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

    public String getLocation() {
        return location;
    }

    public void setLocation(String description) {
        this.location = description;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }
    public void setUser(User user) {
    }
}


