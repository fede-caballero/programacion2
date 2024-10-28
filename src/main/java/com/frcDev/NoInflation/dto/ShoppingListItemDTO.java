package com.frcDev.NoInflation.dto;

import com.frcDev.NoInflation.ShoppingListItem.ShoppingListItem;

public class ShoppingListItemDTO {
    private Long id;
    private String itemName;
    private Integer quantity;
    private String notes;

    // Constructor
    public ShoppingListItemDTO(ShoppingListItem item) {
        this.id = item.getId();
        this.itemName = item.getItemName();
        this.quantity = item.getQuantity();
        this.notes = item.getNotes();
    }

    public Long getId() {
        return id;
    }

    public String getItemName() {
        return itemName;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public String getNotes() {
        return notes;
    }

    // Getters
}