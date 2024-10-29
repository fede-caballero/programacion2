package com.frcDev.NoInflation.shoppingList;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.frcDev.NoInflation.product.Product;
import com.frcDev.NoInflation.product.ProductController;
import jakarta.persistence.*;
import com.frcDev.NoInflation.user.User;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import com.frcDev.NoInflation.ShoppingListItem.ShoppingListItem;

@Entity
public class ShoppingList {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String listName;
    private String description;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    @JsonIgnoreProperties("shoppingLists")
    private User user;

    @OneToMany(mappedBy = "shoppingList", cascade = CascadeType.ALL)
    @JsonIgnoreProperties("shoppingList")
    private List<ShoppingListItem> items;

    // Constructor sin parámetros (obligatorio para JPA)
    public ShoppingList() {
    }

    // Constructor con parámetros
    public ShoppingList(String listName, String description) {
        this.listName = listName;
        this.description = description;
    }

    // Getters y setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getListName() {
        return listName;
    }

    public void setListName(String listName) {
        this.listName = listName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<ShoppingListItem> getItems() {
        return items;
    }

    public void setItems(List<ShoppingListItem> items) {
        this.items = items;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
