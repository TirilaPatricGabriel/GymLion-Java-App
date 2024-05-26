package services;

import classes.OrderProduct;
import classes.Product;
import repositories.AthleteRepository;
import repositories.OrderProductRepository;

import java.sql.SQLException;
import java.util.List;

public class OrderProductService {

    private OrderProductRepository orderProductRepository;

    private static OrderProductService instance;
    private AuditService auditService;

    private OrderProductService(OrderProductRepository repo) {
        this.orderProductRepository = repo;
        this.auditService = AuditService.getInstance();
    }

    public static OrderProductService getInstance(OrderProductRepository repo) {
        if (instance == null) {
            instance = new OrderProductService(repo);
        }
        return instance;
    }

    public void addOrderProduct(int orderId, int productId) throws SQLException {
        OrderProduct orderProduct = new OrderProduct(orderId, productId);
        orderProductRepository.addOrderProduct(orderProduct);
    }

    public List<Product> getProductsByOrderId(int orderId) throws SQLException {
        return orderProductRepository.getProductsByOrderId(orderId);
    }

    public List<OrderProduct> getOrdersByProductId(int productId) throws SQLException {
        return orderProductRepository.getOrdersByProductId(productId);
    }

    public void removeOrderProduct(int orderId, int productId) throws SQLException {
        OrderProduct orderProduct = new OrderProduct(orderId, productId);
        orderProductRepository.removeOrderProduct(orderProduct);
    }

    public void removeAllProductsFromOrder(int orderId) throws SQLException {
        orderProductRepository.removeAllProductsFromOrder(orderId);
    }
}
