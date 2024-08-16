package com.frcDev.NoInflation.ShoppingListItem;

import java.util.List;

public interface ShoppingListItemService {
    List<ShoppingListItem> getAllItemsByShoppingListId(Long shoppingListId);

    void createShoppingListItem(ShoppingListItem shoppingListItem);

    ShoppingListItem getShoppingListItemById(Long id);

    boolean updateShoppingListItem(ShoppingListItem shoppingListItem, Long id);

    boolean deleteShoppingListItemById(Long id);
}
