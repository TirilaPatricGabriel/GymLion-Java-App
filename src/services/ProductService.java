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

    public void registerNewEntity(double price, String code) throws InvalidDataException, SQLException {
        if (price <= 0) {
            throw new InvalidDataException("Price must be greater than zero.");
        }
        if (code == null || code.trim().isEmpty()) {
            throw new InvalidDataException("Code cannot be null or empty.");
        }

        Product product = new Product(price, code);
        productRepository.add(product);
    }

    public Product get(int id) throws InvalidDataException, SQLException {
        if (id <= 0) {
            throw new InvalidDataException("Invalid product ID.");
        }
        return productRepository.get(id);
    }

    public void update(int id) throws InvalidDataException, SQLException {
        if (id <= 0) {
            throw new InvalidDataException("Invalid product ID.");
        }

        Product product = productRepository.get(id);
        productRepository.update(product);
    }

    public void delete(int id) throws InvalidDataException, SQLException {
        if (id <= 0) {
            throw new InvalidDataException("Invalid product ID.");
        }
        productRepository.delete(id);
    }

    public List<Product> getAllProducts() throws SQLException {
        return productRepository.getAll();
    }
}
