package com.frcDev.NoInflation.dto;

import com.frcDev.NoInflation.shoppingList.ShoppingList;

import java.util.List;
import java.util.stream.Collectors;

public class ShoppingListDTO {
    private Long id;
    private String listName;
    private String description;
    private List<ShoppingListItemDTO> items;

    public ShoppingListDTO(ShoppingList shoppingList) {
        this.id = shoppingList.getId();
        this.listName = shoppingList.getListName();
        this.description = shoppingList.getDescription();
        this.items = shoppingList.getItems() != null
                ? shoppingList.getItems().stream()
                .map(ShoppingListItemDTO::new)
                .collect(Collectors.toList())
                : null;
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

    public List<ShoppingListItemDTO> getItems() {
        return items;
    }

    public void setItems(List<ShoppingListItemDTO> items) {
        this.items = items;
    }
}
