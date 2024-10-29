package com.frcDev.NoInflation.product.impl;

import com.frcDev.NoInflation.product.Product;
import com.frcDev.NoInflation.product.ProductRepository;
import com.frcDev.NoInflation.product.ProductService;
import com.frcDev.NoInflation.shop.Shop;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.frcDev.NoInflation.shop.ShopRepository;
import org.springframework.stereotype.Service;
import java.util.*;
import java.util.stream.Collectors;

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

    @Override
    public List<Map<String, Object>> compareShoppingListPrices(Long shoppingListId) {
        // Aquí deberías obtener la lista de productos de la lista de compras
        List<Product> shoppingListProducts = getProductsFromShoppingList(shoppingListId);

        // Obtener lista de todos los comercios con estos productos
        List<Shop> shops = shopRepository.findAll();

        List<Map<String, Object>> comparisons = new ArrayList<>();

        for (Shop shop : shops) {
            double totalPrice = 0;
            int availableItems = 0;
            List<Map<String, Object>> items = new ArrayList<>();

            for (Product product : shoppingListProducts) {
                Product shopProduct = findProductInShop(shop, product.getProductName());

                if (shopProduct != null) {
                    double itemTotal = shopProduct.getPrice() * 1; // Cambia el 1 por la cantidad necesaria
                    totalPrice += itemTotal;
                    availableItems++;

                    items.add(Map.of(
                            "itemName", shopProduct.getProductName(),
                            "quantity", 1, // Asume cantidad 1
                            "price", shopProduct.getPrice(),
                            "total", itemTotal,
                            "available", true
                    ));
                } else {
                    items.add(Map.of(
                            "itemName", product.getProductName(),
                            "quantity", 1,
                            "price", null,
                            "total", null,
                            "available", false
                    ));
                }
            }

            comparisons.add(Map.of(
                    "shopId", shop.getId(),
                    "shopName", shop.getShopName(),
                    "totalPrice", totalPrice,
                    "availableItems", availableItems,
                    "items", items
            ));
        }

        return comparisons;
    }

    @Override
    public Map<String, Object> getBestShoppingOption(Long shoppingListId) {
        List<Map<String, Object>> comparisons = compareShoppingListPrices(shoppingListId);

        return comparisons.stream()
                .min(Comparator.comparingDouble(comp -> (Double) comp.get("totalPrice")))
                .orElse(null);
    }

    private Product findProductInShop(Shop shop, String productName) {
        return productRepository.findByShopId(shop.getId())
                .stream()
                .filter(product -> product.getProductName().equalsIgnoreCase(productName))
                .findFirst()
                .orElse(null);
    }

    private List<Product> getProductsFromShoppingList(Long shoppingListId) {
        // Lógica para obtener los productos en la lista de compras según shoppingListId
        return List.of(); // Reemplaza por la lógica real
    }

    private void validateProduct(Product product) {
        if (product.getProductName() == null || product.getProductName().trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre del producto es requerido");
        }
        if (product.getPrice() == null || product.getPrice() <= 0) {
            throw new IllegalArgumentException("El precio debe ser mayor a 0");
        }
        if (product.getShop() == null || product.getShop().getId() == null) {
            throw new IllegalArgumentException("La tienda es requerida");
        }
    }
}
