package com.frcDev.NoInflation.shoppingList.impl;

import com.frcDev.NoInflation.ShoppingListItem.ShoppingListItem;
import com.frcDev.NoInflation.ShoppingListItem.ShoppingListItemRepository;
import com.frcDev.NoInflation.product.Product;
import com.frcDev.NoInflation.product.ProductRepository;
import com.frcDev.NoInflation.shoppingList.ShoppingList;
import com.frcDev.NoInflation.shoppingList.ShoppingListRepository;
import com.frcDev.NoInflation.shoppingList.ShoppingListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class ShoppingListServiceImpl implements ShoppingListService {
    @Autowired
    private ShoppingListRepository shoppingListRepository;

    @Autowired
    private ShoppingListItemRepository shoppingListItemRepository;

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    public ShoppingListServiceImpl(ShoppingListRepository shoppingListRepository,
                                   ShoppingListItemRepository shoppingListItemRepository) {
        this.shoppingListRepository = shoppingListRepository;
        this.shoppingListItemRepository = shoppingListItemRepository;
    }

    @Override
    public List<ShoppingList> getAllShoppingLists() {
        List<ShoppingList> lists = shoppingListRepository.findAll();
        return lists.stream()
                .filter(list -> list != null && list.getListName() != null && !list.getListName().trim().isEmpty())
                .collect(Collectors.toList());
    }

    @Override
    public List<ShoppingList> getAllShoppingListsByUser(Long userId) {
        List<ShoppingList> lists = shoppingListRepository.findByUserId(userId);
        return lists.stream()
                .filter(list -> list != null && list.getListName() != null && !list.getListName().trim().isEmpty())
                .collect(Collectors.toList());
    }

    @Transactional
    public ShoppingList createShoppingList(ShoppingList shoppingList) {
        if (shoppingList.getItems() == null) {
            shoppingList.setItems(new ArrayList<>());
        }
        return shoppingListRepository.save(shoppingList);
    }

    @Override
    public ShoppingList getShoppingListById(Long shoppingListId) {
        return shoppingListRepository.findById(shoppingListId)
                .filter(list -> list.getListName() != null && !list.getListName().trim().isEmpty())
                .orElse(null);
    }

    @Override
    public ShoppingList getShoppingListByIdAndUser(Long shoppingListId, Long userId) {
        return shoppingListRepository.findByIdAndUserId(shoppingListId, userId)
                .filter(list -> list.getListName() != null && !list.getListName().trim().isEmpty())
                .orElse(null);
    }

    @Override
    public boolean updateShoppingList(ShoppingList shoppingList, Long shoppingListId, Long userId) {
        Optional<ShoppingList> existingList = shoppingListRepository.findByIdAndUserId(shoppingListId, userId);
        if (existingList.isPresent()) {
            ShoppingList updatedList = existingList.get();
            updatedList.setListName(shoppingList.getListName());
            updatedList.setDescription(shoppingList.getDescription());
            shoppingListRepository.save(updatedList);
            return true;
        }
        return false;
    }

    @Override
    public boolean deleteShoppingListById(Long shoppingListId) {
        if (shoppingListRepository.existsById(shoppingListId)) {
            shoppingListRepository.deleteById(shoppingListId);
            return true;
        }
        return false;
    }

    @Override
    public boolean deleteShoppingListByIdAndUser(Long shoppingListId, Long userId) {
        Optional<ShoppingList> shoppingList = shoppingListRepository.findByIdAndUserId(shoppingListId, userId);
        if (shoppingList.isPresent()) {
            shoppingListRepository.delete(shoppingList.get());
            return true;
        }
        return false;
    }
    @Override
    public ShoppingListItem addItemToList(Long userId, Long listId, ShoppingListItem item) {
        ShoppingList shoppingList = shoppingListRepository.findByIdAndUserId(listId, userId)
                .orElseThrow(() -> new RuntimeException("Lista de compras no encontrada"));

        // Si hay un producto asociado, verificarlo
        if (item.getProduct() != null && item.getProduct().getProductId() != null) {
            Product product = productRepository.findById(item.getProduct().getProductId())
                    .orElseThrow(() -> new RuntimeException("Producto no encontrado"));
            item.setProduct(product);
        }

        // Establecer la lista de compras
        item.setShoppingList(shoppingList);

        // Validar datos requeridos
        if (item.getItemName() == null || item.getItemName().trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre del item es requerido");
        }

        if (item.getQuantity() == null || item.getQuantity() < 1) {
            item.setQuantity(1);
        }

        // Guardar el item
        return shoppingListItemRepository.save(item);
    }

    @Override
    @Transactional
    public boolean removeItemFromList(Long userId, Long listId, Long itemId) {
        Optional<ShoppingList> shoppingList = shoppingListRepository.findByIdAndUserId(listId, userId);
        if (!shoppingList.isPresent()) {
            return false;
        }

        Optional<ShoppingListItem> item = shoppingListItemRepository.findById(itemId);
        if (!item.isPresent() || !item.get().getShoppingList().getId().equals(listId)) {
            return false;
        }

        shoppingListItemRepository.deleteById(itemId);
        return true;
    }

    @Override
    public boolean updateItemQuantity(Long userId, Long listId, Long itemId, Integer quantity) {
        return shoppingListItemRepository.findById(itemId)
                .filter(item -> item.getShoppingList().getId().equals(listId))
                .map(item -> {
                    item.setQuantity(quantity);
                    shoppingListItemRepository.save(item);
                    return true;
                })
                .orElse(false);
    }
}