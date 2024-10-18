package com.usbbog.productsproject.controllers;

import com.usbbog.productsproject.entities.ProductEntity;
import com.usbbog.productsproject.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/products")
public class ProductController {
    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    // Metodo para obtener todos los productos
    @GetMapping
    public ResponseEntity<Map<String, Object>> getAllProducts() {
        return productService.getAllProducts();
    }

    @GetMapping("/{id}")
    // Metodo para obtener un producto por id
    public ResponseEntity<?> getProduct(@PathVariable UUID id){
        return productService.getProductById(id);
    }

    @PostMapping
    // Metodo para crear un producto
    public ResponseEntity<?> createProduct(@RequestBody ProductEntity newProduct) {
        return productService.createProduct(newProduct);
    }

    @PutMapping("/{id}")
    // Metodo para actualizar un producto
    public ResponseEntity<Map<String, Object>> updateProduct(
            @PathVariable UUID id, @RequestBody ProductEntity updatedProduct) {
        return productService.updateProduct(id, updatedProduct);
    }

    @DeleteMapping("/{id}")
    // Metodo para eliminar un producto
    public ResponseEntity<Map<String, Object>> deleteProduct(@PathVariable UUID id) {
        return productService.deleteProduct(id);
    }
}

/*  En Postman en "Headers" agregar:
    Key: Content-Type
    Value: application/json

    Ejemplo de JSON con los detalles del producto:
    {
        "name": "Auriculares Bluetooth",
        "category": "Electrónica",
        "price": 150.00,
        "stock": 50
    }
 */
