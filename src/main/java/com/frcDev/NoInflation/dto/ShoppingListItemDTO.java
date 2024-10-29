package com.frcDev.NoInflation.dto;

import com.frcDev.NoInflation.ShoppingListItem.ShoppingListItem;

public class ShoppingListItemDTO {
    private Long id;
    private String itemName;
    private Integer quantity;
    private String notes;
    private ProductListDTO product;

    public ShoppingListItemDTO(ShoppingListItem item) {
        this.id = item.getId();
        this.itemName = item.getItemName();
        this.quantity = item.getQuantity();
        this.notes = item.getNotes();
        this.product = item.getProduct() != null ? new ProductListDTO(item.getProduct()) : null;
    }

    // Getters y setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public ProductListDTO getProduct() {
        return product;
    }

    public void setProduct(ProductListDTO product) {
        this.product = product;
    }
}
