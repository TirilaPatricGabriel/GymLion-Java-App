package repositories;

import classes.Product;
import config.DatabaseConfiguration;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductRepository {

    public void add(Product product) throws SQLException {
        String sql = "CALL INSERT_PRODUCT(?, ?)";
        try (Connection connection = DatabaseConfiguration.getConnection();
             CallableStatement stmt = connection.prepareCall(sql)) {
            stmt.registerOutParameter(1, Types.INTEGER);
            stmt.setDouble(2, product.getPrice());
            stmt.setString(3, product.getCode());
            stmt.execute();
        }
    }

    public Product get(int id) throws SQLException {
        String sql = "SELECT * FROM products WHERE productId = ?";
        try (Connection connection = DatabaseConfiguration.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Product(rs.getDouble("price"), rs.getString("code"));
            }
        }
        return null;
    }

    public void update(Product product) throws SQLException {
        String sql = "UPDATE products SET price = ?, code = ? WHERE productId = ?";
        try (Connection connection = DatabaseConfiguration.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setDouble(1, product.getPrice());
            stmt.setString(2, product.getCode());
            stmt.setInt(3, product.getId());
            stmt.executeUpdate();
        }
    }

    public void delete(int id) throws SQLException {
        String sql = "DELETE FROM products WHERE productId = ?";
        try (Connection connection = DatabaseConfiguration.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }

    public List<Product> getAll() throws SQLException {
        String sql = "SELECT * FROM products";
        List<Product> products = new ArrayList<>();
        try (Connection connection = DatabaseConfiguration.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                products.add(new Product(rs.getDouble("price"), rs.getString("code")));
            }
        }
        return products;
    }
}
