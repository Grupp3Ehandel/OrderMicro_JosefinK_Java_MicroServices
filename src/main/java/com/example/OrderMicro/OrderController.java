package com.example.OrderMicro;

import org.apache.catalina.User;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/orders")
public class OrderController {

    private final WebClient webclient;

    private final OrderRepository orderRepository;

    public OrderController(WebClient.Builder webclientBuilder, OrderRepository orderRepository) {
        this.webclient = webclientBuilder.baseUrl("http://localhost:8080/orders").build();
        this.orderRepository = orderRepository;
    }

    @GetMapping("/{id}")
    public Mono<OrderResponse> getOrderById(@PathVariable Long id) {

        return orderRepository.findById(id).map(order ->
            webclient.get()
                .uri("/users/" + order.getUserId())
                .retrieve().bodyToMono(User.class)
                .map(user -> new OrderResponse(order, user)))
            .orElse(Mono.empty());
    }

    @PostMapping
    public Order createOrder(@RequestBody Order order) {
        return orderRepository.save(order);
    }


}
