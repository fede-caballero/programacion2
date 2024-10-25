package com.frcDev.NoInflation.dto;

public class LoginResponseDto {
    private Long userId;
    private String name;
    private String email;
    private String role;
    private String token;
    private ShopDto shop;

    // Constructor por defecto
    public LoginResponseDto() {
    }

    // Constructor con parámetros
    public LoginResponseDto(Long userId, String name, String email, String role, String token, ShopDto shop) {
        this.userId = userId;
        this.name = name;
        this.email = email;
        this.role = role;
        this.token = token;
        this.shop = shop;
    }

    // Getters y setters
    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public ShopDto getShop() {
        return shop;
    }

    public void setShop(ShopDto shop) {
        this.shop = shop;
    }

    // Clase interna para los datos de la tienda
    public static class ShopDto {
        private Long shopId;
        private String shopName;
        private String location;

        // Constructor por defecto
        public ShopDto() {
        }

        // Constructor con parámetros
        public ShopDto(Long shopId, String shopName, String location) {
            this.shopId = shopId;
            this.shopName = shopName;
            this.location = location;
        }

        // Getters y setters
        public Long getShopId() {
            return shopId;
        }

        public void setShopId(Long shopId) {
            this.shopId = shopId;
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
}