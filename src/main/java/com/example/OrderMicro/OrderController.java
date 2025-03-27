package com.example.OrderMicro;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import com.example.OrderMicro.User;

import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController {

    private User user1;
    private final WebClient webclient;
    private String WEB_CLIENT_URL = System.getenv("WEB_CLIENT_URL");

    @Autowired
    OrderService orderService;

    OrderRepository orderRepository;


    public OrderController(WebClient.Builder webclientBuilder, OrderRepository orderRepository) {
        this.webclient = webclientBuilder.baseUrl(WEB_CLIENT_URL).build();
        this.orderRepository = orderRepository;
    }

    @GetMapping("/users/{id}")
    public Mono<OrderResponse> getOrderById(@PathVariable Long id) {

        return orderRepository.findById(id).map(order ->
            webclient.get()
                .uri("/users/" + order.getUserId())
                .retrieve().bodyToMono(User.class)
                .map(user -> new OrderResponse(order, user)))
            .orElse(Mono.empty());
    }

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

    @PutMapping
    public ResponseEntity<Order> updateOrder(@RequestBody Order order) throws Exception {
        Order o1 =orderService.updateOrder(order);
        if(o1 != null
        ) {
            return ResponseEntity.accepted().body(order);
        }else{
            return ResponseEntity.notFound().build();
        }
    }

}


