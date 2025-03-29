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


    private final WebClient webclient;
    private final WebClient webclient2;
    private String WEB_CLIENT_URL = System.getenv("WEB_CLIENT_URL");
    private String WEB_CLIENT_URL2 = System.getenv("WEB_CLIENT_URL2");

    @Autowired
    OrderService orderService;

    OrderRepository orderRepository;


    public OrderController(WebClient.Builder webclientBuilder, OrderRepository orderRepository, WebClient.Builder webclientBuilder2) {
        this.webclient = webclientBuilder.baseUrl(WEB_CLIENT_URL).build();
        this.webclient2 = webclientBuilder2.baseUrl(WEB_CLIENT_URL2).build();
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

    @GetMapping("/products/{id}")
    public Mono<OrderProductResponse> getOrderById2(@PathVariable Long id) {

        return orderRepository.findById(id).map(order ->
                        webclient2.get()
                                .uri("/products/" + order.getProductID())
                                .retrieve().bodyToMono(Product.class)
                                .map(product -> new OrderProductResponse(product, order)))
                .orElse(Mono.empty());
    }

    @GetMapping("/count/{productId}")
    public ResponseEntity<Integer> getOrderCount(@PathVariable Long productId) {
        int count = orderService.getOrderCountByProductId(productId);
        return ResponseEntity.ok(count);
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


