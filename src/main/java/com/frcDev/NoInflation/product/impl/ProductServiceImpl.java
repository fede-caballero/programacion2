package com.frcDev.NoInflation.product.impl;

import com.frcDev.NoInflation.product.Product;
import com.frcDev.NoInflation.product.ProductRepository;
import com.frcDev.NoInflation.product.ProductService;
import com.frcDev.NoInflation.shop.Shop;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.frcDev.NoInflation.shop.ShopRepository;

import java.util.List;

@Service
@Transactional
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final ShopRepository shopRepository;

    public ProductServiceImpl(ProductRepository productRepository, ShopRepository shopRepository) {
        this.productRepository = productRepository;
        this.shopRepository = shopRepository;
    }

    @Override
    public List<Product> findAll() {
        return productRepository.findAll();
    }

    @Override
    public Product createProduct(Product product) {
        if (!product.isValid()) {
            throw new IllegalArgumentException("Información del producto incompleta o inválida");
        }

        // Verificar que la tienda existe
        Shop shop = shopRepository.findById(product.getShop().getId())
                .orElseThrow(() -> new EntityNotFoundException(
                        "No se encontró la tienda con id: " + product.getShop().getId()));

        // Asignar la tienda verificada al producto
        product.setShop(shop);

        // Guardar y retornar el producto
        return productRepository.save(product);
    }

    @Override
    public Product getProductById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(
                        "No se encontró el producto con id: " + id));
    }

    @Override
    public Product updateProduct(Long id, Product updatedProduct) {
        Product existingProduct = getProductById(id);

        // Validar el producto actualizado
        if (!updatedProduct.isValid()) {
            throw new IllegalArgumentException("Información del producto actualizado inválida");
        }

        // Actualizar los campos permitidos
        existingProduct.setProductName(updatedProduct.getProductName());
        existingProduct.setDescription(updatedProduct.getDescription());
        existingProduct.setPrice(updatedProduct.getPrice());
        existingProduct.setLocation(updatedProduct.getLocation());
        existingProduct.setCategory(updatedProduct.getCategory());

        // Guardar y retornar el producto actualizado
        return productRepository.save(existingProduct);
    }

    @Override
    public void deleteProductById(Long id) {
        // Verificar que el producto existe antes de eliminarlo
        Product product = getProductById(id);
        productRepository.delete(product);
    }

    @Override
    public List<Product> getProductsByShopId(Long shopId) {
        // Verificar que la tienda existe
        Shop shop = shopRepository.findById(shopId)
                .orElseThrow(() -> new EntityNotFoundException(
                        "No se encontró la tienda con id: " + shopId));

        return productRepository.findByShopId(shopId);
    }
}