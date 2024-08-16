package com.frcDev.NoInflation.shoppingList;

import java.util.List;

public interface ShoppingListService {
    List<ShoppingList> getAllShoppingLists();
    void createShoppingList(ShoppingList shoppingList);
    ShoppingList getShoppingListById(Long shoppingListId);
    boolean updateShoppingList(ShoppingList shoppingList, Long shoppingListId);
    boolean deleteShoppingListById(Long shoppingListId);
}
