package com.frcDev.NoInflation.dto;

import com.frcDev.NoInflation.shop.Shop;

public class ShopListDTO {
    private Long id;
    private String shopName;
    private String location;

    public ShopListDTO(Shop shop) {
        this.id = shop.getId();
        this.shopName = shop.getShopName();
        this.location = shop.getLocation();
    }

    // Getters y setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public void setLocation(String location) {
        this.location = location;
    }
}