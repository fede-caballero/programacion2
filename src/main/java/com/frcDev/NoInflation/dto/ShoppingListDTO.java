package com.frcDev.NoInflation.dto;

import com.frcDev.NoInflation.shoppingList.ShoppingList;

import java.util.List;
import java.util.stream.Collectors;

public class ShoppingListDTO {
    private Long id;
    private String listName;
    private String description;
    private List<ShoppingListItemDTO> items;

    // Constructor
    public ShoppingListDTO(ShoppingList shoppingList) {
        this.id = shoppingList.getId();
        this.listName = shoppingList.getListName();
        this.description = shoppingList.getDescription();
        this.items = shoppingList.getItems().stream()
                .map(ShoppingListItemDTO::new)
                .collect(Collectors.toList());
    }

    public Long getId() {
        return id;
    }

    public String getListName() {
        return listName;
    }

    public String getDescription() {
        return description;
    }

    public List<ShoppingListItemDTO> getItems() {
        return items;
    }
}
