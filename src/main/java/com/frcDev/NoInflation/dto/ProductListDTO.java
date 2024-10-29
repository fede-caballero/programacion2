package com.frcDev.NoInflation.dto;

import com.frcDev.NoInflation.product.Product;
import com.frcDev.NoInflation.product.ProductCategory;

public class ProductListDTO {
    private Long productId;
    private String productName;
    private String description;
    private Double price;
    private String location;
    private ProductCategory category;
    private ShopListDTO shop;

    public ProductListDTO(Product product) {
        this.productId = product.getProductId();
        this.productName = product.getProductName();
        this.description = product.getDescription();
        this.price = product.getPrice();
        this.location = product.getLocation();
        this.category = product.getCategory();
        this.shop = product.getShop() != null ? new ShopListDTO(product.getShop()) : null;
    }

    // Getters y setters
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

    public ProductCategory getCategory() {
        return category;
    }

    public void setCategory(ProductCategory category) {
        this.category = category;
    }

    public ShopListDTO getShop() {
        return shop;
    }

    public void setShop(ShopListDTO shop) {
        this.shop = shop;
    }
}