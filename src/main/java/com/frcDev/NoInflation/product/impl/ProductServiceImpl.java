package com.frcDev.NoInflation.product.impl;

import com.frcDev.NoInflation.product.Product;
import com.frcDev.NoInflation.product.ProductRepository;
import com.frcDev.NoInflation.product.ProductService;
import com.frcDev.NoInflation.shop.Shop;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import com.frcDev.NoInflation.shop.ShopRepository;

import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {
    //private List<Product> products = new ArrayList<>();
    ProductRepository productRepository;
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
    public void createProduct(Product product){
        if (!product.isValid()) {
            throw new IllegalArgumentException("Informacion del producto incompleta o invalida");
        }

        Shop shop = shopRepository.findById(product.getShop().getId())
                        .orElseThrow(() -> new EntityNotFoundException("No se encontro la tienda con id:" +product.getShop().getId()));
        productRepository.save(product);
    }

    @Override
    public Product getProductById(Long id) {
        return productRepository.findById(id).orElse(null);
    }

    @Override
    public boolean updateProduct(Long id, Product updatedProduct) {
        Optional<Product> productOptional = productRepository.findById(id);
        if (productOptional.isPresent()) {
            Product product = productOptional.get();
                product.setProductName(updatedProduct.getProductName());
                product.setDescription(updatedProduct.getDescription());
                product.setPrice(updatedProduct.getPrice());
                product.setLocation(updatedProduct.getLocation());
                productRepository.save(product);
                return true;
        }
        return false;
    }

    @Override
    public boolean deleteProductById(Long id) {
        try {
            productRepository.deleteById(id);
            return true;
        } catch (Exception e){
            return false;
        }

    }

    @Override
    public List<Product> getProductsByShopId(Long shopId) {
        return productRepository.findByShopId(shopId);
    }

}
