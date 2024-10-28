package com.frcDev.NoInflation.product;

import java.util.List;

public interface ProductService {
    List<Product> findAll();

    Product createProduct(Product product);

    Product getProductById(Long id);

    Product updateProduct(Long id, Product updatedProduct);

    void deleteProductById(Long id);

    List<Product> getProductsByShopId(Long shopId);
}