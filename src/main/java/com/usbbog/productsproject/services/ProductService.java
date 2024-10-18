package com.usbbog.productsproject.services;

import com.usbbog.productsproject.entities.ProductEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ProductService {
    private List<ProductEntity> productos;

    public ProductService() {
        productos = new ArrayList<>();
        productos.add(new ProductEntity(UUID.randomUUID(),"Laptop","Electrónica",1000.00, 50));
        productos.add(new ProductEntity(UUID.randomUUID(),"Silla","Muebles",150.00, 30));
        productos.add(new ProductEntity(UUID.randomUUID(),"Cámara","Fotografía",500.00, 15));
        productos.add(new ProductEntity(UUID.randomUUID(),"Smartphone","Electrónica",800.00, 40));
        productos.add(new ProductEntity(UUID.randomUUID(),"Mesa de comedor","Muebles",250.00, 20));
        productos.add(new ProductEntity(UUID.randomUUID(),"Televisor","Electrónica",1200.00, 25));
        productos.add(new ProductEntity(UUID.randomUUID(),"Cafetera","Electrodomésticos",75.00, 60));
        productos.add(new ProductEntity(UUID.randomUUID(),"Microondas","Electrodomésticos",200.00, 45));
        productos.add(new ProductEntity(UUID.randomUUID(),"Zapatillas deportivas","Deportes",120.00, 80));
        productos.add(new ProductEntity(UUID.randomUUID(),"Auriculares","Electrónica",100.00, 80));
        productos.add(new ProductEntity(UUID.randomUUID(),"Lámpara de pie","Muebles",90.00, 40));
        productos.add(new ProductEntity(UUID.randomUUID(),"Refrigerador","Electrodomésticos",1300.00, 15));
        productos.add(new ProductEntity(UUID.randomUUID(),"Monitor","Electrónica",300.00, 50));
        productos.add(new ProductEntity(UUID.randomUUID(),"Bicicleta","Deportes",400.00, 20));
        productos.add(new ProductEntity(UUID.randomUUID(),"Sofá","Muebles",500.00, 10));
        productos.add(new ProductEntity(UUID.randomUUID(),"Reloj inteligente","Electrónica",250.00, 70));
        productos.add(new ProductEntity(UUID.randomUUID(),"Tablet","Electrónica",600.00, 55));
        productos.add(new ProductEntity(UUID.randomUUID(),"Colchón","Muebles",350.00, 25));
        productos.add(new ProductEntity(UUID.randomUUID(),"Mesa para TV","Muebles",280.00, 15));
        productos.add(new ProductEntity(UUID.randomUUID(),"Escritorio","Muebles",180.00, 35));
        productos.add(new ProductEntity(UUID.randomUUID(),"Tostadora","Electrodomésticos",50.00, 60));
        productos.add(new ProductEntity(UUID.randomUUID(),"Licuadora","Electrodomésticos",90.00, 55));
        productos.add(new ProductEntity(UUID.randomUUID(),"Elíptica","Deportes",600.00, 20));
        productos.add(new ProductEntity(UUID.randomUUID(),"Placa base","Electrónica",250.00, 30));
        productos.add(new ProductEntity(UUID.randomUUID(),"Parlantes","Electrónica",150.00, 70));
        productos.add(new ProductEntity(UUID.randomUUID(),"Cámara profesional","Fotografía",2000.00, 10));
        productos.add(new ProductEntity(UUID.randomUUID(),"Aspiradora","Electrodomésticos",300.00, 25));
        productos.add(new ProductEntity(UUID.randomUUID(),"Teclado mecánico","Electrónica",80.00, 60));
        productos.add(new ProductEntity(UUID.randomUUID(),"Cinta de correr","Deportes",900.00, 15));
        productos.add(new ProductEntity(UUID.randomUUID(),"Batidora de mano","Electrodomésticos",50.00, 70));
    }

    public ResponseEntity<Map<String, Object>> getAllProducts() {
        Map<String, Object> response = new LinkedHashMap<>();
        // Obtener la lista de productos
        List<ProductEntity> productsList = productos;
        response.put("TotalProducts", productsList.size());
        response.put("Products", productsList);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    public ResponseEntity<Map<String, Object>> getProductById(UUID id) {
        Map<String, Object> response = new HashMap<>();
        Optional<ProductEntity> productFound = productos.stream().filter(product -> product.getId().equals(id)).findFirst();
        if (productFound.isPresent()) {
            response.put("Product", productFound.get());
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            response.put("Error", "Producto no encontrado");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }

    public ResponseEntity<Map<String, Object>> createProduct(ProductEntity product) {
        Map<String, Object> response = new HashMap<>();
        // Generar un nuevo UUID para el producto
        product.setId(UUID.randomUUID());

        // Verificar si ya existe un producto con ese UUID
        if (productos.stream().anyMatch(existingProduct -> existingProduct.getId().equals(product.getId()))) {
            response.put("Status", "El producto ya existe");
            return new ResponseEntity<>(response, HttpStatus.CONFLICT);
        } else {
            // Agregar el producto a la lista
            productos.add(product);
            response.put("Status", "Producto insertado exitosamente");
            response.put("Product", product);
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        }
    }

    // Metodo que devuelve el producto como Optional<ProductEntity> para updateProduct y deleteProduct
    private Optional<ProductEntity> findProductById(UUID id) {
        return productos.stream().filter(product -> product.getId().equals(id)).findFirst();
    }

    public ResponseEntity<Map<String, Object>> updateProduct(UUID id, ProductEntity updatedProduct) {
        Map<String, Object> response = new HashMap<>();
        Optional<ProductEntity> productFound = findProductById(id);
        if (productFound.isPresent()) {
            ProductEntity existingProduct = productFound.get();

            // Actualizar los campos del producto
            existingProduct.setName(updatedProduct.getName());
            existingProduct.setCategory(updatedProduct.getCategory());
            existingProduct.setPrice(updatedProduct.getPrice());
            existingProduct.setStock(updatedProduct.getStock());

            response.put("Status", "Producto actualizado con éxito");
            response.put("UpdatedProduct", existingProduct);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            response.put("Status", "Producto no encontrado");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }

    public ResponseEntity<Map<String, Object>> deleteProduct(UUID id) {
        Map<String, Object> response = new HashMap<>();
        Optional<ProductEntity> productFound = findProductById(id);

        if (productFound.isPresent()) {
            productos.remove(productFound.get());  // Eliminar el producto
            response.put("Status", "Producto eliminado con éxito");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            response.put("Status", "Producto no encontrado");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }
}
