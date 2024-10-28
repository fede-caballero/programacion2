package com.frcDev.NoInflation.product;

import com.frcDev.NoInflation.dto.ProductDTO;
import com.frcDev.NoInflation.shop.Shop;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/products")
@CrossOrigin(origins = "*")
public class ProductController {
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public ResponseEntity<List<Product>> findAll() {
        return ResponseEntity.ok(productService.findAll());
    }

    @PostMapping
    public ResponseEntity<?> createProduct(@RequestBody ProductDTO productDTO) {
        try {
            if (!isValidProductData(productDTO)) {
                return ResponseEntity
                        .badRequest()
                        .body(Map.of("error", "Datos del producto inválidos"));
            }

            Product product = convertDTOToProduct(productDTO);
            Product createdProduct = productService.createProduct(product);

            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(Map.of(
                            "message", "Producto creado exitosamente",
                            "product", createdProduct
                    ));
        } catch (Exception e) {
            return handleException(e);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getProductById(@PathVariable Long id) {
        try {
            Product product = productService.getProductById(id);
            return ResponseEntity.ok(product);
        } catch (EntityNotFoundException e) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(Map.of("error", "Producto no encontrado"));
        } catch (Exception e) {
            return handleException(e);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable Long id) {
        try {
            productService.deleteProductById(id);
            return ResponseEntity.ok(Map.of("message", "Producto eliminado exitosamente"));
        } catch (EntityNotFoundException e) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(Map.of("error", "Producto no encontrado"));
        } catch (Exception e) {
            return handleException(e);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateProduct(
            @PathVariable Long id,
            @RequestBody ProductDTO updatedProductDTO) {
        try {
            if (!isValidProductData(updatedProductDTO)) {
                return ResponseEntity
                        .badRequest()
                        .body(Map.of("error", "Datos del producto inválidos"));
            }

            Product updatedProduct = convertDTOToProduct(updatedProductDTO);
            Product result = productService.updateProduct(id, updatedProduct);

            return ResponseEntity.ok(Map.of(
                    "message", "Producto actualizado exitosamente",
                    "product", result
            ));
        } catch (EntityNotFoundException e) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(Map.of("error", "Producto no encontrado"));
        } catch (Exception e) {
            return handleException(e);
        }
    }

    @GetMapping("/shop/{shopId}")
    public ResponseEntity<?> getProductsByShop(@PathVariable Long shopId) {
        try {
            List<Product> products = productService.getProductsByShopId(shopId);
            return ResponseEntity.ok(products);
        } catch (EntityNotFoundException e) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(Map.of("error", "Tienda no encontrada"));
        } catch (Exception e) {
            return handleException(e);
        }
    }

    private boolean isValidProductData(ProductDTO productDTO) {
        return productDTO != null
                && productDTO.getProductName() != null && !productDTO.getProductName().trim().isEmpty()
                && productDTO.getDescription() != null && !productDTO.getDescription().trim().isEmpty()
                && productDTO.getPrice() != null && productDTO.getPrice() > 0
                && productDTO.getCategory() != null
                && productDTO.getShop() != null && productDTO.getShop().getId() != null;
    }

    private Product convertDTOToProduct(ProductDTO productDTO) {
        Product product = new Product();
        product.setProductName(productDTO.getProductName().trim());
        product.setDescription(productDTO.getDescription().trim());
        product.setPrice(productDTO.getPrice());
        product.setLocation(productDTO.getLocation());
        product.setCategory(productDTO.getCategory());

        Shop shop = new Shop();
        shop.setId(productDTO.getShop().getId());
        product.setShop(shop);

        return product;
    }

    private ResponseEntity<?> handleException(Exception e) {
        e.printStackTrace();
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(Map.of("error", "Error interno del servidor: " + e.getMessage()));
    }
}