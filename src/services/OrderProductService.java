package services;

import classes.OrderProduct;
import repositories.OrderProductRepository;

import java.sql.SQLException;
import java.util.List;

public class OrderProductService {

    private OrderProductRepository orderProductRepository;

    public OrderProductService(OrderProductRepository repo) {
        this.orderProductRepository = repo;
    }

    public void addOrderProduct(int orderId, int productId) throws SQLException {
        OrderProduct orderProduct = new OrderProduct(orderId, productId);
        orderProductRepository.addOrderProduct(orderProduct);
    }

    public List<OrderProduct> getProductsByOrderId(int orderId) throws SQLException {
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
