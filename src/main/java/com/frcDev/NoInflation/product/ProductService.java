package com.frcDev.NoInflation.product;

import java.util.List;
import java.util.Map;


public interface ProductService {
    List<Product> findAll();

    Product createProduct(Product product);

    Product getProductById(Long id);

    Product updateProduct(Long id, Product updatedProduct);

    void deleteProductById(Long id);

    List<Product> getProductsByShopId(Long shopId);
    List<Map<String, Object>> compareShoppingListPrices(Long shoppingListId);
    Map<String, Object> getBestShoppingOption(Long shoppingListId);
}