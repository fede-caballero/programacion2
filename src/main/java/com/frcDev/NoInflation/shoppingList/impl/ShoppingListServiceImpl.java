package com.frcDev.NoInflation.shoppingList.impl;
import com.frcDev.NoInflation.shoppingList.ShoppingList;
import com.frcDev.NoInflation.shoppingList.ShoppingListRepository;
import com.frcDev.NoInflation.shoppingList.ShoppingListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ShoppingListServiceImpl implements ShoppingListService {

    private final ShoppingListRepository shoppingListRepository;

    @Autowired
    public ShoppingListServiceImpl(ShoppingListRepository shoppingListRepository) {
        this.shoppingListRepository = shoppingListRepository;
    }

    @Override
    public List<ShoppingList> getAllShoppingLists() {
        return shoppingListRepository.findAll();
    }

    @Override
    public void createShoppingList(ShoppingList shoppingList) {
        shoppingListRepository.save(shoppingList);
    }

    @Override
    public ShoppingList getShoppingListById(Long shoppingListId) {
        return shoppingListRepository.findById(shoppingListId).orElse(null);
    }

    @Override
    public boolean updateShoppingList(ShoppingList shoppingList, Long shoppingListId) {
        Optional<ShoppingList> existingShoppingList = shoppingListRepository.findById(shoppingListId);
        if (existingShoppingList.isPresent()) {
            ShoppingList updatedList = existingShoppingList.get();
            updatedList.setListName(shoppingList.getListName());
            updatedList.setDescription(shoppingList.getDescription());
            shoppingListRepository.save(updatedList);
            return true;
        } else {
            return false;
        }
    }


    @Override
    public boolean deleteShoppingListById(Long shoppingListId) {
        if (shoppingListRepository.existsById(shoppingListId)) {
            shoppingListRepository.deleteById(shoppingListId);
            return true;
        }
        return false;
    }
}
