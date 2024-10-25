package com.frcDev.NoInflation.product;

import java.util.List;

public interface ProductService {
    List<Product> findAll();
    void createProduct(Product product);

    Product getProductById(Long id);

    boolean updateProduct(Long id, Product updatedProduct);

    boolean deleteProductById(Long id);

    List<Product> getProductsByShopId(Long shopId);
}
