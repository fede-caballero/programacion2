package com.frcDev.NoInflation.shoppingList;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface ShoppingListRepository extends JpaRepository<ShoppingList, Long> {
    List<ShoppingList> findByUserId(Long userId); // Método para obtener todas las listas de un usuario
    Optional<ShoppingList> findByIdAndUserId(Long shoppingListId, Long userId); // Método para obtener una lista específica de un usuario
}
