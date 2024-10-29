package com.frcDev.NoInflation.ShoppingListItem;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.frcDev.NoInflation.product.Product;
import com.frcDev.NoInflation.shoppingList.ShoppingList;
import jakarta.persistence.*;

@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Table(name = "shopping_list_item")
public class ShoppingListItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String itemName;
    private Integer quantity;
    private String notes;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "shopping_list_id")
    @JsonIgnoreProperties({"items", "user", "hibernateLazyInitializer", "handler"})
    private ShoppingList shoppingList;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    @JsonIgnoreProperties({"shoppingListItems", "hibernateLazyInitializer", "handler"})
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

    public Product getProduct() {
        return product;
    }
    public void setProduct(Product product) {
        this.product = product;
    }

    @Override
    public String toString() {
        return "ShoppingListItem{" +
                "id=" + id +
                ", itemName='" + itemName + '\'' +
                ", quantity=" + quantity +
                ", notes='" + notes + '\'' +
                ", shoppingList=" + (shoppingList != null ? shoppingList.getId() : "null") +
                ", product=" + (product != null ? product.getProductId() : "null") +
                '}';
    }
}
