package com.example.OrderMicro;

public class OrderResponse {

    private Order order;

    private User user;

    public OrderResponse(Order order, User user) {
        this.order = order;
        this.user = user;
    }

    public OrderResponse() {
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
