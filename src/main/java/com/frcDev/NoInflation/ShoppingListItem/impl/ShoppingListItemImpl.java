package com.frcDev.NoInflation.ShoppingListItem.impl;

import com.frcDev.NoInflation.ShoppingListItem.ShoppingListItem;
import com.frcDev.NoInflation.ShoppingListItem.ShoppingListItemRepository;
import com.frcDev.NoInflation.ShoppingListItem.ShoppingListItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ShoppingListItemImpl implements ShoppingListItemService {
    private final ShoppingListItemRepository shoppingListItemRepository;

    @Autowired
    public ShoppingListItemImpl(ShoppingListItemRepository shoppingListItemRepository) {
        this.shoppingListItemRepository = shoppingListItemRepository;
    }

    @Override
    public List<ShoppingListItem> getAllItemsByShoppingListId(Long shoppingListId) {
        return shoppingListItemRepository.findByShoppingList_Id(shoppingListId);
    }

    @Override
    public void createShoppingListItem(ShoppingListItem shoppingListItem) {
        shoppingListItemRepository.save(shoppingListItem);
    }

    @Override
    public ShoppingListItem getShoppingListItemById(Long id) {
        return shoppingListItemRepository.findById(id).orElse(null);
    }

    @Override
    public boolean updateShoppingListItem(ShoppingListItem shoppingListItem, Long id) {
        Optional<ShoppingListItem> existingItem = shoppingListItemRepository.findById(id);
        if (existingItem.isPresent()) {
            ShoppingListItem updatedItem = existingItem.get();
            updatedItem.setItemName(shoppingListItem.getItemName());
            updatedItem.setQuantity(shoppingListItem.getQuantity());
            updatedItem.setNotes(shoppingListItem.getNotes());
            shoppingListItemRepository.save(updatedItem);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean deleteShoppingListItemById(Long id) {
        if (shoppingListItemRepository.existsById(id)) {
            shoppingListItemRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
