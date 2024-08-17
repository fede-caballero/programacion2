package com.frcDev.NoInflation.shoppingList;

import java.util.List;

public interface ShoppingListService {
    List<ShoppingList> getAllShoppingLists();
    List<ShoppingList> getAllShoppingListsByUser(Long userId); // Nuevo método
    void createShoppingList(ShoppingList shoppingList);
    ShoppingList getShoppingListById(Long shoppingListId);
    ShoppingList getShoppingListByIdAndUser(Long shoppingListId, Long userId); // Nuevo método
    boolean updateShoppingList(ShoppingList shoppingList, Long shoppingListId, Long userId); // Modificado
    boolean deleteShoppingListById(Long shoppingListId);
    boolean deleteShoppingListByIdAndUser(Long shoppingListId, Long userId); // Nuevo método
}
