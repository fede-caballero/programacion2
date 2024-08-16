package com.frcDev.NoInflation.ShoppingListItem;

import com.frcDev.NoInflation.product.Product;
import com.frcDev.NoInflation.shoppingList.ShoppingList;
import jakarta.persistence.*;

@Entity
public class ShoppingListItem {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "shopping_list_item_seq")
    @SequenceGenerator(name = "shopping_list_item_seq", sequenceName = "shopping_list_item_seq", allocationSize = 50)
    private Long id;
    private String itemName;
    private Integer quantity;
    private String notes;

    @ManyToOne
    @JoinColumn(name = "shopping_list_id", nullable = false)
    private ShoppingList shoppingList;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    // Builder
    public ShoppingListItem() {
    }

    //Builder with parameters
    public ShoppingListItem(String itemName, Integer quantity, String notes, ShoppingList shoppingList, Product product) {
        this.itemName = itemName;
        this.quantity = quantity;
        this.notes = notes;
        this.shoppingList = shoppingList;
        this.product = product;
    }

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

    public ShoppingList getShoppingList() {
        return shoppingList;
    }

    public void setShoppingList(ShoppingList shoppingList) {
        this.shoppingList = shoppingList;
    }
}
