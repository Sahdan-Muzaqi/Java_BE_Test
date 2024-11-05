package com.example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import com.example.entity.OrderEntity;
import com.example.service.OrderService;

@RestController
@RequestMapping("/api/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @GetMapping("/{id}")
    public OrderEntity getOrder(@PathVariable String id) {
        return orderService.getOrder(id);
    }

    @GetMapping
    public Page<OrderEntity> listOrders(Pageable pageable) {
        return orderService.listOrders(pageable);
    }

    @PostMapping
    public OrderEntity saveOrder(@RequestBody OrderEntity order) {
        return orderService.saveOrder(order);
    }

    @PutMapping("/{id}")
    public OrderEntity updateOrder(@PathVariable String id, @RequestBody OrderEntity order) {
        return orderService.updateOrder(id, order);
    }

    @DeleteMapping("/{id}")
    public void deleteOrder(@PathVariable String id) {
        orderService.deleteOrder(id);
    }
}
