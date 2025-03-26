package com.example.OrderMicro;

import org.apache.catalina.User;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController {

//    private final WebClient webclient;

    OrderService orderService;
    OrderRepository orderRepository;

    public OrderController(OrderRepository orderRepository, OrderService orderService) {
        this.orderRepository = orderRepository;
        this.orderService = orderService;
    }

//    public OrderController(WebClient.Builder webclientBuilder, OrderRepository orderRepository) {
//        this.webclient = webclientBuilder.baseUrl("http://localhost:8080/orders").build();
//        this.orderRepository = orderRepository;
//    }

//    @GetMapping("/{id}")
//    public Mono<OrderResponse> getOrderById(@PathVariable Long id) {
//
//        return orderRepository.findById(id).map(order ->
//            webclient.get()
//                .uri("/users/" + order.getUserId())
//                .retrieve().bodyToMono(User.class)
//                .map(user -> new OrderResponse(order, (com.example.OrderMicro.User) user)))
//            .orElse(Mono.empty());
//    }

    @PostMapping
    public ResponseEntity<Order> createOrder(@RequestBody Order order) {
        Order o1 = orderService.addOrder(order);
        return ResponseEntity.ok().body(o1);
    }


    @GetMapping
    public ResponseEntity<List<Order>> getAllOrders() {
        List<Order> orders = orderRepository.findAll();
        if (orders.isEmpty()) {
            return ResponseEntity.notFound().build();

        } else {
            return ResponseEntity.ok(orders);
        }
    }

    @DeleteMapping ("/{id}")
    public void deleteOrder(@PathVariable long id) {
        orderService.deleteOrder(id);
    }

}


