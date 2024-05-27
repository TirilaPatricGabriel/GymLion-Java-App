package repositories;

import classes.OrderProduct;
import classes.Product;
import config.DatabaseConfiguration;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OrderProductRepository {

    public void addOrderProduct(OrderProduct orderProduct) throws SQLException {
        String sql = "CALL INSERT_ORDER_PRODUCT(?, ?)";
        try (Connection connection = DatabaseConfiguration.getConnection();
             CallableStatement stmt = connection.prepareCall(sql)) {
            stmt.setInt(1, orderProduct.getOrderId());
            stmt.setInt(2, orderProduct.getProductId());
            stmt.execute();
        } catch (SQLException e) {
            throw new SQLException("Failed to connect product to order.", e);
        }
    }

    public List<Product> getProductsByOrderId(int orderId) throws SQLException {
        String sql = "SELECT p.* FROM order_products op " +
                "JOIN products p ON op.productId = p.productId " +
                "WHERE op.orderId = ?";
        List<Product> products = new ArrayList<>();
        try (Connection connection = DatabaseConfiguration.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setInt(1, orderId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                int productId = rs.getInt("productId");
                double price = rs.getDouble("price");
                String code = rs.getString("code");

                Product product = new Product(price, code);
                product.setId(productId);

                products.add(product);
            }
        } catch (SQLException e) {
            throw new SQLException("Failed to get products by order id.", e);
        }
        return products;
    }

    public List<OrderProduct> getOrdersByProductId(int productId) throws SQLException {
        String sql = "SELECT * FROM order_products WHERE productId = ?";
        List<OrderProduct> orderProducts = new ArrayList<>();
        try (Connection connection = DatabaseConfiguration.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, productId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                orderProducts.add(new OrderProduct(rs.getInt("orderId"), rs.getInt("productId")));
            }
        } catch (SQLException e) {
            throw new SQLException("Failed to get orders by product id.", e);
        }
        return orderProducts;
    }

    public void removeOrderProduct(OrderProduct orderProduct) throws SQLException {
        String sql = "DELETE FROM order_products WHERE orderId = ? AND productId = ?";
        try (Connection connection = DatabaseConfiguration.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, orderProduct.getOrderId());
            stmt.setInt(2, orderProduct.getProductId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new SQLException("Failed to remove product from order.", e);
        }
    }

    public void removeAllProductsFromOrder(int orderId) throws SQLException {
        String sql = "DELETE FROM order_products WHERE orderId = ?";
        try (Connection connection = DatabaseConfiguration.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, orderId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new SQLException("Failed to remove all products from order.", e);
        }
    }
}
