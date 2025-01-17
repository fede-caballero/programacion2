package com.frcDev.NoInflation.shoppingList;

import com.frcDev.NoInflation.ShoppingListItem.ShoppingListItem;
import java.util.List;

public interface ShoppingListService {
    // Métodos para ShoppingList
    List<ShoppingList> getAllShoppingLists();
    List<ShoppingList> getAllShoppingListsByUser(Long userId);
    ShoppingList createShoppingList(ShoppingList shoppingList);
    ShoppingList getShoppingListById(Long shoppingListId);
    ShoppingList getShoppingListByIdAndUser(Long shoppingListId, Long userId);
    boolean updateShoppingList(ShoppingList shoppingList, Long shoppingListId, Long userId);
    boolean deleteShoppingListById(Long shoppingListId);
    boolean deleteShoppingListByIdAndUser(Long shoppingListId, Long userId);

    // Métodos para ShoppingListItem
    ShoppingListItem addItemToList(Long userId, Long listId, ShoppingListItem newItem);
    boolean removeItemFromList(Long userId, Long listId, Long itemId);

    boolean updateItemQuantity(Long userId, Long listId, Long itemId, Integer quantity);



}
