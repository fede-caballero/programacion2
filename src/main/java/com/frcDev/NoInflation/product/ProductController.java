package com.frcDev.NoInflation.product;
import java.util.List;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/products")
public class ProductController {
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public ResponseEntity<List<Product>> findAll(){
        return ResponseEntity.ok(productService.findAll());
    }

    /*
    @CrossOrigin(origins = "http://localhost:8081")
    @PostMapping
    public ResponseEntity<String> createProduct(@RequestBody Product product){
        productService.createProduct(product);
        return new ResponseEntity<>("Product added successfully", HttpStatus.CREATED);
    }
    */
    // Nuevo createProduct
    @CrossOrigin(origins = "*")
    @PostMapping
    public ResponseEntity<String> createProduct(@RequestBody Product product) {
        System.out.println("Received request body: " + product);
        System.out.println("Product name: " + product.getProductName());
        System.out.println("Shop info: " + (product.getShop() != null ? "ID: " + product.getShop().getId() : "null"));
        try {
            // Log para debugging
            System.out.println("Received product data: " + product);
            System.out.println("Shop ID: " + (product.getShop() != null ? product.getShop().getId() : "null"));

            if (product.getShop() == null || product.getShop().getId() == null) {
                return new ResponseEntity<>("Se requiere información de la tienda", HttpStatus.BAD_REQUEST);
            }

            productService.createProduct(product);
            return new ResponseEntity<>("Producto creado exitosamente", HttpStatus.CREATED);

        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>("Error en los datos del producto: " + e.getMessage(),
                    HttpStatus.BAD_REQUEST);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>("Error: " + e.getMessage(),
                    HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            System.err.println("Error creating product: " + e.getMessage());
            e.printStackTrace();
            return new ResponseEntity<>("Error interno del servidor: " + e.getMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable Long id) {
        Product product = productService.getProductById(id);
        if (product != null)
            return new ResponseEntity<>(product, HttpStatus.OK);
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable Long id) {
        boolean deleted = productService.deleteProductById(id);
        if (deleted)
            return new ResponseEntity<>("Product deleted successfully", HttpStatus.OK);
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateJob(@PathVariable Long id, @RequestBody Product updatedProduct) {
        boolean updated = productService.updateProduct(id, updatedProduct);
        if (updated)
            return new ResponseEntity<>("Product updated successfully", HttpStatus.OK);
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/shop/{shopId}")
    public ResponseEntity<List<Product>> getProductsByShop(@PathVariable Long shopId) {
        try {
            List<Product> products = productService.getProductsByShopId(shopId);
            return new ResponseEntity<>(products, HttpStatus.OK);
        } catch (Exception e) {
            System.err.println("Error fetching products for shop: " + e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
