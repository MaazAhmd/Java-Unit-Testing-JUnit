package main;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ProductService {
    private final List<Product> products = new ArrayList<>();

    public ProductService() {
        // Initialize with some demo data
        products.add(new Product(1, "Product1", "Description1", 10.99));
        products.add(new Product(2, "Product2", "Description2", 20.49));
        products.add(new Product(3, "Product3", "Description3", 15.75));
    }

    // Retrieve all products
    public List<Product> getProducts() {
        return new ArrayList<>(products);
    }

    // Find product by ID
    public Optional<Product> getProductById(int id) {
        return products.stream().filter(product -> product.getId() == id).findFirst();
    }

    // Add a new product
    public boolean addProduct(Product product) {
        if (products.stream().anyMatch(p -> p.getId() == product.getId())) {
            return false; // Duplicate ID
        }
        products.add(product);
        return true;
    }

    // Update an existing product
    public boolean updateProduct(int id, Product updatedProduct) {
        Optional<Product> existingProduct = getProductById(id);
        if (existingProduct.isPresent()) {
            Product product = existingProduct.get();
            product.setName(updatedProduct.getName());
            product.setDescription(updatedProduct.getDescription());
            product.setPrice(updatedProduct.getPrice());
            return true;
        }
        return false;
    }

    // Delete a product by ID
    public boolean deleteProduct(int id) {
        return products.removeIf(product -> product.getId() == id);
    }
}

