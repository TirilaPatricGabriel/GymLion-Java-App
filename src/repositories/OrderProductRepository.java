package repositories;

import classes.OrderProduct;
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
        }
    }

    public List<OrderProduct> getProductsByOrderId(int orderId) throws SQLException {
        String sql = "SELECT * FROM order_products WHERE orderId = ?";
        List<OrderProduct> orderProducts = new ArrayList<>();
        try (Connection connection = DatabaseConfiguration.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, orderId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                orderProducts.add(new OrderProduct(rs.getInt("orderId"), rs.getInt("productId")));
            }
        }
        return orderProducts;
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
        }
    }

    public void removeAllProductsFromOrder(int orderId) throws SQLException {
        String sql = "DELETE FROM order_products WHERE orderId = ?";
        try (Connection connection = DatabaseConfiguration.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, orderId);
            stmt.executeUpdate();
        }
    }
}
