package repositories;

import classes.GymMembership;
import classes.Product;
import config.DatabaseConfiguration;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ProductRepository {

    private static Scanner scanner = new Scanner(System.in);

    public void add(Product product) throws SQLException {
        String sql = "CALL INSERT_PRODUCT(?, ?, ?)";
        try (Connection connection = DatabaseConfiguration.getConnection();
             CallableStatement stmt = connection.prepareCall(sql)) {
            stmt.registerOutParameter(1, Types.INTEGER);
            stmt.setDouble(2, product.getPrice());
            stmt.setString(3, product.getCode());
            stmt.execute();
        } catch (SQLException e) {
            e.printStackTrace();
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

        System.out.println("Code:");
        String code = scanner.nextLine();
        System.out.println("Price:");
        Double price = Double.parseDouble(scanner.nextLine());

        try (Connection connection = DatabaseConfiguration.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setDouble(1, price);
            stmt.setString(2, code);
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

//    public List<Product> getAll() throws SQLException {
//        String sql = "SELECT * FROM products";
//        List<Product> products = new ArrayList<>();
//        try (Connection connection = DatabaseConfiguration.getConnection();
//             PreparedStatement stmt = connection.prepareStatement(sql)) {
//            ResultSet rs = stmt.executeQuery();
//            while (rs.next()) {
//                products.add(new Product(rs.getDouble("price"), rs.getString("code")));
//            }
//        }
//        return products;
//    }

    public List<Product> getAll() throws SQLException {
        String sql = "SELECT p.*, gm.gymId, gm.durationInMonths " +
                "FROM products p LEFT JOIN gym_memberships gm ON p.productId = gm.productId";
        List<Product> products = new ArrayList<>();
        try (Connection connection = DatabaseConfiguration.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                double price = rs.getDouble("price");
                String code = rs.getString("code");
                int productId = rs.getInt("productId");

                if (rs.getObject("gymId") != null) {
                    int gymId = rs.getInt("gymId");
                    int durationInMonths = rs.getInt("durationInMonths");
                    GymMembership membership = new GymMembership(gymId, price, durationInMonths, code);
                    membership.setId(productId);
                    products.add(membership);
                } else {
                    Product product = new Product(price, code);
                    product.setId(productId);
                    products.add(product);
                }
            }
        }
        return products;
    }
}
