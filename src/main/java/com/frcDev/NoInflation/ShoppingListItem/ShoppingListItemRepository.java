package com.frcDev.NoInflation.ShoppingListItem;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
public interface ShoppingListItemRepository extends JpaRepository<ShoppingListItem, Long> {
    List<ShoppingListItem> findByShoppingList_Id(Long shoppingListId);
    List<ShoppingListItem> findByShoppingListId(Long shoppingListId);
}
