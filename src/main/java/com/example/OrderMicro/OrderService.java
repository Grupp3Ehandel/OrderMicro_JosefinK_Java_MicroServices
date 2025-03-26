package com.example.OrderMicro;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Service;

@Service
public class OrderService {
    @PersistenceContext
    private EntityManager entityManager;

    OrderRepository orderRepository;
    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public Order addOrder(Order order) {
        return orderRepository.save(order);
    }

    public void deleteOrder(Long id) {
        orderRepository.deleteById(id);
    }

//    public Order updateOrder(Order order) {
//        return orderRepository.findById(order.getId()).map(Orders -> Order.SetName)
//    }

    public Order updateOrder(Order newOrder) {
        return orderRepository.findById(newOrder.getId()).map(Orders ->{
            Orders.setProduct(newOrder.getProduct());

            return orderRepository.save(Orders);
        }).orElse(null);
    }


}
