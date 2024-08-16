package com.frcDev.NoInflation.user;

import com.frcDev.NoInflation.shoppingList.ShoppingList;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;
    private String name;
    private String email;
    private String password;
    private String role;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ShoppingList> shoppingLists;

    public User() {
        // Constructor por defecto
    }

    public User(Long id, String name, String email, String password, String role) {
        this.userId = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.role = role;
    }

    // Getters y setters

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long id) {
        this.userId = id;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public List<ShoppingList> getShoppingLists() {
        return shoppingLists;
    }

    public void setShoppingList(List<ShoppingList> shoppingLists) {
        this.shoppingLists = shoppingLists;
    }
}