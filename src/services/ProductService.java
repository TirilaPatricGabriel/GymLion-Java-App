package services;

import classes.Product;
import exceptions.InvalidDataException;
import repositories.ProductRepository;

import java.sql.SQLException;
import java.util.List;

public class ProductService {

    private ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public void addProduct(double price, String code) throws InvalidDataException, SQLException {
        if (price <= 0) {
            throw new InvalidDataException("Price must be greater than zero.");
        }
        if (code == null || code.trim().isEmpty()) {
            throw new InvalidDataException("Code cannot be null or empty.");
        }

        Product product = new Product(price, code);
        productRepository.add(product);
    }

    public Product getProduct(int id) throws InvalidDataException, SQLException {
        if (id <= 0) {
            throw new InvalidDataException("Invalid product ID.");
        }
        return productRepository.get(id);
    }

    public void updateProduct(int id, double price, String code) throws InvalidDataException, SQLException {
        if (id <= 0) {
            throw new InvalidDataException("Invalid product ID.");
        }
        if (price <= 0) {
            throw new InvalidDataException("Price must be greater than zero.");
        }
        if (code == null || code.trim().isEmpty()) {
            throw new InvalidDataException("Code cannot be null or empty.");
        }

        Product product = new Product(price, code);
        productRepository.update(product);
    }

    public void deleteProduct(int id) throws InvalidDataException, SQLException {
        if (id <= 0) {
            throw new InvalidDataException("Invalid product ID.");
        }
        productRepository.delete(id);
    }

    public List<Product> getAllProducts() throws SQLException {
        return productRepository.getAll();
    }
}
